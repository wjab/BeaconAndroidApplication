package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.ServiceController;


public class ActivityProfile extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    String idUser;
    ServiceController serviceController;
    EditText editName;
    EditText editLastName;
    EditText editEmail;
    EditText editPhone;
    Button updateInfo;
    boolean isFacebook;
    String idFacebook;
    private DatePicker datePicker;
    private Calendar calendar;
    private Button dateView;
    private int year, month, day;
    RadioButton male;
    RadioButton female;
    LinearLayout form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        //getSupportActionBar().setCustomView(actionBarLayout);
        Intent intent1 = getIntent();
        idUser = intent1.getStringExtra("idUser");
        serviceController = new ServiceController();
        responseError = this;
        response = this;
        editName = (EditText) findViewById(R.id.name);
        editLastName = (EditText) findViewById(R.id.lastname);
        editEmail = (EditText) findViewById(R.id.email);
        editPhone = (EditText) findViewById(R.id.phone);
        dateView = (Button) findViewById(R.id.buttonbirthday);
        updateInfo = (Button) findViewById(R.id.update);
        male = (RadioButton) findViewById(R.id.radioM);
        female = (RadioButton) findViewById(R.id.radioF);
        form = (LinearLayout) findViewById(R.id.form_Profile);
        dateView.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                showDialog(999);
                Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                        .show();

            }
        });
        updateInfo.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                if (isFacebook) {
                    getFacebookIntent();
                }

            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(formatDate(year, month, day));
        sendUserRequestById(idUser);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            StringBuilder stringdate = formatDate(arg1, arg2, arg3);
            showDate(stringdate);
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void startNewActivity(String packageName) {
        Intent intent = this.getPackageManager().getLaunchIntentForPackage(packageName);

        if (intent != null) {
            // we found the activity
            // now start the activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            // bring user to the market
            // or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            startActivity(intent);
        }
    }

    public void getFacebookIntent() {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            intent.setData(Uri.parse("fb://profile/"));
        } catch (Exception e) {
            intent.setData(Uri.parse("https://www.facebook.com/" + idFacebook));
        }
        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser
        final PackageManager packageManager = getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() == 0) {
            final String urlBrowser = "https://www.facebook.com/pages/" + idFacebook;
            intent.setData(Uri.parse(urlBrowser));
        }
        startActivity(intent);
    }

    private void showDate(StringBuilder stringdate) {
        dateView.setText(stringdate);
    }

    private StringBuilder formatDate(int year, int month, int day) {
        String String_Month = "";
        switch (month) {
            case 1: {
                String_Month = "Enero";
                break;
            }
            case 2: {
                String_Month = "Febrero";
                break;
            }
            case 3: {
                String_Month = "Marzo";
                break;
            }
            case 4: {
                String_Month = "Abril";
                break;
            }
            case 5: {
                String_Month = "Mayo";
                break;
            }
            case 6: {
                String_Month = "Junio";
                break;
            }
            case 7: {
                String_Month = "Julio";
                break;
            }
            case 8: {
                String_Month = "Agosto";
                break;
            }
            case 9: {
                String_Month = "Setiembre";
                break;
            }
            case 10: {
                String_Month = "Octubre";
                break;
            }
            case 11: {
                String_Month = "Noviembre";
                break;
            }
            case 12: {
                String_Month = "Diciembre";
                break;
            }
        }
        StringBuilder Date_Formated = new StringBuilder().append(day).append(" de ").append(String_Month).append(" de ").append(year);
        return Date_Formated;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }

    List<String> permissionNeeds = Arrays.asList("user_photos", "email", "user_birthday", "user_friends");
    public static CallbackManager callbackmanager;
    Map<String, Object> map;
    Gson gson = new Gson();
    Type stringStringMap = new TypeToken<Map<String, Object>>() {
    }.getType();

    @SuppressWarnings("deprecation")
    public void getinfo() {

        callbackmanager = CallbackManager.Factory.create();

        FacebookSdk.sdkInitialize(getApplicationContext());

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, permissionNeeds);


        LoginManager.getInstance().registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                System.out.println("------- Success -------");
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,gender,first_name,last_name,name,id,birthday");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject json, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error

                                } else {
                                    try {
                                        String jsonresult = String.valueOf(json);
                                        System.out.println("JSON Result" + jsonresult);
                                        map = gson.fromJson(jsonresult, stringStringMap);
                                        if (map.get("first_name") != null) {
                                            editName.setText(json.getString("first_name"));
                                        }
                                        if (map.get("last_name") != null) {
                                            editLastName.setText(json.getString("last_name"));
                                        }
                                        if (map.get("email") != null) {
                                            editEmail.setText(json.getString("email"));
                                        }
                                        if (map.get("gender") != null) {
                                            String gender = json.getString("gender");
                                            if (gender.equals("male")) {
                                                male.setChecked(true);
                                                female.setChecked(false);
                                            } else {
                                                male.setChecked(false);
                                                female.setChecked(true);
                                            }
                                        }
                                        editPhone.setText("No disponible");
                                        if (map.get("birthday") != null) {
                                            String gender = json.getString("birthday");
                                            String[] array = gender.split("/");
                                            showDate(formatDate(Integer.parseInt(array[1]), Integer.parseInt(array[0]), Integer.parseInt(array[2])));

                                        }
                                        enableForm();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        });
                request.setParameters(parameters);
                request.executeAsync();
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

    public void enableForm() {
        editName.setEnabled(false);
        editLastName.setEnabled(false);
        editEmail.setEnabled(false);
        male.setEnabled(false);
        female.setEnabled(false);
        editPhone.setEnabled(false);
        dateView.setEnabled(false);
    }

    public void sendUserRequestById(String id_user) {
        serviceController = new ServiceController();
        String url = getString(R.string.WebService_User) + "user/id/" + id_user;
        Map<String, String> nullMap = new HashMap<String, String>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Log.d("Response", response.toString());
            response = response.getJSONObject("user");
            if (response.getBoolean("enable")) {
                try {
                    String socialNetworkType = response.getString("socialNetworkType");
                    if (socialNetworkType.equals("facebook")) {
                        isFacebook = true;
                        idFacebook = response.getString("socialNetworkId");
                        getinfo();
                        updateInfo.setText("Ir a Perfil");
                    } else {
                        isFacebook = false;
                        idFacebook = "";
                        updateInfo.setText("Actualizar Información");
                        editName.setText(response.getString("name"));
                        editLastName.setText(response.getString("lastName"));
                        editEmail.setText(response.getString("email"));
                        editPhone.setText(response.getString("phone"));
                        try {
                            if (!response.getString("creationDate").isEmpty()) {
                                Date date = new Date(Long.parseLong(response.getString("creationDate")));
                                Calendar calendar = new GregorianCalendar();
                                calendar.setTime(date);
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH) + 1;
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                showDate(formatDate(year, month, day));
                            }
                        } catch (Exception e) {
                            calendar = Calendar.getInstance();
                            year = calendar.get(Calendar.YEAR);
                            month = calendar.get(Calendar.MONTH) + 1;
                            day = calendar.get(Calendar.DAY_OF_MONTH);
                            showDate(formatDate(year, month, day));
                        }
                    }
                } catch (Exception ex) {
                    isFacebook = false;
                    idFacebook = "";
                    updateInfo.setText("Actualizar Información");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
    }


}
