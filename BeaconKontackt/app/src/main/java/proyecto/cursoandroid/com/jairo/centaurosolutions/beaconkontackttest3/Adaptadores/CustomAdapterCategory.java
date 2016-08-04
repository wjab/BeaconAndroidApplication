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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Category;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Centauro on 04/08/2016.
 */
public class CustomAdapterCategory extends ArrayAdapter<Category> {

    public Activity contexto;
    //la lista de todos los elementos
    public ArrayList<Category> list;
    // la lista de los elementos filtrados
    public ArrayList<Category> mStringFilterList;



    // private final Integer[] imgid;
/// constructor que recive el contexto y la lista de los elementos
    public CustomAdapterCategory(Activity contexto, ArrayList<Category> list) {
        super(contexto, R.layout.element_category, list);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.list = list;
        this.mStringFilterList = list;

    }

    // setea el array
    public void setArray(ArrayList<Category> list) {
        this.list = list;

    }

    /// adapta los elementos al layout de los element view
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.element_category, null, true);

        TextView walkin = (TextView) rowView.findViewById(R.id.descriptionCategory);
        walkin.setText(list.get(position).getType());

        ImageView image = (ImageView) rowView.findViewById(R.id.imageCategory);
        ServiceController imageRequest =  new ServiceController();

        imageRequest.imageRequest(list.get(position).getUrlImage(), image, 0,0);

        return rowView;

    }



    //obtiene la cantidad de elementos
    @Override
    public int getCount() {
        return list.size();
    }

    // Obtiene los items especificos del indice
    @Override
    public Category getItem(int position) {
        return list.get(position);
    }

    //obtiene el id de un elemento en especifico
    @Override
    public long getItemId(int position) {
        return list.indexOf(getItem(position));
    }

}
