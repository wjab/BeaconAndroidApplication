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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Department;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Centauro on 01/08/2016.
 */
public class CustomAdapterDepartments extends ArrayAdapter<Department> {

    public Activity contexto;
    //la lista de todos los elementos
    public ArrayList<Department> storeList;
    // la lista de los elementos filtrados
    public ArrayList<Department> mStringFilterList;



    // private final Integer[] imgid;
/// constructor que recive el contexto y la lista de los elementos
    public CustomAdapterDepartments(Activity contexto, ArrayList<Department> lista) {
        super(contexto, R.layout.element_department, lista);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.storeList = lista;
        this.mStringFilterList = lista;

    }

    // setea el array
    public void setArray(ArrayList<Department> storeList) {
        this.storeList = storeList;

    }

    /// adapta los elementos al layout de los element view
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.element_department, null, true);

        TextView walkin = (TextView) rowView.findViewById(R.id.walkin);

        //walkin.setText(storeList.get(position).getTotalGiftPoints().getWalkin() + "");

        ImageView image = (ImageView) rowView.findViewById(R.id.store_image);
        ServiceController imageRequest =  new ServiceController();

        //imageRequest.imageRequest(storeList.get(position).getUrlImagen(), image, 0,0);



        return rowView;

    }



    //obtiene la cantidad de elementos
    @Override
    public int getCount() {
        return storeList.size();
    }

    // Obtiene los items especificos del indice
    @Override
    public Department getItem(int position) {
        return storeList.get(position);
    }

    //obtiene el id de un elemento en especifico
    @Override
    public long getItemId(int position) {
        return storeList.indexOf(getItem(position));
    }

}
