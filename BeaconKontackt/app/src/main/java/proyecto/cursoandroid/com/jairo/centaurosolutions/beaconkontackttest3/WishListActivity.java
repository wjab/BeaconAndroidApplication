package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.StyleableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterWish;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Wish;
import utils.NonStaticUtils;

public class WishListActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    public CustomAdapterWish adapter;
    private static ListView listView;
    public ArrayList<Wish> listArray;
    private static String idUser, toastDelete, toast;
    private static Activity context;
    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    private static String webServiceUser;
    ImageView openHistoryPoints;
    LinearLayout back;
    private int size;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    private static Button addImage;
    private String mpoints, userAcumulatedPoints;
    TextView pointsAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        setTitle("Lista de deseos");
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar_promodetail,
                null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        mpoints = String.valueOf(preferences.getInt("points", 0));
        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        pointsAction.setText(userAcumulatedPoints.toString());
        back = (LinearLayout) actionBarLayout.findViewById(R.id.back);
        openHistoryPoints = (ImageView) actionBarLayout.findViewById(R.id.openHistoryPoints);
        addImage = (Button) actionBarLayout.findViewById(R.id.buttonAdd);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("code",1);
                setResult(2,intent);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        Intent intent1 = getIntent();
        size = intent1.getIntExtra("sizeWishList", 0);
        addImage.setText(String.valueOf(BackgroundScanActivity.size));
        idUser = intent1.getStringExtra("idUser");
        webServiceUser = getString(R.string.WebService_User);
        listView = (ListView) findViewById(R.id.listviewWish);
        context = this;
        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mpoints.equals("0")) {
                    Toast.makeText(getApplication(), "Aún no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                } else {
                    openHistory();
                }
            }
        });
        openHistoryPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mpoints.equals("0")) {
                    Toast.makeText(getApplication(), "Aun no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                } else {
                    openHistory();
                }
            }
        });
        shopProductService();
        return;
    }

    public void openHistory() {
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser", idUser);
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
            if (response.getString("message").toString().equals("User updated")) {

                Toast.makeText(context, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
            }
            response = response.getJSONObject("user");
            JSONArray ranges = response.getJSONArray("productWishList");
            listArray = new ArrayList<Wish>();
            for (int i = 0; i < ranges.length(); i++) {
                Wish element = new Wish();
                JSONObject currRange = ranges.getJSONObject(i);
                element.setProductId(currRange.getString("productId"));
                element.setProductName(currRange.getString("productName"));
                element.setImageUrlList(currRange.getString("imageUrlList"));
                element.setPrice(currRange.getInt("price"));
                listArray.add(element);
            }
            BackgroundScanActivity.size = ranges.length();
            addImage.setText(String.valueOf(BackgroundScanActivity.size));
            adapter = new CustomAdapterWish(context, listArray);
            listView.setAdapter(adapter);

            if (ranges.length() == 0) {
                Toast.makeText(context, "No hay productos en la lista", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void shopProductService() {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> nullMap = new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = webServiceUser + "user/id/" + idUser;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }

    public void serviceDeleteWish(String id, String name, int price, String urlP) {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", idUser);
        mapParams.put("productId", id);
        mapParams.put("productName", name);
        mapParams.put("price", price);
        mapParams.put("imageUrlList", urlP);

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = webServiceUser + "user/wishlist/delete";
        Log.e("URL", url);
        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent();
        intent.putExtra("code",1);
        setResult(2,intent);
        finish();
        super.onBackPressed();
    }
}
