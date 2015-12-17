package adapter.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.elementMenu.ElementMenu;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Jairo on 16/12/2015.
 */
public class MenuAdapter extends ArrayAdapter<ElementMenu> {

        ArrayList<ElementMenu> elementoMenu;
        private Context context;
        public MenuAdapter(Context context, ArrayList<ElementMenu> objects) {
            super(context, 0, objects);
            this.context = context;
            elementoMenu=objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItem = convertView;

                LayoutInflater inflater = (LayoutInflater)parent.getContext().
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.elemento_lista_menu, null);


            ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            TextView textViewName = (TextView) convertView.findViewById(R.id.textViewName);

            ElementMenu folder = elementoMenu.get(position);


            imageViewIcon.setImageResource(folder.getImagen());
            textViewName.setText(folder.getElemento());

            return convertView;
        }
    }

