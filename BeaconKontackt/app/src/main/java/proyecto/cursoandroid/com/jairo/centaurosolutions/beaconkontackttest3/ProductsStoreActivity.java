package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterProductStore;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterStore;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.ProductStore;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Store;

public class ProductsStoreActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

public CustomAdapterProductStore adapter;
public ListView listviewShopProduct;
public ArrayList<ProductStore> listStoreProductArray;
private View rootView;
    String mpoints;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_products_store);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
            R.layout.action_bar_promodetail,
            null);

    mpoints = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getInt("points", 0)+"";

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(false);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayShowCustomEnabled(true);
    getSupportActionBar().setCustomView(actionBarLayout);
    listviewShopProduct= (ListView)findViewById(R.id.listviewStoresProducts);

    listviewShopProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ProductStore storeProduct = new ProductStore();
            storeProduct = listStoreProductArray.get(position);
            //Intent intentSuccess = new Intent(getActivity().getBaseContext(), Detail_Promo.class);
            //intentSuccess.putExtra("Detail", store);
            //startActivity(intentSuccess);
        }
    });
    TextView pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
    pointsAction.setText(mpoints + " pts");
    pointsAction.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openHistory();
        }
    });
    shopProductService();
    return;
    }
    public void openHistory(){
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        startActivity(intent);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            listStoreProductArray = new ArrayList<ProductStore>();
            Gson gson= new Gson();
            JSONArray ranges= response.getJSONArray("MerchantProduct");
            String range = "";
            String message = "";
            String messageType = "";
            String storeProduct = "";

           // for(int i=0; i < ranges.length(); i++ ){
             //   JSONObject currRange = ranges.getJSONObject(i);

                ProductStore storeProductElement = new  ProductStore();
                //storeProductElement.setProductName(currRange.getString("productName"));
               // storeProductElement.setPrice(currRange.getInt("price"));
            storeProductElement.setProductName("Producto");
            storeProductElement.setPrice(1000);
            //storeProductElement.setUrlImagen(currRange.getString("image"));

                listStoreProductArray.add(storeProductElement);
            //}

            adapter=new CustomAdapterProductStore(this, listStoreProductArray);
            listviewShopProduct.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;

    public void shopProductService(){
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> nullMap = new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_MerchantProduct)+"merchantproduct/";
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }
}
