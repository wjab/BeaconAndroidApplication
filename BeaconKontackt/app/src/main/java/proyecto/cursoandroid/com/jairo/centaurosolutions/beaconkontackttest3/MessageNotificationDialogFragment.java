package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterPreference;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Preference;


/**
 * Created by Centauro on 28/06/2016.
 */
public class MessageNotificationDialogFragment extends DialogFragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    public MessageNotificationDialogFragment() {
    }
    public CustomAdapterPreference adapter;
    private static TextView notification;
    private static ArrayList<Preference> listArray;
    Dialog dialog;

    private static String idNotification;
    private static String message;
    private static String webServiceUser;
    private static Activity context;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        // Get the layout inflater
        dialog.setTitle("Notificación");
        dialog.setContentView(R.layout.notification_dialog);
        notification = (TextView) dialog.findViewById(R.id.notificarion_message);

        Bundle mArgs = getArguments();
        idNotification = mArgs.getString("id");
        message = mArgs.getString("message");
        notification.setText(message);
        webServiceUser = getString(R.string.WebService_Utils);
        context=getActivity();
        service();
        return dialog;
    }


    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;

    public void service(){
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> nullMap = new HashMap<String, String>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = webServiceUser+"/notification/"+idNotification;
        serviceController.jsonObjectRequest(url, Request.Method.PUT, null, map, response, responseError);

    }



    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
