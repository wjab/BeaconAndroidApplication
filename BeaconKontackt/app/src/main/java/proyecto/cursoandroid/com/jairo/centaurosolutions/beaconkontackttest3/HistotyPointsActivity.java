package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterHistoryPoints;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.History;
import utils.NonStaticUtils;


public class HistotyPointsActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    public CustomAdapterHistoryPoints adapter;
    public ListView listviewHistory;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    private String mpoints, userAcumulatedPoints;
    public ArrayList<History> listHistoryArray;
    String idUser;
    Button addImage;
    LinearLayout back;
    TextView pointsAction;
    private int preLast;
    private int listCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histoty_points);
        setTitle("Historial de puntos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Intent intent1 = getIntent();
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar_promodetail,
                null);
        getSupportActionBar().setCustomView(actionBarLayout);
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        mpoints = String.valueOf(preferences.getInt("points", 0));
        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        idUser = intent1.getStringExtra("idUser");
        pointsAction.setText(userAcumulatedPoints.toString());
        listviewHistory = (ListView) findViewById(R.id.listviewHistory);
        listviewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                History storeProduct = new History();
                storeProduct = listHistoryArray.get(position);
                //Intent intentSuccess = new Intent(getActivity().getBaseContext(), DetailPromo.class);
                //intentSuccess.putExtra("Detail", store);
                //startActivity(intentSuccess);
            }
        });
        listviewHistory.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) { //to avoid multiple calls for last item
                        if (listHistoryArray == null) {
                            listCount = 0;
                        } else {
                            listCount = listHistoryArray.size();
                        }
                        shopProductService();
                        preLast = lastItem;
                    }
                } else {
                    preLast = 0;
                }
            }
        });

        back = (LinearLayout) actionBarLayout.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        addImage = (Button) actionBarLayout.findViewById(R.id.buttonAdd);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WishListActivity.class);
                intent.putExtra("idUser",idUser);
                intent.putExtra("code", 1);
                startActivityForResult(intent, 2);
            }
        });
        addImage.setText(String.valueOf(BackgroundScanActivity.size));
        shopProductService();
        return;
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

            Gson gson = new Gson();
            JSONArray ranges = response.getJSONArray("pointsData");

            String range = "";
            String message = "";
            String messageType = "";
            String storeProduct = "";
            if (listCount != ranges.length()) {
                listHistoryArray = new ArrayList<History>();
                for (int i = 0; i < ranges.length(); i++) {
                    History historyElement = new History();
                    //Agarra el array que se encuentra en la posdicion i
                    JSONObject currRange = ranges.getJSONObject(i);
                    //Array de tiendas
                    JSONObject store = currRange.getJSONObject("merchantProfile");
                    //Array de ptomo
                    JSONObject promo = currRange.getJSONObject("promo");

                    historyElement.setAdressMerchant(store.getString("address"));
                    historyElement.setPoints(currRange.getString("points"));
                    historyElement.setPromoTitle(promo.getString("title"));
                    listHistoryArray.add(historyElement);
                }

                adapter = new CustomAdapterHistoryPoints(this, listHistoryArray);
                listviewHistory.setAdapter(adapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;

    public void shopProductService() {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> nullMap = new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebServiceHistoryPointsUser) + idUser;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }


}
