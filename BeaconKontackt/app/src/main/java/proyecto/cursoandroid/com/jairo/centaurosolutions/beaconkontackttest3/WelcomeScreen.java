package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.User;
import utils.NonStaticUtils;

public class WelcomeScreen extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{


    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    TextView info;
    String usuario;
    NonStaticUtils nonStaticUtils;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome_screen);
        info= (TextView) findViewById(R.id.InfoLoading);
        info.setText("Comprobando Usuario");
        SystemClock.sleep(2000);
        responseError = this;
        response = this;
        serviceController =  new ServiceController();
        nonStaticUtils = new NonStaticUtils();
        prefs = nonStaticUtils.loadLoginInfo(this);

        if(prefs.getString("userId", null) != null)
        {
            sendUserRequestById(prefs.getString("userId", null));
        }
        else
        {
            info.setText("Redirigiendo al Login");
            SystemClock.sleep(2000);
            Intent intent = new Intent(getApplicationContext(), LoginOptions.class);
            startActivity(intent);
        }
    }

    public void sendUserRequestById(String userId){

        info.setText("Cargando Aplicación");
        SystemClock.sleep(2000);
        serviceController = new ServiceController();
        String url = getString(R.string.WebService_User) + "user/id/" + userId;
        Map<String,String> nullMap =  new HashMap<String, String>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");


        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);
    }



    @Override
    public void onResponse(JSONObject response) {

        Log.d("Response", response.toString());

        //prefs = nonStaticUtils.loadLoginInfo(this);

        try
        {
            JSONObject currRange= response.getJSONObject("user");


            if (currRange.getString("enable").equals("true") && prefs.getBoolean("isAuthenticated", false)) {

                info.setText("Bienvenido "+ currRange.getString("user"));
                SystemClock.sleep(2000);
                Intent intent = new Intent(getApplicationContext(), BackgroundScanActivity.class);

                intent.putExtra("totalPoints",currRange.getInt("totalGiftPoints"));
                Map<String,String> map = new HashMap<>();
                if (!currRange.getString("socialNetworkJson").isEmpty()){
                String jsonFace= currRange.getString("socialNetworkJson");
                jsonFace = jsonFace.substring(1, jsonFace.length()-1);           //remove curly brackets
                String[] keyValuePairs = jsonFace.split(",");              //split the string to creat key-value pairs

                for(String pair : keyValuePairs)                        //iterate over the pairs
                {
                    String[] entry = pair.split("=");                   //split the pairs to get key and value
                    map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
                }
                }
                nonStaticUtils.saveLogin(this,
                        currRange.getString("user"),
                        currRange.getString("password"),
                        currRange.getString("id"),
                        currRange.getInt("totalGiftPoints"),
                        true,
                        currRange.getString("socialNetworkType"),
                        currRange.getString("socialNetworkId"),
                        currRange.getString("pathImage"),
                        (currRange.getString("name") != null ? currRange.getString("name").toString() : ""),
                        (currRange.getString("lastName") != null ? currRange.getString("lastName").toString() : ""),
                        (currRange.getString("phone") != null ? currRange.getString("phone").toString() : ""),
                        (currRange.getString("email") != null ? currRange.getString("email").toString() : ""),
                        (currRange.getString("gender") != null ? currRange.getString("gender").toString() : ""),
                        (map.get("birthday") != null ? map.get("birthday").toString() : ""));
                startActivity(intent);
            }

            else{

                Intent intent = new Intent(getApplicationContext(), LoginOptions.class);
                startActivity(intent);
            }
        }
        catch (Exception ex){
            Toast toast = Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
    }



}
