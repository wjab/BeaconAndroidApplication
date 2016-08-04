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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.Adaptador_Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;

public class PromoFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    public Adaptador_Promo adapter;
    public ListView listviewPromo;
    public ArrayList<Promociones> listPromoArray;
    private View rootView;

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
        listviewPromo= (ListView)rootView.findViewById(R.id.listviewPromo);
        listviewPromo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Promociones promo = new Promociones();
                promo = listPromoArray.get(position);
                Intent intentSuccess = new Intent(getActivity().getBaseContext(), DetailPromo.class);
                intentSuccess.putExtra("Detail", promo);
                startActivity(intentSuccess);
            }
        });
        promoService();
       return rootView;

    }
    @Override
    public void onResponse(JSONObject response) {


        try {
            listPromoArray = new ArrayList<Promociones>();
            Gson gson= new Gson();
            JSONArray ranges= response.getJSONArray("listPromo");
            String range = "";
            String message = "";
            String messageType = "";
            String promo = "";

            for(int i=0; i < ranges.length(); i++ ){
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

            adapter=new Adaptador_Promo(getActivity(), listPromoArray);
            listviewPromo.setAdapter(adapter);
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


    public void promoService(){
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> nullMap = new HashMap<String, String>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_Promo)+"promo/";
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }



}
