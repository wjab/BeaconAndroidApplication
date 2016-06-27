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

public class Detail_Promo extends AppCompatActivity
{
    TextView TituloPromo, DescripcionPromo, Points, pointsAction;
    ImageView ImagenPromo, open_history_points;
    String mpoints, userAcumulatedPoints;
    ArrayList<BeaconCache> myBeaconCacheList;
    ImageButton wishes;
    Intent intent;

    NonStaticUtils nonStaticUtils;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__promo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /* Obtiene de las preferencias compartidas, la cantidad de los puntos*/
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        mpoints = preferences.getInt("points", 0) + "";
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel),mpoints);
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_promodetail, null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);


        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        pointsAction.setText(userAcumulatedPoints.toString());

        TituloPromo = (TextView) findViewById(R.id.Titulo_Promo);
        Points = (TextView) findViewById(R.id.Puntos_promo_Detail);
        DescripcionPromo = (TextView) findViewById(R.id.DescriptionPromoDetai);
        ImagenPromo = (ImageView) findViewById(R.id.Imagen_Promo_Detail);
        intent = getIntent();
        ImagenPromo= (ImageView) findViewById(R.id.Imagen_Promo_Detail);
        open_history_points=(ImageView) actionBarLayout.findViewById(R.id.open_history_points);
        Promociones promo = (Promociones)intent.getSerializableExtra("Detail");
        ServiceController imageRequest =  new ServiceController();
        Points.setText(promo.getPuntos() + " pts");
        TituloPromo.setText(promo.getTitulo());
        DescripcionPromo.setText(promo.getDescripcion());
        imageRequest.imageRequest(promo.getUrlImagen(), ImagenPromo, 0, 0);
        final String description= DescripcionPromo.getText().toString()+" "+promo.getUrlImagen();
        final String image=promo.getUrlImagen();

        open_history_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory();
            }
        });
        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory();
            }
        });

        ImagenPromo.setOnClickListener(new View.OnClickListener() {
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
