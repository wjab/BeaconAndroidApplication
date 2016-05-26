package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    EditText edit_User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent1 = getIntent();
        id_user = intent1.getStringExtra("idUser");
        serviceController = new ServiceController();
        responseError = this;
        response = this;
        edit_Name = (EditText) findViewById(R.id.name);
        edit_LastName = (EditText) findViewById(R.id.lastname);
        edit_Email = (EditText) findViewById(R.id.email);
        edit_Phone = (EditText) findViewById(R.id.phone);
        edit_User = (EditText) findViewById(R.id.user);
        sendUserRequestById(id_user);

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
            if (response.getBoolean("enable")) {
                edit_Name.setText(response.getString("name"));
                edit_LastName.setText(response.getString("lastName"));
                edit_Email.setText(response.getString("email"));
                edit_Phone.setText(response.getString("phone"));
                edit_User.setText(response.getString("user"));

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
