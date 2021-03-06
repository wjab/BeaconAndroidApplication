package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import controllers.ServiceController;
import model.cache.BeaconCache;

public class PromoDetailActivity extends AppCompatActivity {

    TextView TituloPromo ;
    TextView DescripcionPromo;
    TextView Points;
    ImageView ImagenPromo;
    String mpoints;
    private ActionBarDrawerToggle mDrawerToggle;
    ArrayList<BeaconCache> myBeaconCacheList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_promo_detail);
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar_layout,
                null);
        mpoints = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getInt("points", 0)+"";
       // getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        //getSupportActionBar().setCustomView(actionBarLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*ImageButton imageButton = (ImageButton) actionBarLayout.findViewById(R.id.back_action);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BackgroundScanActivity.class);
                startActivity(intent);


            }
        });
        */
        TextView pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        pointsAction.setText(mpoints + " pts");
        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory();
            }
        });
        TituloPromo = (TextView) findViewById(R.id.Titulo_Promo);
        Points = (TextView) findViewById(R.id.Puntos_promo_Detail);
        DescripcionPromo = (TextView) findViewById(R.id.DescriptionPromoDetai);
        ImagenPromo = (ImageView) findViewById(R.id.Imagen_Promo_Detail);
        Intent intent1= getIntent();

        myBeaconCacheList = (ArrayList<BeaconCache>)intent1.getSerializableExtra("promoDetail");
       // BeaconCache promo=(BeaconCache)intent1.getSerializableExtra("promoDetail");
       ServiceController imageRequest =  new ServiceController();
       /* Points.setText(promo.giftPoints+" pts");
        TituloPromo.setText(promo.title);
        DescripcionPromo.setText(promo.descrition);
        imageRequest.imageRequest(promo.picturePath, ImagenPromo, 0,0)*/
    }

    @Override
    public void onBackPressed() {
        finish();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    public void openHistory(){
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        startActivity(intent);
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
