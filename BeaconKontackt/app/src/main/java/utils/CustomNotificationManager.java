package utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;

import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

/**
 * Created by Administrador on 12/8/2015.
 */
public class CustomNotificationManager
{
    private String ticker;
    private String contentTitle;
    private String notificationMessage;
    private int icon;
    private Intent redirectIntent;

    public CustomNotificationManager()
    {
        ticker = "";
        contentTitle = "";
        icon = 0;
        redirectIntent = new Intent();
    }

    //public String getTicker(){return ticker;}
    public void setTicker(String ticker){this.ticker = ticker;}

    //public String getContentTitle(){return contentTitle;}
    public void setContentTitle(String contentTitle){this.contentTitle = contentTitle;}

    //public int getIcon(){return icon;}
    public void setIcon(int icon){this.icon = icon;}

    //public Intent getRedirectIntent(){return redirectIntent;}
    public void setRedirectIntent(Intent intent){this.redirectIntent = intent;}

    //public String getnotificationMessage(){return notificationMessage;}
    public void setnotificationMessage(String notificationMessage){this.notificationMessage = notificationMessage;}


    public void ShowInputNotification( Context context, int info, NotificationManager notificationManager )
    {
        android.app.Notification notification = new android.app.Notification.Builder(context)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setTicker(ticker)/*context.getString(R.string.beacon_appeared, beaconDevice.getName())*/
                .setContentIntent(PendingIntent.getActivity(context,
            0,
            redirectIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT))
                .setContentTitle(contentTitle)/*context.getString(R.string.beacon_appeared, beaconDevice.getName())*/
                .setSmallIcon(icon)/*R.drawable.beacon*/
                .setStyle(new android.app.Notification.BigTextStyle().bigText(notificationMessage))
                .setContentText(notificationMessage)
                .build();

        notificationManager.notify(info, notification);
    }
}
