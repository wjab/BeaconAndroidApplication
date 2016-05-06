package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.Adaptador_Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;

public class TabPromocionesActivity extends Activity implements Response.Listener<JSONObject>, Response.ErrorListener {

    Adaptador_Promo adapter;
    ListView listviewPromo;
    EditText search;
    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    ArrayList<Promociones> promociones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_promociones);
        listviewPromo = (ListView) findViewById(R.id.listPromo);
        listviewPromo.setAdapter(adapter);


        search = (EditText) findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        responseError = this;
        response = this;
        serviceController =  new ServiceController();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_Promo)+"promo";

        serviceController.jsonObjectRequest(url, Request.Method.GET, null,map, response, responseError);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_promociones, menu);
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

        try{
            JSONArray promoList = response.getJSONArray("Promo");
            promociones = new ArrayList<Promociones>();

            for (int i=0; i < promoList.length(); i++ ) {
                JSONObject row = promoList.getJSONObject(i);

                ArrayList<String> images = new ArrayList<String>();
                String url = "";

                Promociones promo;
                promo= new Promociones();
                promo.setTitulo(row.getString("title"));
                promo.setDescripcion(row.getString("description"));
                promo.setPuntos(row.getInt("gift_points"));
                promo.setId(row.getString("id"));
                if(row.get("images") != null)
                {
                    url = row.getString("images");
                    promo.setUrlImagen(url);
                }

                promociones.add(promo);
            }

            adapter = new Adaptador_Promo(this, promociones);
        }
        catch(Exception ex){

        }

        listviewPromo.setAdapter(adapter);

        Log.d("Response", response.toString());
     }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
    }

}
