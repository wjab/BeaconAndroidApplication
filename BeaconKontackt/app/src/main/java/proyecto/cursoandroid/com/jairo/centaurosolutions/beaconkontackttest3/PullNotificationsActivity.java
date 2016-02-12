package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import model.cache.BeaconCache;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.Adaptador_Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;

public class PullNotificationsActivity extends Activity {

    Adaptador_Promo adapter;
    ListView listviewPromo;
    EditText search;
    ServiceController serviceController;

    Response.ErrorListener responseError;
    ArrayList<Promociones> promociones;
    ArrayList<BeaconCache> myBeaconCacheList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_promociones);
        listviewPromo = (ListView) findViewById(R.id.listPromo);
        listviewPromo.setAdapter(adapter);

        listviewPromo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Promociones promo = new Promociones();
                BeaconCache listitem = myBeaconCacheList.get(position);
                promo.setTitulo(listitem.title);
                promo.setDescripcion(listitem.descrition);
                promo.setPuntos(listitem.giftPoints);
                promo.setId(listitem.id);
                if(listitem.picturePath != null)
                {
                    String url = listitem.picturePath;
                    promo.setUrlImagen(url);
                }

                Intent intentSuccess = new Intent(getApplicationContext(), Detail_Promo.class);
                intentSuccess.putExtra("Detail", promo);
                startActivity(intentSuccess);
            }
        });
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


        Intent intent1= getIntent();
        myBeaconCacheList = (ArrayList<BeaconCache>)intent1.getSerializableExtra("promoDetail");
        llenarNotificaciones(myBeaconCacheList);
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
    //llenar notificaciones
    public void llenarNotificaciones(ArrayList<BeaconCache> myBeaconCacheList) {

        try{

            promociones = new ArrayList<Promociones>();

            for (int i=0; i < myBeaconCacheList.size(); i++ ) {


                ArrayList<String> images = new ArrayList<String>();
                String url = "";

                Promociones promo;
                promo= new Promociones();
                promo.setTitulo(myBeaconCacheList.get(i).title);
                promo.setDescripcion(myBeaconCacheList.get(i).descrition);
                promo.setPuntos(myBeaconCacheList.get(i).giftPoints);
                promo.setId(myBeaconCacheList.get(i).id);
                if(myBeaconCacheList.get(i).picturePath != null)
                {
                    url = myBeaconCacheList.get(i).picturePath;
                    promo.setUrlImagen(url);
                }

                promociones.add(promo);
            }

            adapter = new Adaptador_Promo(this, promociones);
        }
        catch(Exception ex){

        }

        listviewPromo.setAdapter(adapter);


    }



}
