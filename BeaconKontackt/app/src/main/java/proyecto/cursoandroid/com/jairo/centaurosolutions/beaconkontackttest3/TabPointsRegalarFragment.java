package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
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


public class TabPointsRegalarFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    private CharSequence mpoints;
    private String userAcumulatedPoints;
    private TextView points ,pointsToGift,message;
    private Button sendData;
    private String idUser;
    private String code,dateExpiration, messageToSend = null;
    private int pointsIntGift,pointsUser;
    private static int pointsMin;
    public TabPointsRegalarFragment() {
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
        View view=inflater.inflate(R.layout.fragment_tab_points_regalar, container, false);
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(getContext());
        idUser = preferences.getString("userId", "");
        mpoints = getContext().getSharedPreferences("SQ_UserLogin", getContext().MODE_PRIVATE).getInt("points", 0)+"";
        userAcumulatedPoints = String.format(getString(R.string.giftPoints), mpoints);
        points=(TextView)view.findViewById(R.id.messagePoints);
        pointsToGift=(TextView)view.findViewById(R.id.points);
        message=(TextView)view.findViewById(R.id.message);
        sendData=(Button)view.findViewById(R.id.gift_points);
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
                        Toast.makeText(getContext(), getString(R.string.dontHavePointsNecessary), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(), getString(R.string.process), Toast.LENGTH_SHORT).show();
                        messageToSend=message.getText().toString();
                        service();


                    }
                }
            }
        });
        servicePointsMin();
        points.setText(userAcumulatedPoints+" ,esta es la cantidad minima de puntos: "+String.valueOf(pointsMin));
        return view;
    }

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
    public void servicePointsMin(){
        serviceController = new ServiceController();
        responseError = this;
        response = this;
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_Utils)+"utils/pointsMin";
        Log.e("URL",url);
        serviceController.jsonObjectRequest(url, Request.Method.GET,null, map, response, responseError);
    }

    public void service()
    {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId",idUser);
        mapParams.put("points",pointsIntGift);
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_Utils)+"utils/exchangePoints";
        Log.e("URL",url);
        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }

    public void serviceUpdatePoints(){
        serviceController = new ServiceController();
        responseError = this;
        response = this;
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_User)+"user/id/"+idUser;
        Log.e("URL",url);
        serviceController.jsonObjectRequest(url, Request.Method.GET,null, map, response, responseError);

    }

    @Override
    public void onResponse(JSONObject response) {

        try {
            if(response.get("message").equals("Saldo flotante creado")) {
                JSONObject object = response.getJSONObject("points");
                code = object.getString("code");
                dateExpiration=object.getString("expirationDate");
                serviceUpdatePoints();
            }
            else if(response.get("message").equals("La cantidad de puntos no supera el minimo requerido")){
                Toast.makeText(getContext(), getString(R.string.minPointsRequire), Toast.LENGTH_SHORT).show();
            }
            else if(response.get("message").equals("Points")){
                pointsMin= response.getInt("minPoints");
                points.setText(userAcumulatedPoints+" ,esta es la cantidad minima de puntos: "+String.valueOf(pointsMin));
            }
            else {
                response = response.getJSONObject("user");
                if (response.getBoolean("enable")) {
                    Map<String,String> map = new HashMap<>();
                    if (!response.getString("socialNetworkJson").isEmpty()){
                        String jsonFace= response.getString("socialNetworkJson");
                        jsonFace = jsonFace.substring(1, jsonFace.length()-1);           //remove curly brackets
                        String[] keyValuePairs = jsonFace.split(",");              //split the string to creat key-value pairs

                        for(String pair : keyValuePairs)                        //iterate over the pairs
                        {
                            String[] entry = pair.split("=");                   //split the pairs to get key and value
                            map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
                        }
                    }
                    nonStaticUtils.saveLogin(getContext(),
                            response.getString("user"),
                            response.getString("password"),
                            response.getString("id"),
                            response.getInt("totalGiftPoints"),
                            true,
                            response.getString("socialNetworkType"),
                            response.getString("socialNetworkId"),
                            response.getString("pathImage"),
                            (response.getString("name") != null ? response.getString("name").toString() : ""),
                            (response.getString("lastName") != null ? response.getString("lastName").toString() : ""),
                            (response.getString("phone") != null ? response.getString("phone").toString() : ""),
                            (response.getString("email") != null ? response.getString("email").toString() : ""),
                            (response.getString("gender") != null ? response.getString("gender").toString() : ""),
                            (map.get("birthday") != null ? map.get("birthday").toString() : ""));
                    Intent intent = new Intent(getContext(), RedimirRegalarActivity.class);
                    intent.putExtra("type", "regalar");
                    intent.putExtra("message",messageToSend);
                    intent.putExtra("code", code);
                    intent.putExtra("expiration", dateExpiration);
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
