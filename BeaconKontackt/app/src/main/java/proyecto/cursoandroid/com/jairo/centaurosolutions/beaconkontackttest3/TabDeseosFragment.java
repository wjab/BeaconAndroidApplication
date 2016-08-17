package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterFaq;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Faq;
import utils.NonStaticUtils;

public class TabDeseosFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    public CustomAdapterFaq adapter;
    public ListView listView;
    public ArrayList<Faq> listArray;
    String mpoints, userAcumulatedPoints;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    String idUser;

    Activity context;

    public TabDeseosFragment() {
        // Required empty public constructor
    }

    public static TabDeseosFragment newInstance() {
        TabDeseosFragment fragment = new TabDeseosFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_tab_deseos, container, false);
        listView = (ListView) rootView.findViewById(R.id.listviewFaqDeseos);
        service();
        return rootView;
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
        String url = getString(R.string.WebServiceFaq) + "faq/section/deseos/";
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }

    @Override
    public void onResponse(JSONObject response) {


        try {
            listArray = new ArrayList<Faq>();
            Gson gson = new Gson();
            JSONArray ranges = response.getJSONArray("faq");
            String range = "";
            String message = "";
            String messageType = "";
            for (int i = 0; i < ranges.length(); i++) {
                JSONObject currRange = ranges.getJSONObject(i);

                Faq element = new Faq();
                element.setQuestion(currRange.getString("question"));
                element.setAnswer(currRange.getString("answer"));


                listArray.add(element);
            }

            adapter = new CustomAdapterFaq(context, listArray);
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