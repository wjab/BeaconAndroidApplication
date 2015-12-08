package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import service.BeaconSyncMessageService;
import utils.Utils;

public class LoginMainActivity extends Activity implements Response.Listener<JSONObject>, Response.ErrorListener{


    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    ImageView loginImage;
    TextView username;
    TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_main);

        Button login = (Button)findViewById(R.id.login);
        loginImage = (ImageView)findViewById(R.id.loginImage);

        responseError = this;
        response = this;
        serviceController =  new ServiceController();
        Intent intent = new Intent(this, BeaconSyncMessageService.class);
        startService(intent);


       // serviceController.imageRequest("https://pbs.twimg.com/profile_images/415419569377775616/5-NAT78O_400x400.png",loginImage,0,0);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username = (TextView) findViewById(R.id.usuario);
                password = (TextView) findViewById(R.id.password);
                String userText = username.getText().toString();
                String passText = password.getText().toString();

                if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                    serviceController = new ServiceController();
                    String url = "http://buserdev.cfapps.io/user/"+userText;
                    Map<String,String> nullMap =  new HashMap<String, String>();

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("Content-Type","application/json");


                    serviceController.jsonObjectRequest(url, Request.Method.GET, null,map, response, responseError);
                }
                else{

                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario y contraseña requeridos", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        });
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

        try
        {

            String requestPassword = Utils.setEncryptedText(password.getText().toString());

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
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
    }





}
