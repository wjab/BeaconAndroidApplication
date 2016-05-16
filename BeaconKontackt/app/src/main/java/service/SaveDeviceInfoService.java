package service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import controllers.ServiceController;
import database.DatabaseManager;
import model.mobile.Mobile;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

public class SaveDeviceInfoService extends Service implements Response.Listener<JSONObject>, Response.ErrorListener  {

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    Boolean isProcessing = false;
    String url;


    public static final long SYNC_INTERVAL = 5 * 1000;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    SharedPreferences prefs;
    SharedPreferences editor;

    public SaveDeviceInfoService() {

    }

    public void saveMobileRequest(String userId) {

        Log.i("BSMS", "Request sending");
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Mobile mobile = new Mobile();
        prefs = getSharedPreferences("DeviceData", MODE_PRIVATE);
        String deviceId = prefs.getString("DeviceId", null);


        mobile.setDeviceName(Build.MANUFACTURER.toUpperCase());
        mobile.setDeviceModel(Build.MODEL);
        mobile.setOsName("Android");
        mobile.setOsVersion(Build.VERSION.RELEASE);
        mobile.setUserId(userId);


        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("deviceName",mobile.getDeviceName());
        parameters.put("deviceModel",mobile.getDeviceModel());
        parameters.put("osName", mobile.getOsName());
        parameters.put("osVersion",mobile.getOsVersion());
        parameters.put("userId", mobile.getUserId());


        url =getString(R.string.WebService_Mobile)+"mobile";


        if (deviceId != null) {
            url = url+"/"+deviceId;
            serviceController.jsonObjectRequest(url, Request.Method.PUT, parameters, header, response, responseError);
        }
        else {
            serviceController.jsonObjectRequest(url, Request.Method.POST, parameters, header, response, responseError);
        }

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("BSMS", "Service onCreate");
        DatabaseManager.init(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        String userId =   intent.getStringExtra("userId");

        if(userId != null){
            saveMobileRequest(userId);
        }

       /* final Handler mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {

                mHandler.postDelayed(mRunnable,SYNC_INTERVAL);
            }
        };
        mHandler.postDelayed(mRunnable, SYNC_INTERVAL);*/
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


        try{

            prefs = getSharedPreferences("DeviceData", MODE_PRIVATE);
            String deviceId = prefs.getString("DeviceId", null);
            SharedPreferences.Editor editor = prefs.edit();


            if(deviceId == null){

                editor.putString("DeviceId", response.getJSONObject("mobile").getString("id"));

                editor.commit();

                prefs = getSharedPreferences("DeviceId", MODE_PRIVATE);
            }

        }
        catch (Exception ex){
            Log.i("BSMS", "Request received");
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.d("Request Error on BSMS", error.toString());
    }

}
