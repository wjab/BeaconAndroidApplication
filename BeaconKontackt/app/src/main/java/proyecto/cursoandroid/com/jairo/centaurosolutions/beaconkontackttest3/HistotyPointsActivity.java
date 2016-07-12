package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterHistoryPoints;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterProductStore;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.History;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.ProductStore;

public class HistotyPointsActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    public CustomAdapterHistoryPoints adapter;
    public ListView listviewHistory;
    public ArrayList<History> listHistoryArray;
    String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histoty_points);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent1 = getIntent();
        idUser = intent1.getStringExtra("idUser");
        listviewHistory= (ListView)findViewById(R.id.listviewHistory);
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
            listHistoryArray = new ArrayList<History>();
            Gson gson= new Gson();
            JSONArray ranges= response.getJSONArray("pointsData");

            String range = "";
            String message = "";
            String messageType = "";
            String storeProduct = "";

            for(int i=0; i < ranges.length(); i++ ){
                History historyElement = new History();
                //Agarra el array que se encuentra en la posdicion i
                JSONObject currRange=ranges.getJSONObject(i);
                //Array de tiendas
                JSONObject store= currRange.getJSONObject("merchantProfile");
                //Array de ptomo
                JSONObject promo= currRange.getJSONObject("promo");

                historyElement.setAdressMerchant(store.getString("address"));
                historyElement.setPoints(currRange.getString("points"));
                historyElement.setPromoTitle(promo.getString("title"));
            listHistoryArray.add(historyElement);
            }

            adapter=new CustomAdapterHistoryPoints(this, listHistoryArray);
            listviewHistory.setAdapter(adapter);
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
        String url = getString(R.string.WebServiceHistoryPointsUser)+idUser;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }

  

}
