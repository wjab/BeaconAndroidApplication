package broudcast;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kontakt.sdk.android.common.Proximity;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.BackgroundScanActivity;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;
import utils.Utils;

public class ForegroundBroadcastInterceptor extends AbstractBroadcastInterceptor implements Response.Listener<JSONObject>, Response.ErrorListener {

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    private final NotificationManager notificationManager;
    int inodoroForever;
    boolean proximityReached;
     boolean isEnteredImmediate = false;
     boolean isEnteredFar = false;
     boolean isEnteredNear = false;
     int attempts = 0;


    public ForegroundBroadcastInterceptor(Context context) {
        super(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        inodoroForever = 0;
        proximityReached = false;
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


        if(proximity.name() == "IMMEDIATE" ) {
            inodoroForever++;

            Notification notification = new Notification.Builder(context)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setTicker(context.getString(R.string.beacon_appeared, deviceName))
                    .setContentIntent(PendingIntent.getActivity(context,
                            0,
                            redirectIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT))
                    .setContentTitle(context.getString(R.string.beacon_appeared, deviceName))
                    .setContentText("Has ido al inodoro " + inodoroForever + (inodoroForever == 1 ? " vez" : " veces"))
                    .setSmallIcon(R.drawable.beacon)
                    .setStyle(new Notification.BigTextStyle().bigText(context.getString(R.string.appeared_beacon_info, deviceName,
                            beaconDevice.getUniqueId(),
                            major,
                            minor,
                            distance,
                            proximity.name())))
                    .build();

            notificationManager.notify(info, notification);
        }


   /*     Utils.showToast(context, context.getString(R.string.appeared_beacon_info, deviceName,
                proximityUUID,
                major,
                minor,
                distance,
                proximity.name()));*/


/*
        serviceController = new ServiceController();
        String url = "http://beacon_user_devel.cfapps.io/user/"+userText;
        Map<String,String> nullMap =  new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type","application/json");


        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);*/
    }



    @Override
    protected void onRegionAbandoned(int info, IBeaconRegion region) {
        Context context = getContext();
        isEnteredImmediate =false;
        isEnteredNear = false;
        isEnteredFar = false;



        Utils.showToast(context, context.getString(R.string.region_abandoned, region.getName()));
    }

    @Override
    protected void onRegionEntered(int info, IBeaconRegion region) {
        Context context = getContext();

        isEnteredImmediate =false;
        isEnteredNear = false;
        isEnteredFar = false;

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

        Log.d("Response", response.toString());
        /* try
        {


            String requestPassword = setEncryptedPassword(password.getText().toString());

            if(response.getString("password").equals(requestPassword) ){

                if(response.getBoolean("enable")){
                    Intent intent = new Intent(getApplicationContext(),BackgroundScanActivity.class);
                    //Intent intent = new Intent(getApplicationContext(),Activity_Principal.class);
                    //intent.putExtra("totalPoints",response.getInt("total_gift_points"));
                    startActivity(intent);
                }
                else{

                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario deshabilitado", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
        catch (Exception ex){
            Toast toast = Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT);
            toast.show();
        }*/
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
    }
}
