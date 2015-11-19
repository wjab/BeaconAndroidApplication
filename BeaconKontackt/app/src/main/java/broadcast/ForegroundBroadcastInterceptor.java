package broadcast;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kontakt.sdk.android.common.Proximity;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.ServiceController;
import database.DatabaseManager;
import model.BeaconCache;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.BackgroundScanActivity;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;
import service.BeaconSyncMessageService;
import utils.Utils;

public class ForegroundBroadcastInterceptor extends AbstractBroadcastInterceptor implements Response.Listener<JSONObject>, Response.ErrorListener {

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    BeaconCache myBeaconCache;
    boolean requestPromo;
    boolean requestDevice;
    private final NotificationManager notificationManager;
    int inodoroForever;
    int attempts = 0;
    String error;

    List<BeaconCache> beaconList;



    public ForegroundBroadcastInterceptor(Context context) {
        super(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        inodoroForever = 0;

        DatabaseManager.init(getContext());



        requestDevice =false;
        requestPromo = false;
        myBeaconCache = new BeaconCache();

        Intent i = new Intent(getContext(), BeaconSyncMessageService.class);
        context.startService(i);


    }

    @Override
    protected void onBeaconAppeared(int info, IBeaconDevice beaconDevice) {

        final Context context = getContext();
        final String deviceName = beaconDevice.getUniqueId();
        final String proximityUUID = beaconDevice.getProximityUUID().toString();
        final int major = beaconDevice.getMajor();
        final int minor = beaconDevice.getMinor();
        final double distance = beaconDevice.getDistance();
        final Proximity proximity = beaconDevice.getProximity();
        responseError = this;
        response = this;
        serviceController = new ServiceController();
        final Intent redirectIntent = new Intent(context, BackgroundScanActivity.class);
        redirectIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);



        try{
                beaconList = DatabaseManager.getInstance().getAllBeaconCache();
                serviceController =  new ServiceController();
                responseError = this;
                response = this;
                myBeaconCache= new BeaconCache();
                Map<String,String> nullMap =  new HashMap<String, String>();

                Map<String, String> map = new HashMap<String, String>();
                map.put("Content-Type", "application/json");
                String url = "http://beacon_device_dev.cfapps.io/device/UID/"+beaconDevice.getUniqueId();
                serviceController.jsonObjectRequest(url, Request.Method.GET,null,map,response,responseError);
                requestDevice = true;


        }
        catch (Exception ex){
               error = ex.toString();
        }



/*
            Notification notification = new Notification.Builder(context)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setTicker(context.getString(R.string.beacon_appeared, deviceName))
                    .setContentIntent(PendingIntent.getActivity(context,
                            0,
                            redirectIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT))
                    .setContentTitle(context.getString(R.string.beacon_appeared, deviceName))
                    .setSmallIcon(R.drawable.beacon)
                    .setStyle(new Notification.BigTextStyle().bigText(context.getString(R.string.appeared_beacon_info, deviceName,
                            beaconDevice.getUniqueId(),
                            major,
                            minor,
                            distance,
                            proximity.name())))
                    .build();

            notificationManager.notify(info, notification);
        */




   /*     Utils.showToast(context, context.getString(R.string.appeared_beacon_info, deviceName,
                proximityUUID,
                major,
                minor,
                distance,
                proximity.name()));*/


/*
        serviceController = new ServiceController();
        String url = "http://beacon_user_devel.cfapps.io/user";
        Map<String,String> nullMap =  new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type","application/json");


        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);*/
    }



    @Override
    protected void onRegionAbandoned(int info, IBeaconRegion region) {
        Context context = getContext();


        Utils.showToast(context, context.getString(R.string.region_abandoned, region.getName()));
    }

    @Override
    protected void onRegionEntered(int info, IBeaconRegion region) {
        Context context = getContext();


       Utils.showToast(context, context.getString(R.string.region_entered, region.getName()));
    }

    @Override
    protected void onScanStarted(int info) {
        Context context = getContext();
      //  Utils.showToast(context, context.getString(R.string.scan_started));
    }

    @Override
    protected void onScanStopped(int info) {
        Context context = getContext();
       // Utils.showToast(context, context.getString(R.string.scan_stopped));
    }

    @Override
    public void onResponse(JSONObject response) {




        try {

            if(response.getJSONArray("ranges") != null && !requestPromo){

                JSONArray ranges= response.getJSONArray("ranges");
                String range = "";
                String message = "";
                String messageType = "";
                String promo = "";

                for(int i=0; i < ranges.length(); i++ ){
                    JSONObject currRange = ranges.getJSONObject(i);
                    range = currRange.getString("type");
                    message = currRange.getString("message");
                    messageType = currRange.getString("messageType");
                    promo = currRange.getString("promoID");


                    myBeaconCache.setMessage(message);
                    myBeaconCache.setProximity(range);
                    myBeaconCache.setUniqueID(response.getString("uniqueID"));
                    myBeaconCache.setPromoId(promo);


                    addBeaconDB(myBeaconCache);
                }

            }
            else if(requestPromo){

            }

            /*
            if(requestDevice && !requestPromo){
                JSONArray ranges= response.getJSONArray("ranges");
                String range = "";
                String message = "";
                String messageType = "";
                String promo = "";

                for(int i=0; i < ranges.length(); i++ ){
                    JSONObject currRange = ranges.getJSONObject(i);
                    range = currRange.getString("type");
                    message = currRange.getString("message");
                    messageType = currRange.getString("messageType");
                    promo = currRange.getString("promoID");


                    myBeaconCache.setMessage(message);
                    myBeaconCache.setProximityUUID(response.getString("proximityUUID"));
                    myBeaconCache.setUniqueID(response.getString("uniqueID"));
                    DatabaseManager.getInstance().addBeaconCache(myBeaconCache);
                }



                sendPromoRequest(myBeaconCache,promo);
            }

            if(requestPromo){

                long startDateLong = response.getLong("startDate");
                long endDateLong = response.getLong("endDate");
                Date startDate = Utils.convertLongToDate(startDateLong);
                Date endDate = Utils.convertLongToDate(endDateLong);
                double expiration =  Utils.getDaysDiff(startDate, endDate);

                requestPromo = false;
            }
            */


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void sendPromoRequest(BeaconCache beaconCache, String promo){
        serviceController =  new ServiceController();
        responseError = this;
        response = this;


        Map<String,String> nullMap =  new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = "http://beacon_promo_development.cfapps.io/promo/"+promo;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response,responseError);
        requestPromo = true;

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
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

}
