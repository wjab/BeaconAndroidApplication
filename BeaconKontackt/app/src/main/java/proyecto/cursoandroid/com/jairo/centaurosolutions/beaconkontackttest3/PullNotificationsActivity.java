package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.cache.BeaconCache;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.Adaptador_Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;
import utils.Utils;


public class PullNotificationsActivity extends AppCompatActivity {

    Adaptador_Promo adapter;
    ListView listviewPromo;

    private String idUser, userAcumulatedPoints;
    LinearLayout back;
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
                R.layout.action_bar_promodetail,
                null);
        mpoints = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getInt("points", 0) + "";
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        TextView pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        back = (LinearLayout) actionBarLayout.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirectIntent = new Intent(getApplicationContext(), BackgroundScanActivity.class);
                myBeaconCacheList = new ArrayList<BeaconCache>();
                redirectIntent.putExtra("promoDetail", myBeaconCacheList);
                startActivity(redirectIntent);
                finish();

            }
        });

        pointsAction.setText(userAcumulatedPoints.toString());
        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mpoints.toString().equals("0")) {
                    Toast.makeText(getApplication(), getString(R.string.dontHavePoints), Toast.LENGTH_SHORT).show();
                } else {
                    openHistory();
                }
            }
        });
        pointsAction.setText(mpoints + " pts");

        Intent intent1 = getIntent();
        myBeaconCacheList = (ArrayList<BeaconCache>) intent1.getSerializableExtra("promoDetail");
        llenarNotificaciones(myBeaconCacheList);

        listviewPromo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Promociones promo = new Promociones();
                BeaconCache listitem = myBeaconCacheList.get(position);
                promo.setTitulo(listitem.title);
                promo.setDescripcion(Utils.StringDecode64(listitem.descrition));
                promo.setPuntos(listitem.giftPoints);
                promo.setId(listitem.promoId);
                if (listitem.picturePath != null) {
                    String url = listitem.picturePath;
                    promo.setUrlImagen(url);
                }

                Intent intentSuccess = new Intent(getApplicationContext(), DetailPromo.class);
                intentSuccess.putExtra("Detail", promo);
                intentSuccess.putExtra("promoDetail", myBeaconCacheList);
                startActivity(intentSuccess);
            }
        });

        NotificationManagerCompat notifManager = NotificationManagerCompat.from(this);
        notifManager.cancelAll();

    }

    public void openHistory() {
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            Intent redirectIntent = new Intent(getApplicationContext(), BackgroundScanActivity.class);
            myBeaconCacheList = new ArrayList<BeaconCache>();
            redirectIntent.putExtra("promoDetail", myBeaconCacheList);
            startActivity(redirectIntent);
            this.finish();
        } else {
            Toast.makeText(this, getString(R.string.pressBackAgainToExit),
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

        try {
            Log.d("PromoList", "Size:" + myBeaconCacheList.size());

            if (myBeaconCacheList.size() > 0) {
                promociones = new ArrayList<Promociones>();

                for (BeaconCache myCache : myBeaconCacheList) {

                    ArrayList<String> images = new ArrayList<String>();
                    String url = "";
                    if (myCache.descrition != null && myCache.title != null) {
                        Promociones promo;
                        promo = new Promociones();
                        promo.setTitulo(myCache.title);
                        promo.setDescripcion(Utils.StringDecode64(myCache.descrition));
                        promo.setPuntos(myCache.giftPoints);
                        promo.setId(myCache.promoId);
                        if (myCache.picturePath != null) {
                            url = myCache.picturePath;
                            promo.setUrlImagen(url);
                        }

                        promociones.add(promo);
                    }
                }

                if (promociones.size() > 0) {
                    adapter = new Adaptador_Promo(this, promociones);
                    listviewPromo.setAdapter(adapter);
                } else {
                    Intent i = new Intent(getApplicationContext(), BackgroundScanActivity.class);
                    startActivity(i);
                }
            } else {
                Intent i = new Intent(getApplicationContext(), BackgroundScanActivity.class);
                startActivity(i);
            }
        } catch (Exception ex) {
            Log.d("PromoList", ex.getMessage());
        }
    }
}
