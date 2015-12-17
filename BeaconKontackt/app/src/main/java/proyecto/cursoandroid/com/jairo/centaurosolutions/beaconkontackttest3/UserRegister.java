package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import controllers.ServiceController;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);


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

                if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){

                    //sendUserRequestByName(username.getText().toString());

                }
                else{

                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario y contraseña requeridos", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        });
    }

    public Boolean validateFields(){
       // if(!username.getText().toString().isEmpty() || !password.getText().toString().isEmpty() || !name.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() || !phone.getText().toString().isEmpty() && !email.getText().toString().isEmpty())
        return  true;
    }

    @Override
    public void onResponse(JSONObject response) {

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
