package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterProductDepartment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.ProductStore;
import utils.NonStaticUtils;

public class ProductsStoreActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener
{
    public CustomAdapterProductDepartment adapter;
    public ListView listviewShopProduct;
    public ArrayList<ProductStore> listStoreProductArray;
    private View rootView;
    String mpoints, userAcumulatedPoints;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    String idUser;
    LinearLayout back;
    Button addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_store);
        nonStaticUtils = new NonStaticUtils();

        preferences = nonStaticUtils.loadLoginInfo(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar_promodetail,
                null);

        mpoints = String.valueOf(preferences.getInt("points", 0));

        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        idUser = preferences.getString("userId", "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        listviewShopProduct = (ListView)findViewById(R.id.listviewStoresProducts);
        back = (LinearLayout) actionBarLayout.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();

            }
        });

        listviewShopProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductStore storeProduct = new ProductStore();
                storeProduct = listStoreProductArray.get(position);
                //Intent intentSuccess = new Intent(getActivity().getBaseContext(), DetailPromo.class);
                //intentSuccess.putExtra("Detail", store);
                //startActivity(intentSuccess);
            }
        });
        addImage = (Button) actionBarLayout.findViewById(R.id.buttonAdd);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WishListActivity.class);
                intent.putExtra("idUser", idUser);
                startActivity(intent);
            }
        });
        TextView pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        pointsAction.setText(userAcumulatedPoints.toString());

        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpoints.equals("0"))
                {
                    Toast.makeText(getApplication(), getString(R.string.dontHavePoints), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    openHistory();
                }
            }
        });
        shopProductService();
        return;
    }

    public void openHistory(){
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser",idUser);
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
        try
        {
            listStoreProductArray = new ArrayList<ProductStore>();
            Gson gson= new Gson();
            JSONArray ranges= response.getJSONArray("merchantProduct");
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

            adapter=new CustomAdapterProductDepartment(this, listStoreProductArray,0);
            listviewShopProduct.setAdapter(adapter);
        }
        catch (JSONException e)
        {
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
