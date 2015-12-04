package task;

import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import controllers.ServiceController;
import database.DatabaseManager;
import model.BeaconCache;

/**
 * Created by Eduardo on 20/11/2015.
 */
public class BeaconMessageSyncTask extends TimerTask implements Response.Listener<JSONObject>, Response.ErrorListener {

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    BeaconCache myBeaconCache;
    List<BeaconCache> beaconList;

    public static final long SYNC_INTERVAL = 5 * 1000;


    private Handler mHandler = new Handler();

    private Timer mTimer = null;


    public void sendPromoRequest() {
        Log.i("BSMS", "Request sending");
        serviceController = new ServiceController();
        responseError = this;
        response = this;


        Map<String, String> nullMap = new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = "http://bpromodev.cfapps.io/promo/exp";
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);


    }

    @Override
    public void onResponse(JSONObject response) {
        Log.i("BSMS", "Request received");

        beaconList = DatabaseManager.getInstance().getAllBeaconCache();
        Map<String, Double> values = new HashMap<String, Double>();

        try {
            JSONArray promoArray = response.getJSONArray("PromosExp");
            for (int i = 0; i < promoArray.length(); i++) {
                Log.i("BSMS", promoArray.getJSONObject(i).getString("promoId"));

                for (int j = 0; j < beaconList.size(); j++) {
                    if (beaconList.get(j).promoId.equals(promoArray.getJSONObject(i).getString("promoId"))) {
                        Log.i("BSMS", beaconList.get(j).uniqueID + " " + promoArray.getJSONObject(i).getString("promoId"));
                        BeaconCache myBeacon = new BeaconCache();
                        myBeacon.promoId = beaconList.get(j).promoId;
                        myBeacon.uniqueID = beaconList.get(j).uniqueID;
                        myBeacon.message =  beaconList.get(j).message;
                        myBeacon.proximity = beaconList.get(j).proximity;
                       // myBeacon.expiration = promoArray.getJSONObject(i).get("expiration").toString();
                        DatabaseManager.getInstance().updateBeaconCache(myBeacon);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
    }

    @Override
    public void run() {
        // run on another thread
        mHandler.post(new Runnable() {

            @Override
            public void run() {

                Log.i("BSMS", "Running Task");
                beaconList = DatabaseManager.getInstance().getAllBeaconCache();
                if (beaconList != null) {
                    Log.i("BSMS", "DB not null");
                    sendPromoRequest();
                }

            }

        });
    }



}
