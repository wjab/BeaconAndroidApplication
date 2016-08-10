package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

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

public class LoginMainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    public static CallbackManager callbackmanager;
    //ImageButton facebookLogin;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
   // LoginButton loginButtonFace;

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    ImageView loginImage;
    TextView username;
    LinearLayout register;
    TextView password;
    //TextView register;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    boolean isAuthenticated;
    NonStaticUtils nonStaticUtils;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_main);
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar_login_layout,
                null);
        actionBarLayout.setBackgroundColor(Color.TRANSPARENT);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);

        TextView pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        ImageButton imageButton= (ImageButton) actionBarLayout.findViewById(R.id.back_action);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setElevation(0);
        /* se agrega el callback y el boton que hace el login con facebook */
        callbackmanager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);

        nonStaticUtils =  new NonStaticUtils();
        login = (Button)findViewById(R.id.login);
        loginImage = (ImageView)findViewById(R.id.loginImage);
        username = (TextView) findViewById(R.id.usuario);
        password = (TextView) findViewById(R.id.password);
        register = (LinearLayout) findViewById(R.id.layout_register);

        responseError = this;
        response = this;
        serviceController =  new ServiceController();

        prefs = nonStaticUtils.loadLoginInfo(this);

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

    public void sendUserRequestByName(String username)
    {
        serviceController = new ServiceController();
        String url = getString(R.string.WebService_User) + "user/username";
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("username", username);
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type","application/json");

        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
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
            JSONObject currRange= response.getJSONObject("user");

                if (currRange.getString("password").equals(requestPassword)) {

                    if (currRange.getBoolean("enable")) {
                        isAuthenticated = true;
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
                                isAuthenticated,
                                currRange.getString("socialNetworkType"),
                                currRange.getString("socialNetworkId"),
                                currRange.getString("pathImage"),
                                (currRange.getString("name") != null ? currRange.getString("name").toString() : ""),
                                (currRange.getString("lastName") != null ? currRange.getString("lastName").toString() : ""),
                                (currRange.getString("phone") != null ? currRange.getString("phone").toString() : ""),
                                (currRange.getString("email") != null ? currRange.getString("email").toString() : ""),
                                (currRange.getString("gender") != null ? currRange.getString("gender").toString() : ""),
                                (map.get("birthday") != null ? map.get("birthday").toString() : ""));
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
        catch (Exception ex)
        {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

}
