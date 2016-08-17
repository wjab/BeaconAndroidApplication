package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
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

public class CategoryFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    public CustomAdapterCategory adapter;
    public ListView listView;
    public ArrayList<Category> listArray;
    private View rootView;
    private String nameCategory, urlImage, type;
    private int preLast;
    private int listCount = 0;
    public Button addImage;

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
        addImage = (Button) getActivity().findViewById(R.id.buttonAdd);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nameCategory = listArray.get(position).getDescription();
                urlImage = listArray.get(position).getUrlImage();
                type = listArray.get(position).getType();
                Intent intentSuccess = new Intent(getActivity().getBaseContext(), ProductCategoryActivity.class);
                intentSuccess.putExtra("name", nameCategory);
                intentSuccess.putExtra("urlImage", urlImage);
                intentSuccess.putExtra("type", type);
                intentSuccess.putExtra("code", 1);
                startActivityForResult(intentSuccess, 2);

            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        if (listArray == null) {
                            listCount = 0;
                        } else {
                            listCount = listArray.size();
                        }
                        service();
                        preLast = lastItem;
                    }
                } else {
                    preLast = 0;
                }
            }
        });

        service();
        return rootView;

    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        int code = intent.getIntExtra("code", 0);
        if (code == 1) {
            super.onActivityResult(requestCode, resultCode, intent);
            addImage.setText(String.valueOf(BackgroundScanActivity.size));
        }
    }
    @Override
    public void onResponse(JSONObject response) {
        try {

            Gson gson = new Gson();
            JSONArray ranges = response.getJSONArray("merchantBusinessTypeResult");
            String range = "";
            String message = "";
            String messageType = "";
            String promo = "";
            if (listCount != ranges.length()) {
                listArray = new ArrayList<Category>();
                for (int i = 0; i < ranges.length(); i++) {
                    JSONObject currRange = ranges.getJSONObject(i);

                    Category element = new Category();
                    element.setId(currRange.getString("id"));
                    element.setType(currRange.getString("type"));
                    element.setDescription(currRange.getString("description"));
                    element.setUrlImage(currRange.getString("imageUrl"));
                    listArray.add(element);
                }
                adapter = new CustomAdapterCategory(getActivity(), listArray);
                listView.setAdapter(adapter);
                listView.setSelection(preLast - 1);
            }
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


    public void service() {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> nullMap = new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_MerchantProfile) + "merchantbusinesstype/all";
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }

}
