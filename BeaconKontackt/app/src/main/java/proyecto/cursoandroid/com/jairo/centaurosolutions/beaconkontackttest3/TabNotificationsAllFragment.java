package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;

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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.Adaptador_Notifications;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.Adaptador_Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Notification;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;
import utils.NonStaticUtils;


public class TabNotificationsAllFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    private CharSequence mpoints;
    private String userAcumulatedPoints;
    private TextView points, pointsToGift;
    private Button sendData;
    private String idUser;
    ListView listviewNotification;
    String code = null;
    public ArrayList<Notification> notifications;
    public Adaptador_Notifications adapter;
    private int pointsIntGift, pointsUser;

    public TabNotificationsAllFragment() {
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
        View view = inflater.inflate(R.layout.fragment_tab_all_notifications, container, false);
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(getContext());
        idUser = preferences.getString("userId", "");
        mpoints = getContext().getSharedPreferences("SQ_UserLogin", getContext().MODE_PRIVATE).getInt("points", 0) + "";
        userAcumulatedPoints = String.format(getString(R.string.changePoints), mpoints);

        listviewNotification = (ListView) view.findViewById(R.id.listNotifications);
        listviewNotification.setAdapter(adapter);
        listviewNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notification notification = new Notification();
                notification = notifications.get(position);
                FragmentManager fm = getFragmentManager();
                Bundle args = new Bundle();
                args.putString("id", notification.getId());
                args.putString("message", notification.getMessage());

                MessageNotificationDialogFragment p = new MessageNotificationDialogFragment();
                p.setArguments(args);
                p.show(fm, "tag");


            }
        });
        service();
        return view;
    }

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;

    public void service() {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = getString(R.string.WebService_Utils) + "/notification/all/" + idUser;
        Log.e("URL", url);
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);
    }


    @Override
    public void onResponse(JSONObject response) {

        try {
            if (response.getJSONArray("notificationResult").length() > 0) {
                JSONArray notificationList = response.getJSONArray("notificationResult");
                notifications = new ArrayList<Notification>();
                for (int i = 0; i < notificationList.length(); i++) {
                    JSONObject row = notificationList.getJSONObject(i);

                    ArrayList<String> images = new ArrayList<String>();
                    String url = "";

                    Notification notification;
                    notification = new Notification();
                    notification.setId(row.getString("id"));
                    notification.setMessage(row.getString("message"));
                    notification.setRead(row.getBoolean("read"));
                    notification.setType(row.getString("type"));
                    notifications.add(notification);
                }
                adapter = new Adaptador_Notifications(getActivity(), notifications);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        listviewNotification.setAdapter(adapter);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error Dialog Error", error.toString());
    }
}
