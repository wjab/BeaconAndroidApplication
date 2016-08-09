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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Notification;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Jairo on 04/11/2015.
 */
public class Adaptador_Notifications extends ArrayAdapter<Notification> {

    public Activity contexto;
    //la lista de todos los elementos
    public ArrayList<Notification> listaNotificaiones;
    // la lista de los elementos filtrados
    public ArrayList<Notification> mStringFilterList;



    // private final Integer[] imgid;
/// constructor que recive el contexto y la lista de los elementos
    public Adaptador_Notifications(Activity contexto, ArrayList<Notification> lista) {
        super(contexto, R.layout.elemento_notification, lista);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.listaNotificaiones = lista;
        this.mStringFilterList = lista;

    }

    // setea el array
    public void setArray(ArrayList<Notification> listaNotificaiones) {
        this.listaNotificaiones = listaNotificaiones;

    }

    /// adapta los elementos al layout de los element view
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.elemento_notification, null, true);


        TextView mensaje = (TextView) rowView.findViewById(R.id.notificarion_message);
        mensaje.setText(listaNotificaiones.get(position).getMessage() + "");
        return rowView;

    }



    //obtiene la cantidad de elementos
    @Override
    public int getCount() {
        return listaNotificaiones.size();
    }

    // Obtiene los items especificos del indice
    @Override
    public Notification getItem(int position) {
        return listaNotificaiones.get(position);
    }

    //obtiene el id de un elemento en especifico
    @Override
    public long getItemId(int position) {
        return listaNotificaiones.indexOf(getItem(position));
    }





}