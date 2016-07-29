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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import utils.NonStaticUtils;


public class TabPointsRedimirFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    private CharSequence mpoints;
    private String userAcumulatedPoints;
    private TextView points ,pointsToGift;
    private Button sendData;
    private String idUser;
    String code = null;
    private int pointsIntGift,pointsUser;
    public TabPointsRedimirFragment() {
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
        View view=inflater.inflate(R.layout.fragment_tab_points_redimir, container, false);
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(getContext());
        idUser = preferences.getString("userId", "");
        mpoints = getContext().getSharedPreferences("SQ_UserLogin", getContext().MODE_PRIVATE).getInt("points", 0)+"";
        userAcumulatedPoints = String.format(getString(R.string.changePoints), mpoints);
        points=(TextView)view.findViewById(R.id.messagePoints);
        points.setText(userAcumulatedPoints);
        pointsToGift=(TextView)view.findViewById(R.id.points);

        sendData=(Button)view.findViewById(R.id.gift_pointsR);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if(pointsToGift.getText().toString().equals("")){
                 Toast.makeText(getContext(), "Favor ingresar un monto de puntos", Toast.LENGTH_SHORT).show();
             }
                else
             {
                 pointsIntGift=Integer.parseInt(pointsToGift.getText().toString());
                pointsUser=Integer.parseInt(mpoints.toString());
                 if(pointsIntGift>=pointsUser){
                     Toast.makeText(getContext(), "No tiene la cantidad de puntos necesarios para enviar esa cantidad", Toast.LENGTH_SHORT).show();
                 }
                 else
                 {
                     Toast.makeText(getContext(), "Procesando Solicitud", Toast.LENGTH_SHORT).show();
                     service();
                 }
             }
            }
        });

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
        mapParams.put("points",pointsIntGift);
        mapParams.put("type","redimir");
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebServicePoints)+"points";
        Log.e("URL",url);
        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }

    @Override
    public void onResponse(JSONObject response) {

        try {


            if(response.getString("message")=="null"){
                JSONObject object = response.getJSONObject("points");
                code=object.getString("code");
                serviceUpdatePoints();
            }
            if(response.getString("message").equals("User updated"))
            {
                response = response.getJSONObject("user");
                if (response.getBoolean("enable")) {
                    nonStaticUtils.saveLogin(getContext(),
                            response.getString("user"),
                            response.getString("password"),
                            response.getString("id"),
                            response.getInt("totalGiftPoints"),
                            true,
                            response.getString("socialNetworkType"),
                            response.getString("socialNetworkId"),
                            response.getString("pathImage"));

                    Intent intent = new Intent(getContext(), RedimirRegalarActivity.class);
                    intent.putExtra("type", "redimir");
                    intent.putExtra("code", code);
                    startActivity(intent);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void serviceUpdatePoints(){
        serviceController = new ServiceController();
        responseError = this;
        response = this;
        pointsUser=pointsUser-pointsIntGift;
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("totalGiftPoints",pointsUser);
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_User)+"user/setPoints/"+idUser;
        Log.e("URL",url);
        serviceController.jsonObjectRequest(url, Request.Method.PUT, mapParams, map, response, responseError);

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error Dialog Error", error.toString());
    }
}
