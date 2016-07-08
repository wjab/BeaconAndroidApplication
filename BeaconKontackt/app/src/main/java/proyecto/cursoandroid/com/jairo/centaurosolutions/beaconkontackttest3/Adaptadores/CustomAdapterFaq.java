package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Faq;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Centauro on 08/07/2016.
 */
public class CustomAdapterFaq  extends ArrayAdapter<Faq> {


    public Activity contexto;
    //la lista de todos los elementos
    public ArrayList<Faq> list;
    // la lista de los elementos filtrados
    public ArrayList<Faq> mStringFilterList;

    public CustomAdapterFaq(Activity contexto, ArrayList<Faq> list) {
        super(contexto, R.layout.faq_element_list, list);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.list = list;
        this.mStringFilterList = list;

    }
    // setea el array
    public void setArray(ArrayList<Faq>list) {
        this.list = list;

    }

    /// adapta los elementos al layout de los element view
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.faq_element_list, null, true);


        TextView question = (TextView) rowView.findViewById(R.id.question);
        TextView answer = (TextView) rowView.findViewById(R.id.answer);
        question.setText(list.get(position).getQuestion() + "");
        answer.setText(list.get(position).getAnswer() + "");

        return rowView;

    }



    //obtiene la cantidad de elementos
    @Override
    public int getCount() {
        return list.size();
    }

    // Obtiene los items especificos del indice
    @Override
    public Faq getItem(int position) {
        return list.get(position);
    }

    //obtiene el id de un elemento en especifico
    @Override
    public long getItemId(int position) {
        return list.indexOf(getItem(position));
    }




}
