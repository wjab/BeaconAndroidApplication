package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.ProductStore;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.ProductsDepartmentActivity;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Centauro on 20/06/2016.
 */
public class CustomAdapterProductDepartment extends ArrayAdapter<ProductStore> {

    public Activity contexto;
    //la lista de todos los elementos
    public ArrayList<ProductStore> productList;
    // la lista de los elementos filtrados
    public ArrayList<ProductStore> mStringFilterList;



    // private final Integer[] imgid;
/// constructor que recive el contexto y la lista de los elementos
    public CustomAdapterProductDepartment(Activity contexto, ArrayList<ProductStore> lista) {
        super(contexto, R.layout.element_product, lista);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.productList = lista;
        this.mStringFilterList = lista;

    }

    // setea el array
    public void setArray(ArrayList<ProductStore> storeList) {
        this.productList = storeList;

    }

    /// adapta los elementos al layout de los element view
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.element_product, null, true);
        ServiceController imageRequest =  new ServiceController();
        final ImageView imageHeard=(ImageView)rowView.findViewById(R.id.addProduct);
        //Bucar en la lista de productos si se encuentra alguno añadido cambiar el corazon
        imageHeard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String productId=productList.get(position).getProductId();
            String productName=productList.get(position).getProductName();
            float price=productList.get(position).getPrice();
                ProductsDepartmentActivity productDepartmentActivity= new ProductsDepartmentActivity();
                productDepartmentActivity.service(productId, productName, price);
            }
        });




        return rowView;

    }



    //obtiene la cantidad de elementos
    @Override
    public int getCount() {
        return productList.size();
    }

    // Obtiene los items especificos del indice
    @Override
    public ProductStore getItem(int position) {
        return productList.get(position);
    }

    //obtiene el id de un elemento en especifico
    @Override
    public long getItemId(int position) {
        return productList.indexOf(getItem(position));
    }





}