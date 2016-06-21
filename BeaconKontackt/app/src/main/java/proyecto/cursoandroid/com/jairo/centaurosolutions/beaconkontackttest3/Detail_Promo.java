package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
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

public class Detail_Promo extends AppCompatActivity {

    TextView TituloPromo ;
    TextView DescripcionPromo;
    TextView Points, points;
    ImageView ImagenPromo;
    String mpoints;
    ArrayList<BeaconCache> myBeaconCacheList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail__promo);
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar_layout,
                null);

        mpoints = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getInt("points", 0)+"";

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        // ImageButton imageButton = (ImageButton) actionBarLayout.findViewById(R.id.back_action);
       /* imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myBeaconCacheList!=null) {
                    Intent redirectIntent = new Intent(getApplicationContext(), PullNotificationsActivity.class);
                    redirectIntent.putExtra("promoDetail", myBeaconCacheList);
                    startActivity(redirectIntent);
                }else{
                    finish();
                }
                //
            }
            });
            */

        TextView pointsAction= (TextView) findViewById(R.id.userPointsAction);
        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory();
            }
        });
        pointsAction.setText(mpoints + " pts");
        TituloPromo = (TextView) findViewById(R.id.Titulo_Promo);
        Points = (TextView) findViewById(R.id.Puntos_promo_Detail);
        DescripcionPromo = (TextView) findViewById(R.id.DescriptionPromoDetai);
        ImagenPromo = (ImageView) findViewById(R.id.Imagen_Promo_Detail);
        Intent intent= getIntent();
        Promociones promo=(Promociones)intent.getSerializableExtra("Detail");
        ServiceController imageRequest =  new ServiceController();
        Points.setText(promo.getPuntos()+" pts");
        TituloPromo.setText(promo.getTitulo());
        DescripcionPromo.setText(promo.getDescripcion());
        imageRequest.imageRequest(promo.getUrlImagen(), ImagenPromo, 0, 0);

        myBeaconCacheList = (ArrayList<BeaconCache>)intent.getSerializableExtra("promoDetail");
        if(myBeaconCacheList!=null){
        int indextoDelete=-1;
        for (int i=0; i < myBeaconCacheList.size(); i++ ) {
            if(myBeaconCacheList.get(i).promoId.equals(promo.getId()))
            {
                indextoDelete=i;
            }
        }
        if( indextoDelete!=-1)
        {
            myBeaconCacheList.remove(indextoDelete);
        }}



    }

    public void openHistory(){
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
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
