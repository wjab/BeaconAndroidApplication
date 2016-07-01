package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Store;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Centauro on 16/06/2016.
 */
public class CustomAdapterStore extends ArrayAdapter<Store> {

    public Activity contexto;
    //la lista de todos los elementos
    public ArrayList<Store> storeList;
    // la lista de los elementos filtrados
    public ArrayList<Store> mStringFilterList;



    // private final Integer[] imgid;
/// constructor que recive el contexto y la lista de los elementos
    public CustomAdapterStore(Activity contexto, ArrayList<Store> lista) {
        super(contexto, R.layout.activity_elemento_lista_promo, lista);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.storeList = lista;
        this.mStringFilterList = lista;

    }

    // setea el array
    public void setArray(ArrayList<Store> storeList) {
        this.storeList = storeList;

    }

    /// adapta los elementos al layout de los element view
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.store_element_list, null, true);


        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView points = (TextView) rowView.findViewById(R.id.points);
        TextView address=(TextView)rowView.findViewById(R.id.address);
        ImageView image = (ImageView) rowView.findViewById(R.id.store_image);
        ServiceController imageRequest =  new ServiceController();

       imageRequest.imageRequest(storeList.get(position).getUrlImagen(), image, 0,0);


        address.setText(storeList.get(position).getAddress());
        name.setText(storeList.get(position).getMerchantName() + "");
        points.setText(storeList.get(position).getPointToGive() + "");

        return rowView;

    }



    //obtiene la cantidad de elementos
    @Override
    public int getCount() {
        return storeList.size();
    }

    // Obtiene los items especificos del indice
    @Override
    public Store getItem(int position) {
        return storeList.get(position);
    }

    //obtiene el id de un elemento en especifico
    @Override
    public long getItemId(int position) {
        return storeList.indexOf(getItem(position));
    }





}