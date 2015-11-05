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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Jairo on 04/11/2015.
 */
public class Adaptador_Promo extends ArrayAdapter<Promociones> implements Filterable {

    public Activity contexto;
    //la lista de todos los elementos
    public ArrayList<Promociones> listaPromociones;
    // la lista de los elementos filtrados
    public ArrayList<Promociones> mStringFilterList;
    public ValueFilter valueFilter;


    // private final Integer[] imgid;
/// constructor que recive el contexto y la lista de los elementos
    public Adaptador_Promo(Activity contexto, ArrayList<Promociones> listaPartidos) {
        super(contexto, R.layout.activity_elemento_lista_promo, listaPartidos);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.listaPromociones = listaPartidos;
        this.mStringFilterList = listaPartidos;

    }

    // setea el array
    public void setArray(ArrayList<Promociones> listaPromociones) {
        this.listaPromociones = listaPromociones;

    }

    /// adapta los elementos al layout de los element view
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_elemento_lista_promo, null, true);

        TextView DescripcionPromo = (TextView) rowView.findViewById(R.id.Descripcion_Promo);
        TextView TituloPromo = (TextView) rowView.findViewById(R.id.Titulo_Promo);
        TextView PuntosPromo = (TextView) rowView.findViewById(R.id.Puntos_promo);
        ImageView ImagenPromo = (ImageView) rowView.findViewById(R.id.Imagen_Promo);
        ServiceController imageRequest =  new ServiceController();

        imageRequest.imageRequest(listaPromociones.get(position).getUrlImagen(), ImagenPromo, 0,0);


        DescripcionPromo.setText(listaPromociones.get(position).getDescripcion() + "");
        TituloPromo.setText(listaPromociones.get(position).getTitulo() + "");
        PuntosPromo.setText(listaPromociones.get(position).getPuntos() + " Puntos");

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


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence filtros) {
            FilterResults results = new FilterResults();
            String stringfilter = filtros.toString();

            if (stringfilter != null && stringfilter != "") {
                ArrayList<Promociones> filterList = new ArrayList<Promociones>();
                for (int i = 0; i < mStringFilterList.size(); i++) {


                        if (mStringFilterList.get(i).getTitulo().toUpperCase().contains(stringfilter.toUpperCase())) {

                            Promociones Promociones = new Promociones();
                            Promociones.setDescripcion(mStringFilterList.get(i).getDescripcion());
                            Promociones.setId(mStringFilterList.get(i).getId());
                            Promociones.setPuntos(mStringFilterList.get(i).getPuntos());
                            Promociones.setTitulo(mStringFilterList.get(i).getTitulo());
                            Promociones.setUrlImagen(mStringFilterList.get(i).getUrlImagen());
                            filterList.add(Promociones);
                        }


                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            listaPromociones = (ArrayList<Promociones>) results.values;
            notifyDataSetChanged();
        }
    }
}