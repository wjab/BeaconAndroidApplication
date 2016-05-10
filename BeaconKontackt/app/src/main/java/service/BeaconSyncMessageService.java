package service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import controllers.ServiceController;
import database.DatabaseManager;
import model.cache.BeaconCache;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;
import utils.NonStaticUtils;

public class BeaconSyncMessageService extends Service implements Response.Listener<JSONObject>, Response.ErrorListener  {

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    BeaconCache myBeaconCache;
    List<BeaconCache> beaconList;
    Runnable mRunnable;
    BeaconCache beaconCacheRef = new BeaconCache();
    NonStaticUtils nonStaticUtils;


    public static final long SYNC_INTERVAL = 10 * 1000;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;

    public BeaconSyncMessageService()
    {
        DatabaseManager.init(this);
        nonStaticUtils = new NonStaticUtils();
    }

    public void sendPromoRequest() {
        Log.i("BSMS", "Request sending");
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_Promo)+"promo/" + beaconCacheRef.promoId;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("BSMS", "Service onCreate");
        DatabaseManager.init(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        DatabaseManager.init(this);
       /*final Handler mHandler = new Handler();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                //sendPromoRequest();
                mHandler.postDelayed(mRunnable,SYNC_INTERVAL);
            }
        };
        mHandler.postDelayed(mRunnable, SYNC_INTERVAL);*/

        beaconCacheRef = (BeaconCache)intent.getSerializableExtra("beaconCacheRef");
        sendPromoRequest();

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

        try
        {
            byte[] data = response.getString("description") != null ?response.getString("description").getBytes("UTF-8"): null;
            String encodedDesc = Base64.encodeToString(data, Base64.DEFAULT);
            DatabaseManager.init(this);
            beaconCacheRef.giftPoints = response.getInt("gift_points");
            beaconCacheRef.descrition = encodedDesc;
            beaconCacheRef.title = response.getString("title");
            beaconCacheRef.attempt = response.getInt("attempt");
            beaconCacheRef.isautomatic = response.getBoolean("isAutomatic");
            beaconCacheRef.picturePath = response.getString("images");

            // Se hace la actualizacion de los datos de cache con la informacion recibida por el web service de promociones
            DatabaseManager.getInstance().updateBeaconCache(beaconCacheRef);

            nonStaticUtils.StartGiftpointService(getApplicationContext(), beaconCacheRef.promoId);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
         catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.d("Request Error on BSMS", error.toString());
    }

}
