package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;

public class Detail_Promo extends AppCompatActivity {

    TextView TituloPromo ;
    TextView DescripcionPromo;
    ImageView ImagenPromo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail__promo);
        TituloPromo = (TextView) findViewById(R.id.Titulo_Promo);
        DescripcionPromo = (TextView) findViewById(R.id.DescriptionPromoDetai);
        ImagenPromo = (ImageView) findViewById(R.id.Imagen_Promo_Detail);
        Intent intent= getIntent();
        Promociones promo=(Promociones)intent.getSerializableExtra("Detail");
        ServiceController imageRequest =  new ServiceController();
        TituloPromo.setText(promo.getTitulo());
        DescripcionPromo.setText(promo.getDescripcion());
        imageRequest.imageRequest(promo.getUrlImagen(), ImagenPromo, 0,0);
    }

    @Override
    public void onBackPressed() {
            finish();

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
