package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.Adaptador_Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;

public class PromoFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    public Adaptador_Promo adapter;
    public ListView listviewPromo;
    private static Button addImage;
    public ArrayList<Promociones> listPromoArray;
    private View rootView;
    private int preLast;
    private int listCount = 0;

    public PromoFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_promo, container, false);
        listviewPromo = (ListView) rootView.findViewById(R.id.listviewPromo);
        addImage = (Button) getActivity().findViewById(R.id.buttonAdd);
        listviewPromo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Promociones promo = new Promociones();
                promo = listPromoArray.get(position);
                Intent intentSuccess = new Intent(getActivity().getBaseContext(), DetailPromo.class);
                intentSuccess.putExtra("Detail", promo);
                intentSuccess.putExtra("code", 1);
                startActivityForResult(intentSuccess, 2);
            }
        });
        listviewPromo.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) { //to avoid multiple calls for last item
                        if (listPromoArray == null) {
                            listCount = 0;
                        } else {
                            listCount = listPromoArray.size();
                        }
                        promoService();
                        preLast = lastItem;
                    }
                } else {
                    preLast = 0;
                }
            }
        });

        promoService();
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
            JSONArray ranges = response.getJSONArray("listPromo");
            String range = "";
            String message = "";
            String messageType = "";
            String promo = "";
            if (listCount != ranges.length()) {
                listPromoArray = new ArrayList<Promociones>();
                for (int i = 0; i < ranges.length(); i++) {
                    JSONObject currRange = ranges.getJSONObject(i);
                    Promociones promoelement = new Promociones();
                    promoelement.setTitulo(currRange.getString("title"));
                    promoelement.setDescripcion(currRange.getString("description"));
                    promoelement.setPuntos(currRange.getInt("giftPoints"));
                    promoelement.setUrlImagen(currRange.getString("images"));
                    promoelement.setMerchantId(currRange.getString("merchantId"));
                    promoelement.setDepartamentId(currRange.getString("departamentId"));
                    promoelement.setIdProduct(currRange.getString("idProduct"));
                    promoelement.setId(currRange.getString("id"));
                    listPromoArray.add(promoelement);
                }
                adapter = new Adaptador_Promo(getActivity(), listPromoArray);
                listviewPromo.setAdapter(adapter);
                listviewPromo.setSelection(preLast - 1);
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


    public void promoService() {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> nullMap = new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_Promo) + "promo/";
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }


}
