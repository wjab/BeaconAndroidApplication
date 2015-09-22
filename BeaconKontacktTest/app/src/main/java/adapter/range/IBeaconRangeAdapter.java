package adapter.range;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kontakt.sdk.android.ble.device.BeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.http.KontaktApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import adapter.viewholder.IBeaconItemViewHolder;
import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

public class IBeaconRangeAdapter extends BaseRangeAdapter<IBeaconDevice> implements Response.Listener<JSONObject>, Response.ErrorListener{


    JSONObject myObject;
    Map<String, String> params;
    private static final String TAG = "BeaconConfig";

    private static final String host = "http://api.kontakt.io";

    public IBeaconRangeAdapter(final Context context) {
        super(context);
    }

    @Override
    public IBeaconDevice getItem(int position) {
        return devices.get(position);
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = getTheSameOrInflate(convertView, parent);
        final IBeaconItemViewHolder viewHolder = (IBeaconItemViewHolder) convertView.getTag();

        final BeaconDevice beacon = (BeaconDevice) getItem(position);

        viewHolder.nameTextView.setText(String.format("%s: %s(%s)", beacon.getName(),
                beacon.getAddress(),
                new DecimalFormat("#.##").format(beacon.getDistance())));
        viewHolder.majorTextView.setText(String.format("Major : %d", beacon.getMajor()));
        viewHolder.minorTextView.setText(String.format("Minor : %d", beacon.getMinor()));
        viewHolder.rssiTextView.setText(String.format("Rssi : %f", beacon.getRssi()));
        viewHolder.txPowerTextView.setText(String.format("Tx Power : %d", beacon.getTxPower()));
        viewHolder.proximityTextView.setText(String.format("Proximity: %s", beacon.getProximity().name()));
        viewHolder.firmwareVersionTextView.setText(String.format("Firmware: %d", beacon.getFirmwareVersion()));
        viewHolder.beaconUniqueIdTextView.setText(String.format("Beacon Unique Id: %s", beacon.getUniqueId()));
        viewHolder.proximityUUIDTextView.setText(String.format("Proximity UUID: %s", beacon.getProximityUUID().toString()));

        getAPIResponse(beacon.getProximityUUID().toString());



        return convertView;
    }

    public void getAPIResponse(String UUID){

        
      ServiceController serviceController = new ServiceController();

        params = new HashMap<String, String>();
/*
        params.put("id","f7826da6-4fa2-4e98-8024-bc5b71e0893e");
        params.put("actionType", "BROWSER");
        params.put("device", null);
        params.put("proximity","NEAR");
        params.put("contentLength","");
        params.put("contentType","");
        params.put("contentType","");
        params.put("url","");
        params.put("file","");*/

        /*Ejemplo de uso con header custom*/
        Map<String,String>header = new HashMap<String, String>();

        header.put("Accept", "application/vnd.com.kontakt+json; version=6");
        header.put("Api-Key", "ZtLtzUwyFjUFGlwjSxHoKsDKmyqjXNLc");
        serviceController.jsonObjectRequest("https://api.kontakt.io/device?deviceType=beacon", Request.Method.GET, null, header, this, this);


    }

    @Override
    public void replaceWith(List<IBeaconDevice> devices) {
        this.devices.clear();
        this.devices.addAll(devices);
        notifyDataSetChanged();
    }

    @Override
    View inflate(ViewGroup parent) {
        View view = inflater.inflate(R.layout.beacon_list_row, parent, false);
        IBeaconItemViewHolder viewHolder = new IBeaconItemViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Response", error.toString());
        // vista.setText(response.toString());
    }

    @Override
    public void onResponse(JSONObject response) {


        Log.d("Response", response.toString());
        // vista.setText(response.toString());

        JSONArray jsonArray;
        JSONObject jsonObject;


    }
}
