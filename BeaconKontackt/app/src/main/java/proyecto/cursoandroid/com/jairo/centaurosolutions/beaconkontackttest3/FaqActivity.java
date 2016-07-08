package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterFaq;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Faq;

public class FaqActivity extends  AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    public CustomAdapterFaq adapter;
    public ListView listView;
    public ArrayList<Faq> listArray;
    String mpoints,userAcumulatedPoints;;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar_promodetail,
                null);
        listView= (ListView)findViewById(R.id.listviewFaq);
        mpoints = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getInt("points", 0)+"";
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel),mpoints);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        TextView pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        pointsAction.setText(userAcumulatedPoints.toString());
        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistory();
            }
        });
        service();
        return;
    }
    public void openHistory(){
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;

    public void service(){
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> nullMap = new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebServiceFaq)+"faq/";
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }
    @Override
    public void onResponse(JSONObject response) {


        try {
            listArray = new ArrayList<Faq>();
            Gson gson= new Gson();
            JSONArray ranges= response.getJSONArray("faq");
            String range = "";
            String message = "";
            String messageType = "";

            for(int i=0; i < ranges.length(); i++ ){
                JSONObject currRange = ranges.getJSONObject(i);

                Faq element = new Faq();
                element.setQuestion(currRange.getString("question"));
                element.setAnswer(currRange.getString("answer"));


                listArray.add(element);
            }

            adapter=new CustomAdapterFaq(this, listArray);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error Faq Error", error.toString());
    }
}
