package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
import utils.Utils;

public class LoginMainActivity extends Activity implements Response.Listener<JSONObject>, Response.ErrorListener{


    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    ImageView loginImage;
    TextView username;
    TextView password;
    TextView register;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    boolean isAuthenticated;
    NonStaticUtils nonStaticUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_main);

        nonStaticUtils =  new NonStaticUtils();
        Button login = (Button)findViewById(R.id.login);
        loginImage = (ImageView)findViewById(R.id.loginImage);
        username = (TextView) findViewById(R.id.usuario);
        password = (TextView) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.register);

        responseError = this;
        response = this;
        serviceController =  new ServiceController();

        prefs= nonStaticUtils.loadLoginInfo(this);

       // serviceController.imageRequest("https://pbs.twimg.com/profile_images/415419569377775616/5-NAT78O_400x400.png",loginImage,0,0);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){

                    sendUserRequestByName(username.getText().toString());

                }
                else{

                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario y contraseña requeridos", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        });




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UserRegister.class);
                startActivity(intent);


            }
        });


    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            moveTaskToBack(true);
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }


    public void sendUserRequestByName(String username){
        serviceController = new ServiceController();
        String url = "http://beuserdev.cfapps.io/user/"+username;
        Map<String,String> nullMap =  new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type","application/json");


        serviceController.jsonObjectRequest(url, Request.Method.GET, null,map, response, responseError);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu., menu);
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
    @Override
    public void onResponse(JSONObject response) {

        Log.d("Response", response.toString());


        prefs = nonStaticUtils.loadLoginInfo(this);

        try
        {
                String requestPassword = Utils.setEncryptedText(password.getText().toString());

                if (response.getString("password").equals(requestPassword)) {

                    if (response.getBoolean("enable")) {

                        isAuthenticated = true;
                        nonStaticUtils.saveLogin(this,response.getString("user"), response.getString("password"), response.getString("id"), response.getInt("total_gift_points"), isAuthenticated);
                        Intent intent = new Intent(getApplicationContext(), BackgroundScanActivity.class);
                        startActivity(intent);

                    } else {

                        Toast toast = Toast.makeText(getApplicationContext(), "Usuario deshabilitado", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT);
                    toast.show();
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
        Toast toast = Toast.makeText(getApplicationContext(), "Error procesando la solicitud", Toast.LENGTH_SHORT);
        toast.show();
    }
}
