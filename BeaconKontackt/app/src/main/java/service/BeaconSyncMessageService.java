package service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import controllers.ServiceController;
import database.DatabaseManager;
import model.cache.BeaconCache;
import utils.Utils;

public class BeaconSyncMessageService extends Service implements Response.Listener<JSONObject>, Response.ErrorListener  {

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    BeaconCache myBeaconCache;
    List<BeaconCache> beaconList;
    Runnable mRunnable;

    public static final long SYNC_INTERVAL = 10 * 1000;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    public BeaconSyncMessageService() {

    }

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
    public void onCreate() {
        super.onCreate();
        Log.i("BSMS", "Service onCreate");
        DatabaseManager.init(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

       final Handler mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                //sendPromoRequest();
                mHandler.postDelayed(mRunnable,SYNC_INTERVAL);
            }
        };
       mHandler.postDelayed(mRunnable, SYNC_INTERVAL);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onResponse(JSONObject response) {
        Log.i("BSMS", "Request received");

        beaconList = DatabaseManager.getInstance().getAllBeaconCache();
        NumberFormat tempFormat = new DecimalFormat("#0.00");
        double temp = 0.0;


        try {
            JSONArray promoArray = response.getJSONArray("PromosExp");
            for (int i = 0; i < promoArray.length(); i++)
            {
                for (int j = 0; j < beaconList.size(); j++)
                {
                    if (beaconList.get(j).promoId.equals(promoArray.getJSONObject(i).getString("promoId"))) {

                        BeaconCache myBeacon = new BeaconCache();
                        myBeacon.promoId = beaconList.get(j).promoId;
                        myBeacon.uniqueID = beaconList.get(j).uniqueID;
                        myBeacon.message =  beaconList.get(j).message;
                        myBeacon.proximity = beaconList.get(j).proximity;
                        temp = promoArray.getJSONObject(i).getDouble("expiration");
                        myBeacon.expiration = Utils.UnixTimeStampWithDefaultExpiration();
                        myBeacon.currentDatetime = Utils.UnixTimeStamp();
                        //10.0; //Double.valueOf((String.valueOf(tempFormat.format(temp)).replace(',', '.')).toString());

                        DatabaseManager.getInstance().updateBeaconCache(myBeacon);
                    }
                }
            }

            beaconList = DatabaseManager.getInstance().getAllBeaconCache();

            for (BeaconCache myBeacon:beaconList
                 ) {

                Log.i("BSMS", myBeacon.uniqueID +" "+ myBeacon.expiration);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.d("Request Error on BSMS", error.toString());
    }

}
