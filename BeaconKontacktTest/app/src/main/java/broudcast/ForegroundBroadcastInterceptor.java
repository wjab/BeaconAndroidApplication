package broudcast;


import android.content.Context;

import com.kontakt.sdk.android.common.Proximity;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;
import utils.Utils;

public class ForegroundBroadcastInterceptor extends AbstractBroadcastInterceptor {
    public ForegroundBroadcastInterceptor(Context context) {
        super(context);
    }

    @Override
    protected void onBeaconAppeared(int info, IBeaconDevice beaconDevice) {

        final String deviceName = beaconDevice.getName();
        final String proximityUUID = beaconDevice.getProximityUUID().toString();
        final int major = beaconDevice.getMajor();
        final int minor = beaconDevice.getMinor();
        final double distance = beaconDevice.getDistance();
        final Proximity proximity = beaconDevice.getProximity();

        Context context = getContext();
        Utils.showToast(context, context.getString(R.string.appeared_beacon_info, deviceName,
                proximityUUID,
                major,
                minor,
                distance,
                proximity.name()));
    }

    @Override
    protected void onRegionAbandoned(int info, IBeaconRegion region) {
        Context context = getContext();

        Utils.showToast(context, context.getString(R.string.region_abandoned, region.getName()));
    }

    @Override
    protected void onRegionEntered(int info, IBeaconRegion region) {
        Context context = getContext();

        Utils.showToast(context, context.getString(R.string.region_entered, region.getName()));
    }

    @Override
    protected void onScanStarted(int info) {
        Context context = getContext();
        Utils.showToast(context, context.getString(R.string.scan_started));
    }

    @Override
    protected void onScanStopped(int info) {
        Context context = getContext();
        Utils.showToast(context, context.getString(R.string.scan_stopped));
    }
}
