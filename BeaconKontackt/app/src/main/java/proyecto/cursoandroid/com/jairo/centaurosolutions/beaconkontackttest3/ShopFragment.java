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

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterStore;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Store;


public class ShopFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    public CustomAdapterStore adapter;
    public ListView listviewShop;
    public ArrayList<Store> listStoreArray;
    private View rootView;

    public ShopFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_shops, container, false);
        listviewShop= (ListView)rootView.findViewById(R.id.listviewStores);
        listviewShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store store = new Store();
                store = listStoreArray.get(position);
                Intent intentSuccess = new Intent(getActivity().getBaseContext(), DetailShopActivity.class);
                intentSuccess.putExtra("detailShop", store);
                startActivity(intentSuccess);
            }
        });
        shopService();
        return rootView;

    }
    @Override
    public void onResponse(JSONObject response) {


        try {
            listStoreArray = new ArrayList<Store>();
            Gson gson= new Gson();
            JSONArray ranges= response.getJSONArray("merchantProfile");
            String range = "";
            String message = "";
            String messageType = "";
            String store = "";

            for(int i=0; i < ranges.length(); i++ ){
                JSONObject currRange = ranges.getJSONObject(i);

               Store storeElement = new Store();
                storeElement.setMerchantName(currRange.getString("merchantName"));
                storeElement.setAddress(currRange.getString("address"));
                storeElement.setPointToGive(currRange.getInt("pointsToGive"));
                storeElement.setAddress(currRange.getString("address"));
                storeElement.setUrlImagen(currRange.getString("image"));

                listStoreArray.add(storeElement);
            }

            adapter=new CustomAdapterStore(getActivity(), listStoreArray);
            listviewShop.setAdapter(adapter);
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


    public void shopService(){
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> nullMap = new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_MerchantProfile)+"merchantprofile/";
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }



}
