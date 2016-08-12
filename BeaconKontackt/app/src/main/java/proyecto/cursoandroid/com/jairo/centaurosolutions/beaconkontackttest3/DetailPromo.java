package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import model.cache.BeaconCache;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterWish;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Wish;
import utils.NonStaticUtils;

public class DetailPromo extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    TextView tituloPromo, descripcionPromo, points, pointsAction, descriptionMerchant, nameMerchant, pointsToGive;
    ImageView imagenPromo, openHistoryPoints, share, imageMerchant;
    String mpoints, userAcumulatedPoints;
    ArrayList<BeaconCache> myBeaconCacheList;
    Button addImage;
    Intent intent;
    String idMechantProfile, idPromo, idProduct;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    public CustomAdapterWish adapter;
    public ListView listView;
    public ArrayList<Wish> listArray;
    String idUser;
    Promociones promo;
    LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__promo);
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        /* Obtiene de las preferencias compartidas, la cantidad de los puntos*/
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        mpoints = preferences.getInt("points", 0) + "";
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        idUser = preferences.getString("userId", "");
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_promodetail, null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        back = (LinearLayout) actionBarLayout.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();

            }
        });

        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        pointsAction.setText(userAcumulatedPoints.toString());

        tituloPromo = (TextView) findViewById(R.id.tituloPromo);
        points = (TextView) findViewById(R.id.puntosPromoDetail);
        descripcionPromo = (TextView) findViewById(R.id.descriptionPromoDetai);
        imagenPromo = (ImageView) findViewById(R.id.imagenPromoDetail);
        imageMerchant = (ImageView) findViewById(R.id.imageMerchant);
        descriptionMerchant = (TextView) findViewById(R.id.detailMerchant);
        nameMerchant = (TextView) findViewById(R.id.merchantName);
        pointsToGive = (TextView) findViewById(R.id.pointsToGive);
        intent = getIntent();
        openHistoryPoints = (ImageView) actionBarLayout.findViewById(R.id.openHistoryPoints);
        share = (ImageView) findViewById(R.id.share);
        // wish=(Button)findViewById(R.id.wishButton);
        addImage = (Button) actionBarLayout.findViewById(R.id.buttonAdd);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WishListActivity.class);
                intent.putExtra("idUser", idUser);
                startActivity(intent);
            }
        });
        promo = (Promociones) intent.getSerializableExtra("Detail");
        ServiceController imageRequest = new ServiceController();
        points.setText(promo.getPuntos() + " pts");
        tituloPromo.setText(promo.getTitulo());
        descripcionPromo.setText(promo.getDescripcion());
        idMechantProfile = promo.getMerchantId();
        idPromo = promo.getId();
        idProduct = promo.getIdProduct();

        if ((!idMechantProfile.isEmpty()) && (!idPromo.isEmpty()) && (!idProduct.isEmpty())) {
            searchProductPromo(idPromo, idMechantProfile, idProduct);
        }
        imageRequest.imageRequest(promo.getUrlImagen(), imagenPromo, 0, 0);
        final String description = descripcionPromo.getText().toString() + " " + promo.getUrlImagen();
        final String image = promo.getUrlImagen();
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
        /*wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               searchProductPromo();
            }
        });*/
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, description);

                startActivity(Intent.createChooser(intent, "Share with"));
            }
        });

        dataMerchant();
        myBeaconCacheList = (ArrayList<BeaconCache>) intent.getSerializableExtra("promoDetail");

        if (myBeaconCacheList != null) {
            int indextoDelete = -1;
            for (int i = 0; i < myBeaconCacheList.size(); i++) {
                if (myBeaconCacheList.get(i).promoId.equals(promo.getId())) {
                    indextoDelete = i;
                }
            }
            if (indextoDelete != -1) {
                myBeaconCacheList.remove(indextoDelete);
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (myBeaconCacheList != null) {
            Intent redirectIntent = new Intent(getApplicationContext(), PullNotificationsActivity.class);
            redirectIntent.putExtra("promoDetail", myBeaconCacheList);
            startActivity(redirectIntent);
        } else {
            this.finish();
        }
    }

    public void openHistory() {
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            if (response.getString("message").equals("null")) {
                response = response.getJSONObject("productData");
                response = response.getJSONObject("product");
                String id, name, url = null;
                double price;
                price = response.getDouble("price");
                pointsToGive.setText(String.format(getString(R.string.colonSymbol), Double.toString(price)));
                ArrayList<String> images = new ArrayList<String>();
                JSONArray imagesArray = response.getJSONArray("imageUrlList");
                if (imagesArray != null && imagesArray.length() > 0) {
                    int len = imagesArray.length();
                    for (int l = 0; l < len; l++) {
                        images.add(imagesArray.get(l).toString());
                        if (images.size() == 1) {
                            url = imagesArray.get(l).toString();
                        }
                    }
                } else {
                    images.add("images");
                }
                //service(id,name,price,url);
            } else if (response.getString("message").toString().equals("Perfil de tienda encontrado")) {
                response = response.getJSONObject("merchantProfile");
                descriptionMerchant.setText(response.getString("address"));
                nameMerchant.setText(response.getString("merchantName"));
                Picasso.with(this).load(response.getString("image")).into(imageMerchant);
            } else {
                if (response.getString("message").toString().equals("User updated")) {
                    Toast.makeText(getApplication(), "Añadido correctamente", Toast.LENGTH_SHORT).show();
                } else if (response.getString("message").toString().equals("Product already added to wishlist")) {
                    Toast.makeText(getApplication(), "El producto ya existe en la lista de deseos", Toast.LENGTH_SHORT).show();
                } else if (response.getString("message").toString().equals("Perfil de tienda no encontrado")) {
                    Toast.makeText(getApplication(), "Esta promocion no se encuentra asociada a una tienda", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;

    public void service(String productId, String productName, int price, String urlImage) {
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
        String url = getString(R.string.WebService_User) + "user/wishlist/add";
        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }

    public void searchProductPromo(String promoId, String merchantId, String productId) {
        serviceController = new ServiceController();
        responseError = this;
        response = this;
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("merchantId", merchantId);
        mapParams.put("productId", productId);
        mapParams.put("promoId", promoId);
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_Utils) + "utils/product/getdata";
        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }

    public void dataMerchant() {
        serviceController = new ServiceController();
        responseError = this;
        response = this;
        Map<String, String> nullMap = new HashMap<String, String>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_MerchantProfile) + "merchantprofile/" + idMechantProfile;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);
    }
}
