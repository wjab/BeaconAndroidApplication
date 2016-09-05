package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Wish;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.WishListActivity;

/**
 * Created by Centauro on 11/07/2016.
 */
public class CustomAdapterWish extends ArrayAdapter<Wish> {

    public Activity contexto;
    //la lista de todos los elementos
    public ArrayList<Wish> list;
    // la lista de los elementos filtrados
    public ArrayList<Wish> mStringFilterList;

    public CustomAdapterWish(Activity contexto, ArrayList<Wish> list) {
        super(contexto, R.layout.element_wish_list, list);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.list = list;
        this.mStringFilterList = list;

    }

    // setea el array
    public void setArray(ArrayList<Wish> list) {
        this.list = list;

    }

    /// adapta los elementos al layout de los element view
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.element_wish_list, null, true);


        final TextView name = (TextView) rowView.findViewById(R.id.nameProduct);
        final TextView id = (TextView) rowView.findViewById(R.id.idProduct);
        final TextView price = (TextView) rowView.findViewById(R.id.price);
        final ImageView image = (ImageView) rowView.findViewById(R.id.imageViewProduct);
        final TextView imageUrl=(TextView)rowView.findViewById(R.id.urlImage);
        ServiceController imageRequest = new ServiceController();

        imageRequest.imageRequest(list.get(position).getImageUrlList(), image, 0, 0);
        id.setText(list.get(position).getProductId());
        name.setText(list.get(position).getProductName() + "");
        price.setText(list.get(position).getPointsByPrice() + "");
        imageUrl.setText(list.get(position).getImageUrlList());
        final ImageView delete = (ImageView) rowView.findViewById(R.id.deleteWish);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idProduct= String.valueOf(id.getText());
                String nameProduct= String.valueOf(name.getText());
                int priceProduct= Integer.parseInt(String.valueOf(price.getText()));
                String urlProduct= String.valueOf(imageUrl.getText());
                WishListActivity wlist=new WishListActivity();
                wlist.serviceDeleteWish(idProduct,nameProduct,priceProduct,urlProduct);
            }
        });

        return rowView;

    }


    //obtiene la cantidad de elementos
    @Override
    public int getCount() {
        return list.size();
    }

    // Obtiene los items especificos del indice
    @Override
    public Wish getItem(int position) {
        return list.get(position);
    }

    //obtiene el id de un elemento en especifico
    @Override
    public long getItemId(int position) {
        return list.indexOf(getItem(position));
    }


}