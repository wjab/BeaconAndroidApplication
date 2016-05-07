package service;

import android.app.NotificationManager;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import database.DatabaseManager;
import model.cache.BeaconCache;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.PromoDetailActivity;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.PullNotificationsActivity;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;
import utils.CustomNotificationManager;
import utils.NonStaticUtils;
import utils.Utils;

/**
 * Created by Administrador on 12/21/2015.
 */
public class GivePointToUserService extends Service implements Response.Listener<JSONObject>, Response.ErrorListener
{
        NotificationManager notificationManager;
        ServiceController serviceController;
        Response.Listener<JSONObject> response;
        Response.ErrorListener responseError;
        BeaconCache myBeaconCache;
        Context context;
        String promoId;
        NonStaticUtils nonStaticUtils;
        ArrayList<BeaconCache> beaconCaches = new ArrayList<BeaconCache>();
        int numMessages = 0;

        //Runnable mRunnable;

        //public static final long SYNC_INTERVAL = 10 * 1000;

        // Constructor
        public GivePointToUserService()
        {
            DatabaseManager.init(this);
            nonStaticUtils = new NonStaticUtils();
        }

        @Override
        public void onCreate()
        {
            super.onCreate();
            Log.i("GivePointsToUserService", "Service onCreate");
            DatabaseManager.init(this);
            context = getApplicationContext();
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId)
        {
            promoId = intent.getStringExtra("promoId");
            sendPromoToGivePointsToUserRequest(promoId);

            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent)
        {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onDestroy()
        {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        public void sendPromoToGivePointsToUserRequest(String promoId)
        {
            Log.i("GivePointsToUserService", "Request sending");
            serviceController = new ServiceController();
            responseError = this;
            response = this;

            SharedPreferences sharedPreferences = nonStaticUtils.loadLoginInfo(context);
            String userId = sharedPreferences.getString("userId", null);

            Map<String, String> mapHeaders = new HashMap<String, String>();
            mapHeaders.put("Content-Type", "application/json");
            String url = getString(R.string.WebService_Utils)+"utils/savePoints";

            Map<String, String> mapParams = new HashMap<String, String>();
            mapParams.put("promoId", promoId);
            mapParams.put("userId", userId );

            serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, mapHeaders, response, responseError);
        }

    @Override
    public void onResponse(JSONObject response)
    {
        Log.i("GivePointsToUserService", "Request received");

        myBeaconCache = new BeaconCache();
        JSONObject userObject = new JSONObject();
        SharedPreferences sharedPreferences;
        int giftPoints = 0;

        try
        {
            userObject = response.getJSONObject("user");

            if( userObject != null )
            {
                sharedPreferences = nonStaticUtils.loadLoginInfo(context);
                giftPoints = userObject.getInt("total_gift_points");

                nonStaticUtils.saveLogin(getApplicationContext(),
                        sharedPreferences.getString("username", null),
                        sharedPreferences.getString("password", null),
                        sharedPreferences.getString("userId", null),
                        giftPoints,
                        sharedPreferences.getBoolean("isAuthenticated", false));

                ShowPromoNotification(context);
            }
            else
            {
                //TODO: Identify why the service response a null object
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error)
    {
        Log.d("Request Error on GPTUS", error.toString());
        ShowPromoNotification(context);
    }

    public void ShowPromoNotification(Context context)
    {

        myBeaconCache = Utils.GetCacheByPromoId(promoId);


        boolean isRepeated = false;

        if(beaconCaches != null){
            for (BeaconCache object : beaconCaches){
                if( object.id == myBeaconCache.id ){
                    isRepeated = true;
                    break;
                }
            }
         }


         if(myBeaconCache.id != 0 && !isRepeated) {


            Intent redirectIntent = new Intent(context, PullNotificationsActivity.class);
             beaconCaches.add(myBeaconCache);
            CustomNotificationManager cNotificationManager = new CustomNotificationManager();
            cNotificationManager.setContentTitle("QuickShop");
            cNotificationManager.setContentText(myBeaconCache.descrition);
             cNotificationManager.setIcon(R.drawable.logo);
            cNotificationManager.setTicker("Tienes nuevas notificaciones");
            cNotificationManager.setnotificationMessage(myBeaconCache.descrition);
            redirectIntent.putExtra("promoDetail", beaconCaches);
            cNotificationManager.setRedirectIntent(redirectIntent);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(PromoDetailActivity.class);
            stackBuilder.addNextIntent(redirectIntent);


            cNotificationManager.ShowInputNotification(context,notificationManager,redirectIntent, "Promos", stackBuilder, beaconCaches.size());


        }
    }


}
