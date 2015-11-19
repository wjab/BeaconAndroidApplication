package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

import controllers.ServiceController;
import database.DatabaseManager;
import model.BeaconCache;

public class BeaconSyncMessageService extends Service implements Response.Listener<JSONObject>, Response.ErrorListener {

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    BeaconCache myBeaconCache;
    List<BeaconCache> beaconList;

    public BeaconSyncMessageService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("BSMS", "Service onCreate");
    }

    @Override
    public void onDestroy() {
        Log.i("BSMS", "Service onStop");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("BSMS", "Service Start");

        beaconList = DatabaseManager.getInstance().getAllBeaconCache();
        if(beaconList !=null){
            Log.i("BSMS","DB not null");
            sendPromoRequest();
        }




        return Service.START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void sendPromoRequest(){
        Log.i("BSMS","Request sending");
        serviceController =  new ServiceController();
        responseError = this;
        response = this;


        Map<String,String> nullMap =  new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = "http://beacon_promo_development.cfapps.io/promo/exp";
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response,responseError);


    }

    public void addBeaconDB(BeaconCache beaconObject){

        beaconList = DatabaseManager.getInstance().getAllBeaconCache();
        if(beaconList!=null){
            for (BeaconCache myCache:beaconList) {
                if(!beaconObject.getUniqueID().equals(myCache.getUniqueID()) && !beaconObject.getProximity().equals(myCache.getProximity())){
                    DatabaseManager.getInstance().addBeaconCache(beaconObject);
                }
                else {
                    DatabaseManager.getInstance().updateBeaconCache(beaconObject);
                }
            }
        }
        else{
            DatabaseManager.getInstance().addBeaconCache(beaconObject);
        }
    }
    @Override
    public void onResponse(JSONObject response) {
        Log.i("BSMS", "Request received");

        beaconList = DatabaseManager.getInstance().getAllBeaconCache();
        Map<String,Double> values = new HashMap<String,Double>();

        try {
            JSONArray promoArray = response.getJSONArray("PromosExp");
            for(int i =0; i < promoArray.length();i++){
                Log.i("BSMS", promoArray.getJSONObject(i).getString("promoId"));


                values.put(promoArray.getJSONObject(i).getString("promoId"), promoArray.getJSONObject(i).getDouble("expiration"));
            }

            for(BeaconCache myBeacon:beaconList){

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
    }
}
