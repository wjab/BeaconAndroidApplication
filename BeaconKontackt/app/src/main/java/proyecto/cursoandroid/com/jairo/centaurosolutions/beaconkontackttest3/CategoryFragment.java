package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterCategory;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Category;

public class CategoryFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    public CustomAdapterCategory adapter;
    public ListView listView;
    public ArrayList<Category> listArray;
    private View rootView;
    private String nameCategory,urlImage;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_category, container, false);
        listView= (ListView)rootView.findViewById(R.id.listViewCategory);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nameCategory = listArray.get(position).getType();
                urlImage = listArray.get(position).getUrlImage();
                Intent intentSuccess = new Intent(getActivity().getBaseContext(), ProductCategoryActivity.class);
                intentSuccess.putExtra("name", nameCategory);
                intentSuccess.putExtra("urlImage", urlImage);
                startActivity(intentSuccess);

            }
        });
        service();
        return rootView;

    }
    @Override
    public void onResponse(JSONObject response) {


        try {
            listArray = new ArrayList<Category>();
            Gson gson= new Gson();
            JSONArray ranges= response.getJSONArray("merchantBusinessTypeResult");
            String range = "";
            String message = "";
            String messageType = "";
            String promo = "";

            for(int i=0; i < ranges.length(); i++ ){
                JSONObject currRange = ranges.getJSONObject(i);

                Category element = new Category();
                element.setId(currRange.getString("id"));
                element.setType(currRange.getString("type"));
                element.setDescription(currRange.getString("description"));
                element.setUrlImage(currRange.getString("imageUrl"));
                listArray.add(element);
            }

            adapter=new CustomAdapterCategory(getActivity(), listArray);
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
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
        String url = getString(R.string.WebService_MerchantProfile)+"merchantbusinesstype/all";
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }

}
