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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterWish;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Wish;

public class WishListActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

public CustomAdapterWish adapter;
public ListView listView;
public ArrayList<Wish> listArray;
        String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent1 = getIntent();
        idUser = intent1.getStringExtra("idUser");
        listView= (ListView)findViewById(R.id.listviewWish);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Wish storeProduct = new Wish();
        storeProduct =  listArray.get(position);
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
            listArray= new ArrayList<Wish>();
            response=response.getJSONObject("user");
        JSONArray ranges= response.getJSONArray("productWishList");

        for(int i=0; i < ranges.length(); i++ ){
            Wish element = new Wish();
        JSONObject currRange=ranges.getJSONObject(i);
            element.setProductId(currRange.getString("productId"));
            element.setProductName(currRange.getString("productName"));
            element.setImageUrlList(currRange.getString("imageUrlList"));
            element.setPrice(currRange.getInt("price"));
            listArray.add(element);
        }

        adapter=new CustomAdapterWish(this,  listArray);
        listView.setAdapter(adapter);
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
        String url = getString(R.string.WebService_User)+"user/id/"+idUser;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

        }



        }
