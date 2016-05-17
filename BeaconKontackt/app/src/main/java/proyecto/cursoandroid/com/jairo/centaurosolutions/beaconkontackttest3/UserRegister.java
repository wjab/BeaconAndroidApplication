package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import utils.InputValidatorHelper;
import utils.Utils;

public class UserRegister extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    private TextView username;
    private TextView password;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView lastName;
    Button register;
    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    InputValidatorHelper validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        validator = new InputValidatorHelper();


        register = (Button)findViewById(R.id.register);
        username = (TextView) findViewById(R.id.user);
        password = (TextView) findViewById(R.id.password);
        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        lastName = (TextView) findViewById(R.id.lastname);

        responseError = this;
        response = this;
        serviceController =  new ServiceController();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  if(validateFields()){
                      sendCreateUserRequest();
                  };
            }
        });
    }

    public Boolean validateFields(){
        int error = 0;
        if(!validator.isValidUsername(username.getText().toString())) {
            username.setError("El usuario debe estar compuesto por letras y números");
            error++;
        }
        if(!validator.isValidPassword(password.getText().toString())) {
            password.setError("La longitud mínima debe ser de al menos 6 caracteres");
            error++;
        }
        if(!validator.isNumeric(phone.getText().toString())) {
            phone.setError("Número de teléfono inválido");
            error++;
        }
        if(!validator.isValidWord(name.getText().toString())) {
            name.setError("El campo debe estar compuesto por letras");
            error++;
        }
        if(!validator.isValidWord(lastName.getText().toString())) {
            lastName.setError("El campo debe estar compuesto por letras");
            error++;
        }
        if(!validator.isValidEmail(email.getText().toString())){
            email.setError("Dirección de correo inválida");
            error++;
        }

        return  error == 0;
    }

    public void sendCreateUserRequest(){
        serviceController = new ServiceController();
        String url = getString(R.string.WebService_User)+"user";

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("user", username.getText().toString());
        mapParams.put("password", password.getText().toString());
        mapParams.put("enable", "true");
        mapParams.put("category_id", "0");
        mapParams.put("total_gift_points", "0");
        mapParams.put("name", name.getText().toString());
        mapParams.put("lastName", lastName.getText().toString());
        mapParams.put("phone", phone.getText().toString());
        mapParams.put("creationDate", Utils.convertLongToDate(new Date().getTime()));
        mapParams.put("modifiedDate", Utils.convertLongToDate(new Date().getTime()));
        mapParams.put("email", email.getText().toString());
        mapParams.put("socialNetworkId","");
        mapParams.put("socialNetworkType","");
        mapParams.put("socialNetworkJson","");


        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type","application/json");


        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }

    @Override
    public void onResponse(JSONObject response) {

        try {
            if(response.getJSONObject("user")!=null){
                Toast toast = Toast.makeText(getApplicationContext(), "Usuario creado correctamente",Toast.LENGTH_LONG);
                toast.show();

                Intent intent =new Intent(getApplicationContext(),LoginMainActivity.class );
                startActivity(intent);


            }
        } catch (JSONException e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error procesando la solicitud", Toast.LENGTH_LONG);
            toast.show();
            Intent intent =new Intent(getApplicationContext(),UserRegister.class );
            startActivity(intent);
        }
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
        Toast toast = Toast.makeText(getApplicationContext(), "Error procesando la solicitud", Toast.LENGTH_SHORT);
        toast.show();
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
