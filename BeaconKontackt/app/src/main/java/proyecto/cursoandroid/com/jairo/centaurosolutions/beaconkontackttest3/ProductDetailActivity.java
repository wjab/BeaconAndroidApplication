package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterProductDepartment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.PagerAdapterImage;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.ProductStore;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Wish;
import utils.NonStaticUtils;

public class ProductDetailActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    Intent intent;
    private String mpoints, userAcumulatedPoints, urlImage;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    private static String idUser, idProduct;
    CustomAdapterProductDepartment adapter;
    private TextView pointsAction, name, price, details;
    private ImageView openHistoryPoints;
    private Button addImage;
    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    public static ArrayList<Wish> listArrayWish;
    Response.ErrorListener responseError;
    private ProductStore product;
    private ViewPager pager;
    private ImageView photo;
    LinearLayout back;
    private ArrayList<String> images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        /* Obtiene de las preferencias compartidas, la cantidad de los puntos*/
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mpoints = String.valueOf(preferences.getInt("points", 0));
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        idUser = preferences.getString("userId", "");
        intent = getIntent();
        product = (ProductStore) intent.getSerializableExtra("product");
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_promodetail, null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        back = (LinearLayout) actionBarLayout.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("code", 1);
                setResult(2, intent);
                finish();
            }
        });

        getSupportActionBar().setCustomView(actionBarLayout);
        openHistoryPoints = (ImageView) actionBarLayout.findViewById(R.id.openHistoryPoints);
        addImage = (Button) actionBarLayout.findViewById(R.id.buttonAdd);
        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        name = (TextView) findViewById(R.id.nameProductDetail);
        price = (TextView) findViewById(R.id.priceProductDetail);
        details = (TextView) findViewById(R.id.detailsProductDetail);
        photo = (ImageView) findViewById(R.id.photo);
        pager = (ViewPager) findViewById(R.id.pager);
        addImage.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_add));
        pointsAction.setText(userAcumulatedPoints.toString());
        name.setText(product.getProductName());
        price.setText(String.format(Integer.toString(product.getPointsByPrice())));
        details.setText(product.getDetails());
        images = product.getImageUrlList();
        idProduct = product.getProductId();
        images = product.getImageUrlList();
        int len = images.size();
        for (int l = 0; l < len; l++) {
            if (l == 0) {
                urlImage = images.get(l).toString();
            }
        }

        pager.setAdapter(new PagerAdapterImage(this, images));

        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mpoints.equals("0")) {
                    Toast.makeText(getApplication(), getString(R.string.dontHavePoints), Toast.LENGTH_SHORT).show();
                } else {
                    openHistory();
                }
            }
        });
        openHistoryPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mpoints.equals("0")) {
                    Toast.makeText(getApplication(), getString(R.string.dontHavePoints), Toast.LENGTH_SHORT).show();
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
        productWishList();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        int code = intent.getIntExtra("code", 0);
        if (code == 1) {
            super.onActivityResult(requestCode, resultCode, intent);
            addImage.setText(String.valueOf(BackgroundScanActivity.size));
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("code", 1);
        setResult(2, intent);
        finish();
        super.onBackPressed();
    }

    public void openHistory() {
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser", idUser);
        intent.putExtra("code", 1);
        startActivityForResult(intent, 2);
    }

    public void service() {
        serviceController = new ServiceController();
        responseError = this;
        response = this;
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", idUser);
        mapParams.put("productId", product.getProductId());
        mapParams.put("productName", product.getProductName());
        mapParams.put("price", product.getPrice());
        mapParams.put("pointsByPrice", product.getPointsByPrice());
        mapParams.put("imageUrlList", urlImage);
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_User) + "user/wishlist/add";
        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }

    public void productWishList() {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> nullMap = new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_User) + "user/id/" + idUser;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private void searchProductInWishList() {
        String idProductWishList;

        for (int i = 0; i < listArrayWish.size(); i++) {
            idProductWishList = listArrayWish.get(i).getProductId();

            if (idProductWishList.equals(idProduct)) {
                addImage.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_added));
            }
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            if (response.getString("message").toString().equals("")) {
                response = response.getJSONObject("user");
                JSONArray ranges1 = response.getJSONArray("productWishList");
                listArrayWish = new ArrayList<Wish>();

                for (int i = 0; i < ranges1.length(); i++) {
                    Wish element = new Wish();
                    JSONObject currRange = ranges1.getJSONObject(i);
                    element.setProductId(currRange.getString("productId"));
                    element.setProductName(currRange.getString("productName"));
                    element.setImageUrlList(currRange.getString("imageUrlList"));
                    element.setPrice(currRange.getInt("price"));
                    listArrayWish.add(element);
                }
                searchProductInWishList();
            } else if (response.getString("message").toString().equals("User updated")) {
                Toast.makeText(this, "Añadido correctamente", Toast.LENGTH_SHORT).show();
                response = response.getJSONObject("user");
                JSONArray ranges1 = response.getJSONArray("productWishList");
                listArrayWish = new ArrayList<Wish>();

                for (int i = 0; i < ranges1.length(); i++) {
                    Wish element = new Wish();
                    JSONObject currRange = ranges1.getJSONObject(i);
                    element.setProductId(currRange.getString("productId"));
                    element.setProductName(currRange.getString("productName"));
                    element.setImageUrlList(currRange.getString("imageUrlList"));
                    element.setPrice(currRange.getInt("price"));
                    listArrayWish.add(element);
                }
                searchProductInWishList();
                BackgroundScanActivity.size = listArrayWish.size();
            } else if (response.getString("message").toString().equals("Product already added to wishlist")) {
                Toast.makeText(this, getString(R.string.productExist), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

