package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
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

        if(prefs.getString("userId",null) != null)
        {
            sendUserRequestById(prefs.getString("userId", null));
        }
        else
        {
            info.setText("Redirigiendo al Login");
            SystemClock.sleep(2000);
            Intent intent = new Intent(getApplicationContext(), Login_Options.class);
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
            if (response.getBoolean("enable") && prefs.getBoolean("isAuthenticated", false)) {

                info.setText("Bienvenido "+ response.getString("user"));
                SystemClock.sleep(2000);
                Intent intent = new Intent(getApplicationContext(), BackgroundScanActivity.class);
                intent.putExtra("totalPoints",response.getInt("total_gift_points"));

                nonStaticUtils.saveLogin(this,
                        response.getString("user"),
                        response.getString("password"),
                        response.getString("id"),
                        response.getInt("total_gift_points"),
                        true,
                        response.getString("socialNetworkType"),
                        response.getString("socialNetworkId"));

                startActivity(intent);
            }

            else{

                Intent intent = new Intent(getApplicationContext(), Login_Options.class);
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
