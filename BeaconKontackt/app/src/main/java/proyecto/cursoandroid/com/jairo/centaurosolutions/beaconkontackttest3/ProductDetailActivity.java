package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
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

public class ProductDetailActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    Intent intent;
    String mpoints, userAcumulatedPoints;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    String idUser;
    CustomAdapterProductDepartment adapter;
    TextView pointsAction,name,price,details;
    ImageView openHistoryPoints;
    Button addImage;
    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    ProductStore product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /* Obtiene de las preferencias compartidas, la cantidad de los puntos*/
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        mpoints = preferences.getInt("points", 0) + "";
        ServiceController imageRequest =  new ServiceController();
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        idUser = preferences.getString("userId", "");
        intent = getIntent();
        product = (ProductStore)intent.getSerializableExtra("product");
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_promodetail, null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        openHistoryPoints=(ImageView) actionBarLayout.findViewById(R.id.openHistoryPoints);
        addImage=(Button) actionBarLayout.findViewById(R.id.buttonAdd);
        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        name=(TextView)findViewById(R.id.nameProductDetail);
        price=(TextView)findViewById(R.id.priceProductDetail);
        details=(TextView)findViewById(R.id.detailsProductDetail);
        pointsAction.setText(userAcumulatedPoints.toString());
        name.setText(product.getProductName());
        price.setText(Float.toString(product.getPrice()));
        details.setText(product.getDetails());

        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mpoints.toString().equals("0")) {
                    Toast.makeText(getApplication(), "Aun no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                } else {
                    openHistory();
                }
            }
        });
        openHistoryPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mpoints.toString().equals("0")) {
                    Toast.makeText(getApplication(), "Aun no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                } else {
                    openHistory();
                }
            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               service();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    public void openHistory(){
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser",idUser);
        startActivity(intent);
    }
    public void service(){
        serviceController = new ServiceController();
        responseError = this;
        response = this;
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId",idUser);
        mapParams.put("productId", product.getProductId());
        mapParams.put("productName",product.getProductName());
        mapParams.put("price",product.getPrice());
        mapParams.put("imageUrlList", "http://www.evga.com/products/images/gallery/02G-P4-2958-KR_MD_1.jpg");
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_User)+"user/wishlist/add";
        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        try {

            if (response.getString("message").toString().equals("User updated"))
            {
                Toast.makeText(this, "Añadido correctamente", Toast.LENGTH_SHORT).show();
            }
            if (response.getString("message").toString().equals("Product already added to wishlist"))
            {
                Toast.makeText(this, "El producto ya existe en la lista de deseos", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(this, "Hubo un problema al añadirlo", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}

