package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import utils.NonStaticUtils;
import utils.Utils;

public class LoginMainActivity extends Activity implements Response.Listener<JSONObject>, Response.ErrorListener{

    public static CallbackManager callbackmanager;
    ImageButton facebookLogin;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;

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
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_main);

        /* se agrega el callback y el boton que hace el login con facebook */
        callbackmanager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);
        facebookLogin = (ImageButton) findViewById(R.id.facebook_icon);


        nonStaticUtils =  new NonStaticUtils();
        login = (Button)findViewById(R.id.login);
        loginImage = (ImageView)findViewById(R.id.loginImage);
        username = (TextView) findViewById(R.id.usuario);
        password = (TextView) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.register);

        responseError = this;
        response = this;
        serviceController =  new ServiceController();

        prefs= nonStaticUtils.loadLoginInfo(this);

        /* Gets aplication hash*/
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {
        }
        catch (NoSuchAlgorithmException e) {
        }



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



        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFblogin();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UserRegister.class);
                startActivity(intent);


            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();

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
        String url = getString(R.string.WebService_User)+"user/"+username;
       
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



    // Private method to handle Facebook login and callback
    private void onFblogin()
    {
        //callbackmanager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile", "user_friends"));

        LoginManager.getInstance().registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                System.out.println("------- Success -------");
                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject json, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                    System.out.println(".......ERROR.....");
                                } else {
                                    System.out.println("......Success.......");
                                    try {

                                        String jsonresult = String.valueOf(json);
                                        System.out.println("JSON Result" + jsonresult);

                                        if (json.getString("name") != null) {
                                            //str_name = json.getString("name");
                                            Toast toast = Toast.makeText(getApplicationContext(), "User " + json.getString("name"), Toast.LENGTH_SHORT);
                                            toast.show();
                                        }

                                        if (json.getString("id") != null) {
                                            //str_id = json.getString("id");
                                            Toast toast = Toast.makeText(getApplicationContext(), "Id-Facebook = " + json.getString("id") , Toast.LENGTH_SHORT);
                                            toast.show();
                                        }

                                    /*if(json.getString("email") != null) { str_email = json.getString("email"); }
                                    if(json.getString("first_name") != null) { str_firstname = json.getString("first_name"); }
                                    if(json.getString("last_name") != null) { str_lastname = json.getString("last_name"); }*/
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }).executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("onCancel", "On cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("onError", error.toString());
            }
        });


    }

}
