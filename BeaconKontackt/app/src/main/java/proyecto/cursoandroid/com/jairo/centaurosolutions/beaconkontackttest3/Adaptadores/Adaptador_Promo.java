package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import controllers.ServiceController;
import model.promo.Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Jairo on 04/11/2015.
 */
public class Adaptador_Promo extends ArrayAdapter<Promociones> {

    public Activity contexto;
    //la lista de todos los elementos
    public ArrayList<Promociones> listaPromociones;
    // la lista de los elementos filtrados
    public ArrayList<Promociones> mStringFilterList;



    // private final Integer[] imgid;
/// constructor que recive el contexto y la lista de los elementos
    public Adaptador_Promo(Activity contexto, ArrayList<Promociones> lista) {
        super(contexto, R.layout.activity_elemento_lista_promo, lista);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.listaPromociones = lista;
        this.mStringFilterList = lista;

    }

    // setea el array
    public void setArray(ArrayList<Promociones> listaPromociones) {
        this.listaPromociones = listaPromociones;

    }

    /// adapta los elementos al layout de los element view
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_elemento_lista_promo, null, true);


        TextView tituloPromo = (TextView) rowView.findViewById(R.id.tituloPromo);
        TextView puntosPromo = (TextView) rowView.findViewById(R.id.puntosPromo);
        ImageView imagenPromo = (ImageView) rowView.findViewById(R.id.imagenPromo);
        ServiceController imageRequest =  new ServiceController();

        imageRequest.imageRequest(listaPromociones.get(position).getUrlImagen(), imagenPromo, 0,0);



        tituloPromo.setText(listaPromociones.get(position).getTitulo() + "");
        puntosPromo.setText(listaPromociones.get(position).getPuntos() + " pts");

        return rowView;

    }



    //obtiene la cantidad de elementos
    @Override
    public int getCount() {
        return listaPromociones.size();
    }

    // Obtiene los items especificos del indice
    @Override
    public Promociones getItem(int position) {
        return listaPromociones.get(position);
    }

    //obtiene el id de un elemento en especifico
    @Override
    public long getItemId(int position) {
        return listaPromociones.indexOf(getItem(position));
    }





}