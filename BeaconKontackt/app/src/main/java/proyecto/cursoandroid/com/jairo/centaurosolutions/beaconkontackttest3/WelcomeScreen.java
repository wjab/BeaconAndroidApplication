package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.menu.MenuAdapter;
import controllers.ServiceController;
import model.elementMenu.ElementMenu;
import utils.Utils;

public class WelcomeScreen extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{


    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    TextView info;
    String usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        info= (TextView) findViewById(R.id.InfoLoading);

        info.setText("Comprobando Usuario");
        SystemClock.sleep(2000);
        responseError = this;
        response = this;
        serviceController =  new ServiceController();


        loadLoginInfo();

        if(prefs.getString("userId",null) != null){

            sendUserRequestById(prefs.getString("userId", null));
        }

        else{

            info.setText("Redirigiendo al Loguin");
            SystemClock.sleep(2000);
            Intent intent = new Intent(getApplicationContext(), LoginMainActivity.class);
            startActivity(intent);
        }
    }

    public SharedPreferences loadLoginInfo(){

        prefs = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE);
        return prefs;
    }

    public void saveLogin(String username,String password, String userId, int points, boolean isAuth){

        prefs = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE);
        editor = prefs.edit();
        editor.putString("userId", userId);
        editor.putString("username", username);
        editor.putString("password", Utils.setEncryptedText(password));
        editor.putInt("points", points);
        editor.putBoolean("isAuthenticated", isAuth);
        editor.commit();
        usuario=username;
    }


    public void sendUserRequestById(String userId){

        info.setText("Conectando ccon los servidores");
        SystemClock.sleep(2000);
        serviceController = new ServiceController();
        String url = "http://beuserdev.cfapps.io/user/id/"+userId;
        Map<String,String> nullMap =  new HashMap<String, String>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");


        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);
    }


    @Override
    public void onResponse(JSONObject response) {

        Log.d("Response", response.toString());

        loadLoginInfo();

        try
        {
            if (response.getBoolean("enable") && prefs.getBoolean("isAuthenticated", false)) {

                info.setText("Bienvenido "+ response.getString("user"));
                SystemClock.sleep(2000);
                Intent intent = new Intent(getApplicationContext(), BackgroundScanActivity.class);
                intent.putExtra("totalPoints",response.getInt("total_gift_points"));
                saveLogin(response.getString("user"),response.getString("password"), response.getString("id"),response.getInt("total_gift_points"), true);

                startActivity(intent);
            }

            else{

                Intent intent = new Intent(getApplicationContext(), LoginMainActivity.class);
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
