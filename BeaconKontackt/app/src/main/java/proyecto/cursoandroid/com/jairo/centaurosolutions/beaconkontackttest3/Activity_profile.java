package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import model.cache.BeaconCache;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.Adaptador_Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;
import utils.Utils;

public class Activity_profile extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    String id_user;
    ServiceController serviceController;
    EditText edit_Name;
    EditText edit_LastName;
    EditText edit_Email;
    EditText edit_Phone;
    private DatePicker datePicker;
    private Calendar calendar;
    private Button dateView;
    private int year, month, day;

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
        id_user = intent1.getStringExtra("idUser");
        serviceController = new ServiceController();
        responseError = this;
        response = this;
        edit_Name = (EditText) findViewById(R.id.name);
        edit_LastName = (EditText) findViewById(R.id.lastname);
        edit_Email = (EditText) findViewById(R.id.email);
        edit_Phone = (EditText) findViewById(R.id.phone);
        dateView = (Button) findViewById(R.id.buttonbirthday);
        dateView.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                showDialog(999);
                Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                        .show();

            }
        });
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(formatDate(year, month, day));
        sendUserRequestById(id_user);
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

    @SuppressWarnings("deprecation")
    public void setDate(View view) {

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
                edit_Name.setText(response.getString("name"));
                edit_LastName.setText(response.getString("lastName"));
                edit_Email.setText(response.getString("email"));
                edit_Phone.setText(response.getString("phone"));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
    }


}
