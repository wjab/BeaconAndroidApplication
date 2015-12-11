package broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.ServiceController;
import database.DatabaseManager;
import model.cache.BeaconCache;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.LoginMainActivity;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.PromoDetailActivity;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;
import utils.Constants;
import utils.CustomNotificationManager;
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
    boolean isProcessing;
    String beaconUniqueId = "";

    List<BeaconCache> beaconList;

    public ForegroundBroadcastInterceptor(Context context) {
        super(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        inodoroForever = 0;

        DatabaseManager.init(getContext());

        requestDevice =false;
        myBeaconCache = new BeaconCache();
    }

    @Override
    protected void onBeaconAppeared(int info, IBeaconDevice beaconDevice)
    {
        DatabaseManager.init(getContext());
        final Context context = getContext();

        final String deviceName = beaconDevice.getUniqueId();
        final String proximityUUID = beaconDevice.getProximityUUID().toString();
        final int major = beaconDevice.getMajor();
        final int minor = beaconDevice.getMinor();
        final double distance = beaconDevice.getDistance();
        final Proximity proximity = beaconDevice.getProximity();
        beaconUniqueId = beaconDevice.getUniqueId();

        responseError = this;
        response = this;
        serviceController = new ServiceController();

        try
        {
            sendDeviceRequest(beaconDevice.getUniqueId());
        }
        catch (Exception ex)
        {
               error = ex.toString();
        }

        Intent redirectIntent = new Intent(context, PromoDetailActivity.class);

        CustomNotificationManager cNotificationManager = new CustomNotificationManager();
        cNotificationManager.setContentTitle(context.getString(R.string.beacon_appeared, beaconDevice.getName()));
        cNotificationManager.setIcon(R.drawable.beacon);
        cNotificationManager.setTicker(context.getString(R.string.beacon_appeared, beaconDevice.getName()));
        cNotificationManager.setnotificationMessage(context.getString(R.string.appeared_beacon_info,
                beaconDevice.getName(), beaconDevice.getUniqueId(), beaconDevice.getMajor(),
                beaconDevice.getMinor(), beaconDevice.getDistance(), beaconDevice.getProximity().name()));
        cNotificationManager.setRedirectIntent(redirectIntent);
        cNotificationManager.ShowInputNotification(context, info, notificationManager);

    }

    @Override
    protected void onRegionAbandoned(int info, IBeaconRegion region) {
        Context context = getContext();
        Utils.showToast(context, context.getString(R.string.region_abandoned, beaconUniqueId + " " + region.getName()));
    }

    @Override
    protected void onRegionEntered(int info, IBeaconRegion region) {
        Context context = getContext();
        Utils.showToast(context, context.getString(R.string.region_entered, beaconUniqueId + " " + region.getName()));
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

                myBeaconCache.message = message ;
                myBeaconCache.proximity = range;
                myBeaconCache.uniqueID = response.getString("uniqueID");
                myBeaconCache.promoId = promo;
                myBeaconCache.expiration = Utils.UnixTimeStampWithDefaultExpiration();
                myBeaconCache.currentDatetime = Utils.UnixTimeStamp();

                addBeaconDB(myBeaconCache);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestDevice = false;
    }

    public void sendDeviceRequest(String uniqueId){

        if(!requestDevice)
        {
            serviceController = new ServiceController();
            responseError = this;
            response = this;
            myBeaconCache = new BeaconCache();
            Map<String, String> nullMap = new HashMap<String, String>();

            Map<String, String> map = new HashMap<String, String>();
            map.put("Content-Type", "application/json");
            String url = "http://bdevicedev.cfapps.io/device/UID/" + uniqueId;
            serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
    }

    public void addBeaconDB(BeaconCache beaconObject)
    {
        boolean existOnlist = false;
        try
        {
            beaconList = DatabaseManager.getInstance().getAllBeaconCache();

            // Delete expired promos
            delBeaconDB();

            if(beaconList.size() > 0)
            {
                for (BeaconCache cacheItem : beaconList)
                {
                    if (cacheItem.uniqueID.contains(beaconObject.uniqueID) && cacheItem.proximity.contains(beaconObject.proximity))
                    {
                        existOnlist = true;
                        break;
                    }
                }
                if (!existOnlist)
                {
                    DatabaseManager.getInstance().addBeaconCache(beaconObject);
                }
                /*else
                {
                    DatabaseManager.getInstance().updateBeaconCache(beaconObject);
                }*/
            }
            else
            {
                DatabaseManager.getInstance().addBeaconCache(beaconObject);
            }
        }
        catch (Exception ex)
        {
            Log.i("FBI",ex.getStackTrace().toString());
        }
    }

    public void delBeaconDB()
    {
        List<BeaconCache> listtodelete = new ArrayList<BeaconCache>();

        for (BeaconCache beaconItem : beaconList)
        {
            if (beaconItem.expiration < Utils.UnixTimeStamp())
            {
                listtodelete.add(beaconItem);
            }
        }
        DatabaseManager.getInstance().deleteBeaconCache(listtodelete);
    }

}
