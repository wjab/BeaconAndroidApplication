package utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Surface;
import android.widget.Toast;

import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.cache.BeaconCache;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;


public final class Utils {

    public static void cancelNotifications(final Context context, final List<Integer> notificationIdList) {
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        for (final int notificationId : notificationIdList) {
            notificationManager.cancel(notificationId);
        }
    }

    public static String setEncryptedText(String text){

        MessageDigest md;
        StringBuffer sb = new StringBuffer();

        try {
            md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte[] digest = md.digest();

            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sb.toString();

    }

    public static Date convertLongToDate(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        return date;
    }


    public static double getDaysDiff(Date date1, Date date2){

        NumberFormat daysFormat = new DecimalFormat("#0.00");

        long diff = date1.getTime() - date2.getTime();


        double diffDays = (double) (diff / (24 * 60 * 60 * 1000));
        double result = Double.valueOf((String.valueOf(daysFormat.format(diffDays)).replace(',', '.').toString()));

        return result;
    }

    public static void setOrientationChangeEnabled(final boolean state, final Activity activity) {

        if (!state) {
            int orientation = 0;
            int tempOrientation = activity.getResources().getConfiguration().orientation;
            final int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            switch (tempOrientation) {
                case Configuration.ORIENTATION_LANDSCAPE:
                    if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    } else {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    }
                    break;
                case Configuration.ORIENTATION_PORTRAIT:
                    if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_270) {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    } else {
                        orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    }
                    break;
            }
            activity.setRequestedOrientation(orientation);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    public static void showToast(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        } else if (!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        return true;
    }


    public static boolean getBluetoothState() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.isEnabled();
    }

    public static long UnixTimeStamp()
    {
        return System.currentTimeMillis() / Constants.THOUSAND;
    }

    public static long UnixTimeStampWithDefaultExpiration()
    {
        return System.currentTimeMillis() / Constants.THOUSAND + Constants.DefaultExpirationSeg;
    }

}