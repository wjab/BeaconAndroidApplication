package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.ProductStore;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.ProductCategoryActivity;
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
    public int activity;
    private ProductsDepartmentActivity productDepartmentActivity = new ProductsDepartmentActivity();
    private ProductCategoryActivity productCategoryActivity = new ProductCategoryActivity();


    // private final Integer[] imgid;
/// constructor que recive el contexto y la lista de los elementos
    public CustomAdapterProductDepartment(Activity contexto, ArrayList<ProductStore> lista, int activity) {
        super(contexto, R.layout.element_product, lista);
        // TODO Auto-generated constructor stub

        this.contexto = contexto;
        this.productList = lista;
        this.mStringFilterList = lista;
        this.activity = activity;
    }

    // setea el array
    public void setArray(ArrayList<ProductStore> storeList)
    {
        this.productList = storeList;
    }

    /// adapta los elementos al layout de los element view
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public View getView(final int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = contexto.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.element_product, null, true);
        final ImageView imageHeard = (ImageView)rowView.findViewById(R.id.addProduct);
        final ImageView imageDepartment = (ImageView)rowView.findViewById(R.id.imagenDepartment);
        final ImageView barcodeImage = (ImageView)rowView.findViewById(R.id.imageView4);
        final TextView price = (TextView)rowView.findViewById(R.id.price);
        final TextView points = (TextView)rowView.findViewById(R.id.points);
        final TextView pointsByScan = (TextView)rowView.findViewById(R.id.pointsScan);
        price.setText( String.format(contexto.getString(R.string.colonSymbol), String.valueOf(productList.get(position).getPrice())) );
        points.setText( String.format(contexto.getString(R.string.pointsSymbol), String.format(String.valueOf(productList.get(position).getPointsByPrice()))));

        pointsByScan.setText(String.valueOf(productList.get(position).getPointsByScan()));

        if(activity == 1) {
            if (productList.get(position).getStateWishList() == 1) {
                imageHeard.setBackground(contexto.getResources().getDrawable(R.drawable.ic_added));
            } else {
                imageHeard.setBackground(contexto.getResources().getDrawable(R.drawable.ic_add));
            }
            if (productList.get(position).getAllowScan()==false)
            {
                barcodeImage.setVisibility(View.GONE);
                pointsByScan.setVisibility(View.GONE);
            }
                    imageHeard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String productId = productList.get(position).getProductId();
                    String productName = productList.get(position).getProductName();
                    String url=null;
                    float price = productList.get(position).getPrice();
                    int pointsByPrice=  productList.get(position).getPointsByPrice();
                    ArrayList<String> imagesArray= new ArrayList<String>();
                    imagesArray=productList.get(position).getImageUrlList();
                    int len = imagesArray.size();
                    for (int l=0;l<len;l++){
                        if(l==0) {
                            url =imagesArray.get(l);
                        }
                    }
                    productDepartmentActivity.service(productId, productName, price ,url , pointsByPrice);

                }});

            // Metodo de escaneo
            barcodeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productDepartmentActivity.ActivateScan();
                }
            });
        }
        else {
            // Bucar en la lista de productos si se encuentra alguno añadido cambiar el corazon
            if (productList.get(position).getStateWishList() == 1) {
                imageHeard.setBackground(contexto.getResources().getDrawable(R.drawable.ic_added));
            } else {
                imageHeard.setBackground(contexto.getResources().getDrawable(R.drawable.ic_add));
            }
            imageHeard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String productId = productList.get(position).getProductId();
                    String productName = productList.get(position).getProductName();
                    String url=null;
                    float price = productList.get(position).getPrice();
                    ArrayList<String> imagesArray= new ArrayList<String>();
                    imagesArray=productList.get(position).getImageUrlList();
                    int pointsByPrice = productList.get(position).getPointsByPrice();
                    int len = imagesArray.size();
                    for (int l=0;l<len;l++){
                        if(l==0) {
                            url =imagesArray.get(l);
                        }
                    }

                    productCategoryActivity.service(productId, productName, price, url, pointsByPrice);
                }});

            // Metodo de escaneo
            barcodeImage.setVisibility(View.GONE);
            pointsByScan.setVisibility(View.GONE);
            barcodeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productDepartmentActivity.ActivateScan();
                }
            });
        }
        String url=productList.get(position).getUrlImageShow();
        Picasso.with(contexto).load(url).error(R.drawable.department).into(imageDepartment);
        return rowView;
    }

    //obtiene la cantidad de elementos
    @Override
    public int getCount()
    {
        return productList.size();
    }

    // Obtiene los items especificos del indice
    @Override
    public ProductStore getItem(int position)
    {
        return productList.get(position);
    }

    //obtiene el id de un elemento en especifico
    @Override
    public long getItemId(int position)
    {
        return productList.indexOf(getItem(position));
    }





}