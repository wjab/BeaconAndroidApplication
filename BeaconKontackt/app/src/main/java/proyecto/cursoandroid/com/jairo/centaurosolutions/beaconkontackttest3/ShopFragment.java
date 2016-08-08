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

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Department;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.ProductStore;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Store;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.TotalGiftPoints;


public class ShopFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    public CustomAdapterStore adapter;
    public ListView listviewShop;
    public ArrayList<Store> listStoreArray;
    public ArrayList<Department> listDepartments;
    public ArrayList<ProductStore> listProductStore;
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
                listDepartments = new ArrayList<Department>();
               Store storeElement = new Store();
                TotalGiftPoints points= new TotalGiftPoints();
                storeElement.setMerchantName(currRange.getString("merchantName"));
                storeElement.setAddress(currRange.getString("address"));
                storeElement.setPointToGive(currRange.getInt("pointsToGive"));
                storeElement.setCity(currRange.getString("city"));
                storeElement.setAddress(currRange.getString("address"));
               JSONObject elementPoints=currRange.getJSONObject("totalGiftPoints");
                points.setWalkin(elementPoints.getString("walkin"));
                points.setScan(elementPoints.getString("scan"));
                points.setPurchase(elementPoints.getString("purchase"));
                points.setBill(elementPoints.getString("bill"));
                storeElement.setTotalGiftPoints(points);
                storeElement.setUrlImagen(currRange.getString("image"));

                JSONArray departaments= currRange.getJSONArray("departments");
                for(int j=0; j < departaments.length(); j++ ){
                    JSONObject currDepartament = departaments.getJSONObject(j);
                    Department departmentElement = new Department();
                    departmentElement.setName(currDepartament.getString("name"));
                    departmentElement.setId(currDepartament.getString("id"));
                    departmentElement.setUrlDepartment(currDepartament.getString("departmentUrl"));
                    departmentElement.setUrlImageShop(currRange.getString("image"));
                    JSONArray products= currDepartament.getJSONArray("products");
                    listProductStore =  new ArrayList<ProductStore>();
                    for(int k=0; k < products.length(); k++ ){
                        JSONObject currProduct = products.getJSONObject(k);
                        ProductStore produtctElement = new ProductStore();
                        produtctElement.setPrice(currProduct.getLong("price"));
                        produtctElement.setProductId(currProduct.getString("productId"));
                        produtctElement.setProductName(currProduct.getString("productName"));
                        produtctElement.setDetails(currProduct.getString("details"));
                        produtctElement.setStateWishList(0);
                        ArrayList<String> images= new ArrayList<String>();
                        JSONArray imagesArray= currProduct.getJSONArray("imageUrlList");
                        if (imagesArray != null && imagesArray.length()>0)  {
                            int len = imagesArray.length();
                            for (int l=0;l<len;l++){
                                images.add(imagesArray.get(l).toString());
                            }
                        }
                        else
                        {
                            images.add("images");
                        }
                        produtctElement.setImageUrlList(images);
                        listProductStore.add(produtctElement);
                    }
                    departmentElement.setProducts(listProductStore);
                    listDepartments.add(departmentElement);
                }
                storeElement.setDepartments(listDepartments);

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
