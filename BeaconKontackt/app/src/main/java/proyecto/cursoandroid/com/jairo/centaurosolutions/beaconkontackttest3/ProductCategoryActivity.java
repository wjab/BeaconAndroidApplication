package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterProductDepartment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.ProductStore;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Wish;
import utils.NonStaticUtils;

public class ProductCategoryActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener
{
    Intent intent;
    String mpoints, userAcumulatedPoints,nameCategory,urlImage;
    private static String webServiceUser;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    private static String idUser,type;
    private static GridView grid;
    public static ArrayList<ProductStore> listArray;
    CustomAdapterProductDepartment adapter;
    TextView pointsAction, name;
    ImageView openHistoryPoints,imageCategory;
    public static ArrayList<Wish> listArrayWish;
    private static Activity context;
    private static Activity thisActivity;
    private int activity;
    private Button addImage;
    LinearLayout back;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        /* Obtiene de las preferencias compartidas, la cantidad de los puntos*/
        nonStaticUtils = new NonStaticUtils();

        preferences = nonStaticUtils.loadLoginInfo(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mpoints = String.valueOf(preferences.getInt("points", 0));
        ServiceController imageRequest =  new ServiceController();
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        idUser = preferences.getString("userId", "");
        intent = getIntent();
        context = this;
        thisActivity = this;
        activity = 2;
        nameCategory = intent.getStringExtra("name");
        name = (TextView)findViewById(R.id.nameCategory);
        name.setText(nameCategory);
        urlImage = intent.getStringExtra("urlImage");
        type = intent.getStringExtra("type");

        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_promodetail, null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);

        openHistoryPoints = (ImageView) actionBarLayout.findViewById(R.id.openHistoryPoints);
        back = (LinearLayout) actionBarLayout.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();
            }
        });

        imageCategory = (ImageView)findViewById(R.id.imageCategory);
        Picasso.with(context).load(urlImage).error(R.drawable.department).into(imageCategory);
        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        pointsAction.setText(userAcumulatedPoints.toString());
        addImage = (Button) actionBarLayout.findViewById(R.id.buttonAdd);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WishListActivity.class);
                intent.putExtra("idUser", idUser);
                startActivity(intent);
            }
        });
        grid = (GridView)findViewById(R.id.productsCategory);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ProductStore product = new ProductStore();
                product = listArray.get(position);
                Intent intentSuccess = new Intent(getBaseContext(),ProductDetailActivity.class);
                intentSuccess.putExtra("product", product);
                intentSuccess.putExtra("activity", activity);
                startActivity(intentSuccess);
            }
        });

        pointsAction.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (mpoints.equals("0"))
                {
                    Toast.makeText(getApplication(), "Aun no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                } else
                {
                    openHistory();
                }
            }
        });

        openHistoryPoints.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(mpoints.equals("0"))
                {
                    Toast.makeText(getApplication(), "Aun no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    openHistory();
                }
            }
        });

        webServiceUser = getString(R.string.WebService_User);
        obtainProducts();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    public void openHistory()
    {
        Intent intent = new Intent(context, HistotyPointsActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }
    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;

    public void chargeDepartments()
    {
        String idProductWishList, idProduct;

        for(int i=0; i < listArrayWish.size(); i++ )
        {
            idProductWishList=listArrayWish.get(i).getProductId();

            for(int j=0; j < listArray.size(); j++ )
            {
                idProduct = listArray.get(j).getProductId();
                if(idProductWishList.equals(idProduct))
                {
                    listArray.get(j).setStateWishList(1);
                }
            }
        }
        adapter = new CustomAdapterProductDepartment(thisActivity, listArray, activity);
        grid.setAdapter(adapter);
    }
    public void service(String productId, String productName, float price, String urlImage)
    {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", idUser);
        mapParams.put("productId", productId);
        mapParams.put("productName", productName);
        mapParams.put("price", price);
        mapParams.put("imageUrlList", urlImage);

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        String url = webServiceUser+"user/wishlist/add";
        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }

    public void productWishList()
    {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> nullMap = new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = webServiceUser+"user/id/"+idUser;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }
    public void obtainProducts(){
        serviceController = new ServiceController();
        responseError = this;
        response = this;
        Map<String, String> nullMap = new HashMap<String, String>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_MerchantProfile) + "merchantprofile/allproducts/" + type;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        try
        {
            if(response.getString("message").toString().equals(""))
            {
                response=response.getJSONObject("user");
                JSONArray ranges1= response.getJSONArray("productWishList");
                listArrayWish= new ArrayList<Wish>();

                for(int i = 0; i < ranges1.length(); i++ )
                {
                    Wish element = new Wish();
                    JSONObject currRange=ranges1.getJSONObject(i);
                    element.setProductId(currRange.getString("productId"));
                    element.setProductName(currRange.getString("productName"));
                    element.setImageUrlList(currRange.getString("imageUrlList"));
                    element.setPrice(currRange.getInt("price"));
                    listArrayWish.add(element);
                }
                chargeDepartments();
            }
            else if (response.getString("message").toString().equals("User updated"))
            {
                response=response.getJSONObject("user");
                JSONArray ranges1= response.getJSONArray("productWishList");
                listArrayWish= new ArrayList<Wish>();

                for(int i=0; i < ranges1.length(); i++ )
                {
                    Wish element = new Wish();
                    JSONObject currRange=ranges1.getJSONObject(i);
                    element.setProductId(currRange.getString("productId"));
                    element.setProductName(currRange.getString("productName"));
                    element.setImageUrlList(currRange.getString("imageUrlList"));
                    element.setPrice(currRange.getInt("price"));
                    listArrayWish.add(element);
                }

                Toast.makeText(context, "Añadido correctamente", Toast.LENGTH_SHORT).show();
                chargeDepartments();
            }
            else if (response.getString("message").toString().equals("Product already added to wishlist"))
            {
                Toast.makeText(context, "El producto ya existe en la lista de deseos", Toast.LENGTH_SHORT).show();
            }
            else {
                listArray = new ArrayList<ProductStore>();
                Gson gson = new Gson();
                JSONArray ranges = response.getJSONArray("merchantProfile");

                for (int i = 0; i < ranges.length(); i++)
                {
                    JSONObject currRange = ranges.getJSONObject(i);
                    ProductStore element = new ProductStore();
                    element.setProductId(currRange.getString("productId"));
                    element.setProductName(currRange.getString("productName"));
                    element.setDetails(currRange.getString("details"));
                    ArrayList<String> images= new ArrayList<String>();
                    JSONArray imagesArray= currRange.getJSONArray("imageUrlList");

                    if (imagesArray != null && imagesArray.length()>0)
                    {
                        for (int l = 0; l < imagesArray.length(); l++)
                        {
                            images.add(imagesArray.get(l).toString());
                            if(images.size() == 1)
                            {
                                element.setUrlImageShow(imagesArray.get(l).toString());
                            }
                        }
                    }
                    else
                    {
                        images.add("images");
                    }
                    element.setImageUrlList(images);
                    element.setPrice(currRange.getInt("price"));
                    listArray.add(element);
                }
                productWishList();
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
