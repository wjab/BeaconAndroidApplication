package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.History;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Store;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Centauro on 16/06/2016.
 */
public class CustomAdapterHistoryPoints extends ArrayAdapter<History> {


    public Activity contexto;
    //la lista de todos los elementos
    public ArrayList<History> listaPuntosObtenidos;
    // la lista de los elementos filtrados
    public ArrayList<History> mStringFilterList;

    public CustomAdapterHistoryPoints(Activity contexto, ArrayList<History> lista) {
        super(contexto, R.layout.history_element_list, lista);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.listaPuntosObtenidos = lista;
        this.mStringFilterList = lista;

    }
    // setea el array
    public void setArray(ArrayList<History>listaPuntosObtenidos) {
        this.listaPuntosObtenidos = listaPuntosObtenidos;

    }

    /// adapta los elementos al layout de los element view
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.history_element_list, null, true);


        TextView name = (TextView) rowView.findViewById(R.id.nameHistory);
        TextView points = (TextView) rowView.findViewById(R.id.pointsHistory);
       TextView address=(TextView)rowView.findViewById(R.id.addressHistory);
       // ImageView image = (ImageView) rowView.findViewById(R.id.store_image);
        ServiceController imageRequest =  new ServiceController();

        //imageRequest.imageRequest(listaPuntosObtenidos.get(position).getUrlImagen(), image, 0,0);


        address.setText(listaPuntosObtenidos.get(position).getAdressMerchant());
        name.setText(listaPuntosObtenidos.get(position).getPromoTitle() + "");
        points.setText(listaPuntosObtenidos.get(position).getPoints()+ "");

        return rowView;

    }



    //obtiene la cantidad de elementos
    @Override
    public int getCount() {
        return listaPuntosObtenidos.size();
    }

    // Obtiene los items especificos del indice
    @Override
    public History getItem(int position) {
        return listaPuntosObtenidos.get(position);
    }

    //obtiene el id de un elemento en especifico
    @Override
    public long getItemId(int position) {
        return listaPuntosObtenidos.indexOf(getItem(position));
    }




}
