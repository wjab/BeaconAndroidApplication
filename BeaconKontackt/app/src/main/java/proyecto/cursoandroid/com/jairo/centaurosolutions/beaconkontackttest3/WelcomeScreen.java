package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import utils.Utils;

public class WelcomeScreen extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{


    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        responseError = this;
        response = this;
        serviceController =  new ServiceController();

        loadLoginInfo();

        if(prefs.getString("userId",null) != null){

            sendUserRequestById(prefs.getString("userId", null));
        }

        else{

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
        editor.putInt("points",points);
        editor.putBoolean("isAuthenticated", isAuth);
        editor.commit();
    }


    public void sendUserRequestById(String userId){
        serviceController = new ServiceController();
        String url = "http://buserdev.cfapps.io/user/id/"+userId;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
