package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.Adaptador_Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;

public class TabPromocionesActivity extends Activity {

    Adaptador_Promo adapter;
    ListView listviewPromo;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_promociones);
        listviewPromo = (ListView) findViewById(R.id.listPromo);
        listviewPromo.setAdapter(adapter);
        ArrayList<Promociones> promociones= new ArrayList<Promociones>();
        Promociones promo= new Promociones();

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
                promo.setTitulo("Tienda Kawai");
        promo.setDescripcion("10% de descuento en Dildos con forma de salchichas con corazoncitos :3");
        promo.setPuntos(100);
        promo.setId(57545);
        promo.setUrlImagen("https://kawaiistoretienda.files.wordpress.com/2013/06/cropped-bannerofeliafeliz1.jpg");
        promociones.add(promo);

        promo= new Promociones();
        promo.setTitulo("Siman");
        promo.setDescripcion("15% de descuento en ropa para hombre");
        promo.setPuntos(150);
        promo.setId(53145);
        promo.setUrlImagen("http://i996.photobucket.com/albums/af81/para_elforo/tradiciones/si-1.jpg");
        promociones.add(promo);


        promo= new Promociones();
        promo.setTitulo("Universal");
        promo.setDescripcion("15% de descuento en ropa para Mujer, y muchas cosas kawai en nuestras tiendas, no se que mas poner estoy poniendo caracteres a lo loco jojojo , osheeeeeeeee k riko, como que idiota ");
        promo.setPuntos(999);
        promo.setId(54897);
        promo.setUrlImagen("https://www.larepublica.net/app/cms/www/images/201211170030500.m3.jpg");
        promociones.add(promo);
        adapter=new Adaptador_Promo(this, promociones);
        listviewPromo.setAdapter(adapter);
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
}
