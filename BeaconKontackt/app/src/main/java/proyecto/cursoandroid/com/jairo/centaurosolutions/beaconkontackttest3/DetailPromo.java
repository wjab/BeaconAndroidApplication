package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import controllers.ServiceController;
import model.cache.BeaconCache;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;
import utils.NonStaticUtils;

public class DetailPromo extends AppCompatActivity
{
    TextView tituloPromo, descripcionPromo, points, pointsAction;
    ImageView imagenPromo, openHistoryPoints,share;
    String mpoints, userAcumulatedPoints;
    ArrayList<BeaconCache> myBeaconCacheList;
    ImageButton wishes;
    Intent intent;

    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__promo);
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /* Obtiene de las preferencias compartidas, la cantidad de los puntos*/
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        mpoints = preferences.getInt("points", 0) + "";
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        idUser = preferences.getString("userId", "");
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_promodetail, null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);


        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        pointsAction.setText(userAcumulatedPoints.toString());

        tituloPromo = (TextView) findViewById(R.id.tituloPromo);
        points = (TextView) findViewById(R.id.puntosPromoDetail);
        descripcionPromo = (TextView) findViewById(R.id.descriptionPromoDetai);
        imagenPromo = (ImageView) findViewById(R.id.imagenPromoDetail);
        intent = getIntent();
        openHistoryPoints=(ImageView) actionBarLayout.findViewById(R.id.openHistoryPoints);
        share=(ImageView)findViewById(R.id.share);
        Promociones promo = (Promociones)intent.getSerializableExtra("Detail");
        ServiceController imageRequest =  new ServiceController();
        points.setText(promo.getPuntos() + " pts");
        tituloPromo.setText(promo.getTitulo());
        descripcionPromo.setText(promo.getDescripcion());
        imageRequest.imageRequest(promo.getUrlImagen(), imagenPromo, 0, 0);
        final String description= descripcionPromo.getText().toString()+" "+promo.getUrlImagen();
        final String image=promo.getUrlImagen();

        openHistoryPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpoints.toString().equals("0"))
                {
                    Toast.makeText(getApplication(), "Aun no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    openHistory();
                }
            }
        });
        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpoints.toString().equals("0"))
                {
                    Toast.makeText(getApplication(), "Aun no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    openHistory();
                }
        }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, description);

                startActivity(Intent.createChooser(intent, "Share with"));

            }
        });


        myBeaconCacheList = (ArrayList<BeaconCache>)intent.getSerializableExtra("promoDetail");

        if(myBeaconCacheList != null)
        {
            int indextoDelete = -1;
            for (int i = 0; i < myBeaconCacheList.size(); i++ )
            {
                if(myBeaconCacheList.get(i).promoId.equals(promo.getId()))
                {
                    indextoDelete=i;
                }
            }
            if( indextoDelete != -1)
            {
                myBeaconCacheList.remove(indextoDelete);
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed()
    {
        if(myBeaconCacheList != null)
        {
            Intent redirectIntent = new Intent(getApplicationContext(), PullNotificationsActivity.class);
            redirectIntent.putExtra("promoDetail", myBeaconCacheList);
            startActivity(redirectIntent);
        }
        else
        {
            this.finish();
        }
    }
    public void openHistory(){
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser",idUser);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
