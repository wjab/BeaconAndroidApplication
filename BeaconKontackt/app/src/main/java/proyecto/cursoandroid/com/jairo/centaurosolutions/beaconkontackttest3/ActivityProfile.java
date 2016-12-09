package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Preference;
import utils.NonStaticUtils;
import utils.Utils;


public class ActivityProfile extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    String idUser;
    ServiceController serviceController;
    EditText editName, editLastName, editEmail, editPhone;
    TextView pointsAction;
    Button updateInfo;
    boolean isFacebook;
    String idFacebook;
    private DatePicker datePicker;
    private Calendar calendar;
    private Button dateView, addImage;
    private int year, month, day;
    RadioButton male;
    RadioButton female;
    LinearLayout form, back;
    String mpoints, userAcumulatedPoints;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    ImageView openHistoryPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);

        mpoints = String.valueOf(preferences.getInt("points", 0));
        ServiceController imageRequest = new ServiceController();
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        idUser = preferences.getString("userId", "");
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_promodetail, null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        openHistoryPoints = (ImageView) actionBarLayout.findViewById(R.id.openHistoryPoints);
        back = (LinearLayout) actionBarLayout.findViewById(R.id.back);
        addImage = (Button) actionBarLayout.findViewById(R.id.buttonAdd);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WishListActivity.class);
                intent.putExtra("idUser", idUser);
                intent.putExtra("code", 1);
                startActivityForResult(intent, 2);
            }
        });
        addImage.setText(String.valueOf(BackgroundScanActivity.size));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("code", 1);
                setResult(2, intent);
                finish();
            }
        });

        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);

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
            }
        });

        updateInfo.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                if (isFacebook) {
                    getFacebookIntent();
                }
                else
                {
                    updateUserLocalData();
                }
            }
        });
        pointsAction.setText(userAcumulatedPoints.toString());

        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpoints.equals("0"))
                {
                    Toast.makeText(getApplication(), getString(R.string.dontHavePoints), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    openHistory();
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
    public void openHistory(){
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser",idUser);
        intent.putExtra("code", 1);
        startActivityForResult(intent, 2);
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
        StringBuilder Date_Formated = new StringBuilder().append(day).append(" / ").append(month).append(" / ").append(year);
        return Date_Formated;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int code = data.getIntExtra("code", 0);
        if (code == 1) {
            super.onActivityResult(requestCode, resultCode, data);
            addImage.setText(String.valueOf(BackgroundScanActivity.size));
        }
        else {
            callbackmanager.onActivityResult(requestCode, resultCode, data);
        }
    }

    List<String> permissionNeeds = Arrays.asList("user_photos", "email", "user_birthday", "user_friends");
    public static CallbackManager callbackmanager;
    Map<String, Object> map;
    Gson gson = new Gson();
    Type stringStringMap = new TypeToken<Map<String, Object>>() {
    }.getType();

    @SuppressWarnings("deprecation")
    public void getinfo() {
        preferences = nonStaticUtils.loadLoginInfo(this);

        if (!preferences.getString("name", "").equals("")) {
            editName.setText(preferences.getString("name", ""));
        }
        if (!preferences.getString("lastName", "").equals("")) {
            editLastName.setText(preferences.getString("lastName", ""));
        }
        if (!preferences.getString("email", "").equals("")) {
            editEmail.setText(preferences.getString("email", ""));
        }
        if (!preferences.getString("gender", "").equals("")) {
            String gender = preferences.getString("gender", "");
            if (gender.equals("male")) {
                male.setChecked(true);
                female.setChecked(false);
            } else {
                male.setChecked(false);
                female.setChecked(true);
            }
        }
        editPhone.setText("No disponible");
        if (!preferences.getString("birthday", "").equals("")) {
            String birthday = preferences.getString("birthday", "");
            String[] array = birthday.split("/");
            showDate(formatDate(Integer.parseInt(array[2]), Integer.parseInt(array[0]), Integer.parseInt(array[1])));

        }
        enableForm();
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
    public void onResponse(JSONObject response)
    {
        boolean femaleGender = false;

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
                            if(!response.getString("gender").isEmpty())
                            {
                                femaleGender = response.getString("gender").equals("female") ? true : false;
                            }

                            female.setChecked(femaleGender);
                            male.setChecked(!femaleGender);

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
    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent();
        intent.putExtra("code",1);
        setResult(2,intent);
        finish();
        super.onBackPressed();
    }

    public void updateUserLocalData()
    {
        serviceController = new ServiceController();
        String url = getString(R.string.WebService_User) + "user/" + idUser;
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", editName.getText().toString());
        userMap.put("lastName", editLastName.getText().toString());
        userMap.put("phone", editPhone.getText().toString());
        userMap.put("creationDate", Utils.convertLongToDate(new Date().getTime()));
        userMap.put("modifiedDate", Utils.convertLongToDate(new Date().getTime()));
        userMap.put("gender", (male.isChecked() ? "male" : "female"));
        userMap.put("pathImage", preferences.getString("pathImage", "") );

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        serviceController.jsonObjectRequest(url, Request.Method.PUT, userMap, map, response, responseError);
    }
}
