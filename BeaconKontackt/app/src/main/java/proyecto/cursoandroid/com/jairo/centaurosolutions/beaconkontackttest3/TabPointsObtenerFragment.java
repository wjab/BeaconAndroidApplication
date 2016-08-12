package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import utils.NonStaticUtils;


public class TabPointsObtenerFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    private CharSequence mpoints;
    private String userAcumulatedPoints;
    private TextView points, codeToUse;
    private Button sendData;
    private String idUser, code;
    private int pointsObtain,pointsUser;
    public TabPointsObtenerFragment() {
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
        View view=inflater.inflate(R.layout.fragment_tab_points_obtener, container, false);
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(getContext());
        idUser = preferences.getString("userId", "");
        mpoints = getContext().getSharedPreferences("SQ_UserLogin", getContext().MODE_PRIVATE).getInt("points", 0)+"";
        userAcumulatedPoints = String.format(getString(R.string.obtainPoints));
        codeToUse=(TextView)view.findViewById(R.id.code);
        sendData=(Button)view.findViewById(R.id.gift_points);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(codeToUse.getText().toString().equals("")){
                    Toast.makeText(getContext(),  getString(R.string.pointsMount), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    code=codeToUse.getText().toString();

                    pointsUser=Integer.parseInt(mpoints.toString());

                        Toast.makeText(getContext(),  getString(R.string.process), Toast.LENGTH_SHORT).show();
                        service();
                }
            }
        });

        points=(TextView)view.findViewById(R.id.messagePoints);
        points.setText(userAcumulatedPoints);
        return view;
    }

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;

    public void service()
    {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId",idUser);
        mapParams.put("code",code);
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_Utils)+"utils/redeemPoints";
        Log.e("URL",url);
        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }


    @Override
    public void onResponse(JSONObject response) {

        try {
            if(response.get("message").equals("Código invalido")){
                Toast.makeText(getContext(), "Código invalido", Toast.LENGTH_SHORT).show();
            }
            else {
                response = response.getJSONObject("pointsData");
                JSONObject responseUser = response.getJSONObject("user");
                if (responseUser.getBoolean("enable")) {
                    Map<String,String> map = new HashMap<>();
                    if (!responseUser.getString("socialNetworkJson").isEmpty()){
                        String jsonFace= responseUser.getString("socialNetworkJson");
                        jsonFace = jsonFace.substring(1, jsonFace.length()-1);           //remove curly brackets
                        String[] keyValuePairs = jsonFace.split(",");              //split the string to creat key-value pairs

                        for(String pair : keyValuePairs)                        //iterate over the pairs
                        {
                            String[] entry = pair.split("=");                   //split the pairs to get key and value
                            map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
                        }
                    }
                    nonStaticUtils.saveLogin(getContext(),
                            responseUser.getString("user"),
                            responseUser.getString("password"),
                            responseUser.getString("id"),
                            responseUser.getInt("totalGiftPoints"),
                            true,
                            responseUser.getString("socialNetworkType"),
                            responseUser.getString("socialNetworkId"),
                            preferences.getString("pathImage", ""),
                            (responseUser.getString("name") != null ? responseUser.getString("name").toString() : ""),
                            (responseUser.getString("lastName") != null ? responseUser.getString("lastName").toString() : ""),
                            (responseUser.getString("phone") != null ? responseUser.getString("phone").toString() : ""),
                            (responseUser.getString("email") != null ? responseUser.getString("email").toString() : ""),
                            (responseUser.getString("gender") != null ? responseUser.getString("gender").toString() : ""),
                            (map.get("birthday") != null ? map.get("birthday").toString() : ""));
                    Intent intent = new Intent(getContext(), ObtainPointsActivity.class);
                    int points=response.getInt("points");
                    intent.putExtra("points", points);
                    startActivity(intent);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error Dialog Error", error.toString());
    }

}
