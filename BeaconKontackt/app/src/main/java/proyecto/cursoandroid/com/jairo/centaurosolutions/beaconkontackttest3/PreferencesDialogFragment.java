package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
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
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterPreference;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Preference;


/**
 * Created by Centauro on 28/06/2016.
 */
public class PreferencesDialogFragment extends DialogFragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    public PreferencesDialogFragment() {
    }
    public CustomAdapterPreference adapter;
    public ListView listView;
    public ArrayList<Preference> listArray;
    Dialog dialog;
    Button btn_save;
    String idUser;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        // Get the layout inflater
        dialog.setTitle("Preferencias");
        dialog.setContentView(R.layout.layout_dialog);
        listView= (ListView)dialog.findViewById(R.id.listviewPreferences);
        Bundle mArgs = getArguments();
        idUser = mArgs.getString("idUser");
        btn_save=(Button)dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePreferences();
            }
        });
        service();
        return dialog;
    }

    public void capturePreferences(){

            Toast.makeText(getActivity().getApplicationContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();

        dialog.cancel();

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
        String url = getString(R.string.WebService_User)+"user/id/"+idUser;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }
    @Override
    public void onResponse(JSONObject response) {


        try {
            listArray = new ArrayList<Preference>();
            //Carga el objeto user solamente
            JSONObject currRange = response.getJSONObject("user");
            //Dentro del user selecciona el array de preferencias
            JSONArray ranges=  currRange.getJSONArray("preference");
            for(int i=0; i < ranges.length(); i++ ){
                JSONObject currRange1 = ranges.getJSONObject(i);

                Preference element = new Preference();
                element.setPreference(currRange1.getString("preference"));
               // element.setAnswer(currRange.getString("answer"));


                listArray.add(element);
            }

            adapter=new CustomAdapterPreference(getActivity(), listArray);
            listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error Dialog Error", error.toString());
    }
}
