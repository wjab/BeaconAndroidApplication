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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import controllers.ServiceController;
import utils.NonStaticUtils;
import utils.Utils;


public class Login_Options extends Activity implements Response.Listener<JSONObject>, Response.ErrorListener{

    public static CallbackManager callbackmanager;
    ImageButton facebookLogin;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    LoginButton loginButtonFace;
    ServiceController serviceController;
    ImageView loginImage;
    ImageButton userButton;
    SharedPreferences prefs;
    NonStaticUtils nonStaticUtils;
    Collection<String> arraysPreferences = new ArrayList<String>(Arrays.asList("email", "user_photos", "public_profile", "user_friends"));

    Gson gson = new Gson();
    Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
    Map<String,String> map;


    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login__options);

        /* se agrega el callback y el boton que hace el login con facebook */
        callbackmanager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);
        facebookLogin = (ImageButton) findViewById(R.id.facebook_icon);

        loginButtonFace = (LoginButton) findViewById(R.id.login_button_facebook);
        loginButtonFace.setReadPermissions((ArrayList<String>)arraysPreferences); //"user_friends"


        nonStaticUtils =  new NonStaticUtils();

        loginImage = (ImageView)findViewById(R.id.loginImage);

        userButton = (ImageButton)findViewById(R.id.user_icon);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginMainActivity.class);
                startActivity(intent);

            }
        });


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







        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFblogin();
            }
        });

        loginButtonFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFblogin();
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




    // Private method to handle Facebook login and callback
    private void onFblogin()
    {
        //callbackmanager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, arraysPreferences);

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

                                        map = gson.fromJson(jsonresult, stringStringMap);

                                        if (map.get("name") != null) {
                                            Toast toast = Toast.makeText(getApplicationContext(), "User " + json.getString("name"), Toast.LENGTH_SHORT);
                                            toast.show();
                                        }

                                        if (map.get("id") != null) {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Id-Facebook = " + json.getString("id"), Toast.LENGTH_SHORT);
                                            toast.show();
                                        }



                                    /*if(map.get("email") != null) { str_email = map.get("email"); }
                                    if(map.get("first_name") != null) { str_firstname = map.get("first_name"); }
                                    if(map.get("last_name") != null) { str_lastname = map.get("last_name"); }*/

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

    @Override
    public void onResponse(JSONObject response) {

        Log.d("Response", response.toString());


        prefs = nonStaticUtils.loadLoginInfo(this);

        try
        {
            /*String requestPassword = Utils.setEncryptedText(password.getText().toString());

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
            }*/
        }
        catch (Exception ex){
            /*Toast toast = Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT);
            toast.show();*/
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
        Toast toast = Toast.makeText(getApplicationContext(), "Error procesando la solicitud", Toast.LENGTH_SHORT);
        toast.show();
    }

}