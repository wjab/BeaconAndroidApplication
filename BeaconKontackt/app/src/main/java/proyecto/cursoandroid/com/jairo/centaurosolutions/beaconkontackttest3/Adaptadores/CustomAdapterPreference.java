package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Preference;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.PreferencesDialogFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Centauro on 13/07/2016.
 */
public class CustomAdapterPreference extends ArrayAdapter<Preference> {
    public Activity contexto;
    //la lista de todos los elementos
    public ArrayList<Preference> list;
    // la lista de los elementos filtrados
    public ArrayList<Preference> mStringFilterList;


    public CustomAdapterPreference(Activity contexto, ArrayList<Preference> list) {
        super(contexto, R.layout.element_preference_list, list);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.list = list;
        this.mStringFilterList = list;

    }
    // setea el array
    public void setArray(ArrayList<Preference> list) {
        this.list = list;

    }

    /// adapta los elementos al layout de los element view
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.element_preference_list, null, true);
         final Switch switchPreference = (Switch) rowView.findViewById(R.id.switchPreference);
        switchPreference.setText(list.get(position).getPreference() + "");

        if (list.get(position).getState().equals("activado")) {
            switchPreference.setChecked(true);
        } else {
            switchPreference.setChecked(false);
        }

        switchPreference.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String preference = switchPreference.getText().toString();
                if(isChecked){
                String valor="activado";

                sendDataPreferences(valor,preference);
            }else{
                String valor="desactivado";
                sendDataPreferences(valor,preference);
            }

        }
    });


        return rowView;

    }

    public void sendDataPreferences(String valor,String preference){
        PreferencesDialogFragment pf= new PreferencesDialogFragment();
        pf.serviceUpdate(valor,preference);
    }
    //obtiene la cantidad de elementos
    @Override
    public int getCount() {
        return list.size();
    }

    // Obtiene los items especificos del indice
    @Override
    public Preference getItem(int position) {
        return list.get(position);
    }

    //obtiene el id de un elemento en especifico
    @Override
    public long getItemId(int position) {
        return list.indexOf(getItem(position));
    }


}
