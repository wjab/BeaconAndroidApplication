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
import android.widget.AbsListView;
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


public class TabSesionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    public CustomAdapterFaq adapter;
    public ListView listView;
    public ArrayList<Faq> listArray;
    String mpoints, userAcumulatedPoints;
    ;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    String idUser;
    private int preLast;
    private int listCount = 0;
    Activity context;

    public TabSesionFragment() {
        // Required empty public constructor
    }


    public static TabSesionFragment newInstance() {
        TabSesionFragment fragment = new TabSesionFragment();

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
        View rootView = inflater.inflate(R.layout.fragment_tab_sesion, container, false);
        listView = (ListView) rootView.findViewById(R.id.listviewFaqSesion);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) { //to avoid multiple calls for last item
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
        String url = getString(R.string.WebServiceFaq) + "faq/section/sesion/";
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }

    @Override
    public void onResponse(JSONObject response) {


        try {
            Gson gson = new Gson();
            JSONArray ranges = response.getJSONArray("faq");
            String range = "";
            String message = "";
            String messageType = "";
            if (listCount != ranges.length()) {
                listArray = new ArrayList<Faq>();
                for (int i = 0; i < ranges.length(); i++) {
                    JSONObject currRange = ranges.getJSONObject(i);

                    Faq element = new Faq();
                    element.setQuestion(currRange.getString("question"));
                    element.setAnswer(currRange.getString("answer"));


                    listArray.add(element);
                }

                adapter = new CustomAdapterFaq(context, listArray);
                listView.setAdapter(adapter);
                listView.setSelection(preLast - 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error Faq Error", error.toString());
    }
}
