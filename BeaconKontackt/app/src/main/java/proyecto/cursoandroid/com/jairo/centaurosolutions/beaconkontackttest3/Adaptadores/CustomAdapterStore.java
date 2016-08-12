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
        super(contexto, R.layout.store_element_list, lista);
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

        TextView walkin = (TextView) rowView.findViewById(R.id.walkin);
        TextView scan = (TextView) rowView.findViewById(R.id.scan);
        TextView purchase = (TextView) rowView.findViewById(R.id.purchase);
        ImageView image = (ImageView) rowView.findViewById(R.id.store_image);
        ImageView walkinImage = (ImageView) rowView.findViewById(R.id.walkinImage);
        ImageView scanImage = (ImageView) rowView.findViewById(R.id.scanImage);
        ImageView purchaseImage = (ImageView) rowView.findViewById(R.id.purchaseImage);

        String walkinText = storeList.get(position).getTotalGiftPoints().getWalkin();
        String scanText = storeList.get(position).getTotalGiftPoints().getScan();
        String purchaseText = "C / "+storeList.get(position).getTotalGiftPoints().getPurchase();
        String url = storeList.get(position).getUrlImagen();

        walkin.setText(walkinText);
        scan.setText(scanText);
        purchase.setText(purchaseText);

        if(walkinText.equals("0")){
            walkinImage.setBackground(contexto.getResources().getDrawable(R.drawable.walk_in_gray));
            walkin.setTextColor(contexto.getResources().getColor(R.color.mediumGrey));
        }
        else {
            walkinImage.setBackground(contexto.getResources().getDrawable(R.drawable.walk_in_blue));
            walkin.setTextColor(contexto.getResources().getColor(R.color.darkBlue));
        }

        if(scanText.equals("0")){
           scanImage.setBackground(contexto.getResources().getDrawable(R.drawable.scan_gray));
            scan.setTextColor(contexto.getResources().getColor(R.color.mediumGrey));
        }
        else{
            scanImage.setBackground(contexto.getResources().getDrawable(R.drawable.scan_blue));
            scan.setTextColor(contexto.getResources().getColor(R.color.darkBlue));
        }

        if(purchaseText.equals("0")){
           purchaseImage.setBackground(contexto.getResources().getDrawable(R.drawable.purchase_gray));
            purchase.setTextColor(contexto.getResources().getColor(R.color.mediumGrey));
        }
        else{
            purchaseImage.setBackground(contexto.getResources().getDrawable(R.drawable.purchase_blue));
            purchase.setTextColor(contexto.getResources().getColor(R.color.darkBlue));
        }
        Picasso.with(contexto).load(url).error(R.drawable.department).into(image);

        ServiceController imageRequest =  new ServiceController();

       imageRequest.imageRequest(storeList.get(position).getUrlImagen(), image, 0,0);

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