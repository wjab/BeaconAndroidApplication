package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;



import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import model.cache.BeaconCache;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.Adaptador_Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;


public class PullNotificationsActivity extends AppCompatActivity {

    Adaptador_Promo adapter;
    ListView listviewPromo;


    ArrayList<Promociones> promociones;
    ArrayList<BeaconCache> myBeaconCacheList;
    private CharSequence mpoints;
    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_notifications);

        listviewPromo = (ListView) findViewById(R.id.listNotifications);
        // Inflate your custom layout

        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar_notification_layout,
                null);
        mpoints = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getInt("points", 0)+"";
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);

        TextView pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        pointsAction.setText(mpoints + " pts");

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

        Intent intent1= getIntent();
        myBeaconCacheList = (ArrayList<BeaconCache>)intent1.getSerializableExtra("promoDetail");
        llenarNotificaciones(myBeaconCacheList);
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
