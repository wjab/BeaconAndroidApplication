package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.range;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kontakt.sdk.android.ble.configuration.scan.EddystoneScanContext;
import com.kontakt.sdk.android.ble.configuration.scan.IBeaconScanContext;
import com.kontakt.sdk.android.common.interfaces.SDKBiConsumer;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import adapter.range.BaseRangeAdapter;
import adapter.range.IBeaconRangeAdapter;
import controllers.ServiceController;
import dialog.PasswordDialogFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.management.BeaconManagementActivity;

public class IBeaconRangeActivity extends BaseBeaconRangeActivity implements Response.Listener<JSONObject>, Response.ErrorListener{


    JSONObject myObject;
    JSONArray myArrayt;
    Map<String, String> params;
    IBeaconDevice mybeacon;
    boolean flag;

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

        try
        {
            if(mybeacon != null)
            {
                JSONObject jsonObject;
                JSONArray jsonArray;

                jsonArray = response.getJSONArray("browserActions");

                //jsonObject = jsonArray.getJSONObject(0);

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.getJSONObject(i);

                    String proximity = jsonObject.getString("proximity");
                    //String beaconprox = mybeacon.getProximity().name();


                    if (mybeacon.getProximity().name().equals(proximity)) {
                        String url = jsonObject.getString("url");

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                }
            }
            else
            {
                Toast.makeText(this,"Message: Beacon object is null", Toast.LENGTH_LONG);
            }
            flag = false;
        }
        catch (Exception ex)
        {
            Toast.makeText(this,ex.getMessage(), Toast.LENGTH_LONG);
        }

        /*mybeacon.getName();
        Toast.makeText(this,"Message: " + mybeacon.getName(), Toast.LENGTH_LONG);*/
        Log.d("Response", response.toString());
        // vista.setText(response.toString());

        JSONArray jsonArray;
        JSONObject jsonObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void callOnListItemClick(int position) {
        final IBeaconDevice beacon = (IBeaconDevice) adapter.getItem(position);
        if (beacon != null) {
            mybeacon = beacon;
            /*PasswordDialogFragment.newInstance(getString(R.string.format_connect, beacon.getAddress()),
                    getString(R.string.password),
                    getString(R.string.connect),
                    new SDKBiConsumer<DialogInterface, String>() {
                        @Override
                        public void accept(DialogInterface dialogInterface, String password) {

                            beacon.setPassword(password.getBytes());

                            final Intent intent = new Intent(BeaconRangeSyncableActivity.this, SyncableBeaconManagementActivity.class);
                            intent.putExtra(BeaconManagementActivity.EXTRA_BEACON_DEVICE, beacon);

                            startActivityForResult(intent, REQUEST_CODE_CONNECT_TO_DEVICE);
                        }
                    }).show(getFragmentManager(), "dialog");*/
            getAPIResponse(beacon.getUniqueId());
        }
    }

    public void getAPIResponse(String beaconId)
    {
        if (!flag) {
            ServiceController serviceController = new ServiceController();

            params = new HashMap<String, String>();

        /*Ejemplo de uso con header custom*/
            Map<String, String> header = new HashMap<String, String>();

            header.put("Accept", "application/vnd.com.kontakt+json; version=6");
            header.put("Api-Key", "ZtLtzUwyFjUFGlwjSxHoKsDKmyqjXNLc");
            serviceController.jsonObjectRequest("https://api.kontakt.io/beacon/" + beaconId, Request.Method.GET, null, header, this, this);
            flag = true;
        }
    }

    @Override
    EddystoneScanContext getEddystoneScanContext() {
        return null;
    }

    @Override
    IBeaconScanContext getIBeaconScanContext() {
        return beaconScanContext;
    }

    @Override
    BaseRangeAdapter getAdapter() {
        return new IBeaconRangeAdapter(this);
    }
}