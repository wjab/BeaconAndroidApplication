package broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.kontakt.sdk.android.common.Proximity;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.BackgroundScanActivity;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.LoginMainActivity;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;
import utils.CustomNotificationManager;
import utils.Utils;

public class NotificationBroadcastInterceptor extends AbstractBroadcastInterceptor {

    private final NotificationManager notificationManager;

    public NotificationBroadcastInterceptor(Context context) {
        super(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onBeaconAppeared(int info, IBeaconDevice beaconDevice) {
        final Context context = getContext();

        final String deviceName = beaconDevice.getName();
        final String proximityUUID = beaconDevice.getProximityUUID().toString();
        final int major = beaconDevice.getMajor();
        final int minor = beaconDevice.getMinor();
        final double distance = beaconDevice.getDistance();
        final Proximity proximity = beaconDevice.getProximity();

        Intent redirectIntent = new Intent(context, BackgroundScanActivity.class);
        redirectIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        CustomNotificationManager cNotificationManager = new CustomNotificationManager();
        cNotificationManager.setContentTitle(context.getString(R.string.beacon_appeared, beaconDevice.getName()));
        cNotificationManager.setIcon(R.drawable.logo);
        cNotificationManager.setTicker(context.getString(R.string.beacon_appeared, beaconDevice.getName()));
        cNotificationManager.setnotificationMessage(context.getString(R.string.appeared_beacon_info,
                beaconDevice.getName(), beaconDevice.getUniqueId(), beaconDevice.getMajor(),
                beaconDevice.getMinor(), beaconDevice.getDistance(), beaconDevice.getProximity().name()));
        cNotificationManager.setRedirectIntent(redirectIntent);
        cNotificationManager.ShowInputNotification(context, info, notificationManager);

    }

    @Override
    protected void onRegionAbandoned(int info, IBeaconRegion region) {
        final Context context = getContext();
        final Intent redirectIntent = new Intent(context, BackgroundScanActivity.class);
        redirectIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final String regionName = region.getName();

        CustomNotificationManager cNotificationManager = new CustomNotificationManager();
        cNotificationManager.setContentTitle(context.getString(R.string.app_name));
        cNotificationManager.setIcon(R.drawable.region);
        cNotificationManager.setTicker(context.getString(R.string.region_abandoned, region.getName()));
        cNotificationManager.setnotificationMessage(context.getString(R.string.region_abandoned, regionName));
        cNotificationManager.setRedirectIntent(redirectIntent);
        cNotificationManager.ShowInputNotification(context, info, notificationManager);
    }

    @Override
    protected void onRegionEntered(int info, IBeaconRegion region) {
        final Context context = getContext();
        final Intent redirectIntent = new Intent(context, BackgroundScanActivity.class);
        redirectIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        CustomNotificationManager cNotificationManager = new CustomNotificationManager();
        cNotificationManager.setContentTitle(context.getString(R.string.app_name));
        cNotificationManager.setIcon(R.drawable.region);
        cNotificationManager.setTicker(context.getString(R.string.region_entered, region.getName()));
        cNotificationManager.setnotificationMessage(context.getString(R.string.region_entered, region.getName()));
        cNotificationManager.setRedirectIntent(redirectIntent);
        cNotificationManager.ShowInputNotification(context, info, notificationManager);
    }

    @Override
    protected void onScanStarted(int info) {
        final Context context = getContext();
        final Intent redirectIntent = new Intent(context, BackgroundScanActivity.class);

        CustomNotificationManager cNotificationManager = new CustomNotificationManager();
        cNotificationManager.setContentTitle(context.getString(R.string.scan_started));
        cNotificationManager.setIcon(R.drawable.logo);
        cNotificationManager.setTicker(context.getString(R.string.scan_started));
        cNotificationManager.setRedirectIntent(redirectIntent);
        cNotificationManager.ShowInputNotification(context, info, notificationManager);

    }

    @Override
    protected void onScanStopped(int info) {
        final Context context = getContext();
        final Intent redirectIntent = new Intent(context, BackgroundScanActivity.class);
        redirectIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        CustomNotificationManager cNotificationManager = new CustomNotificationManager();
        cNotificationManager.setContentTitle(context.getString(R.string.scan_stopped));
        cNotificationManager.setIcon(R.drawable.logo);
        cNotificationManager.setTicker(context.getString(R.string.scan_stopped));
        cNotificationManager.setRedirectIntent(redirectIntent);
        cNotificationManager.ShowInputNotification(context, info, notificationManager);

    }
}
