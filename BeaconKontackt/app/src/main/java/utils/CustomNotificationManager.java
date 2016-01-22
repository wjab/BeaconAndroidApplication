package utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Administrador on 12/8/2015.
 */
public class CustomNotificationManager
{
    private String ticker;
    private String contentTitle;
    private String contentText;
    private String notificationMessage;
    private int icon;
    private Intent redirectIntent;

    public CustomNotificationManager()
    {
        ticker = "";
        contentTitle = "";
        icon = 0;
        redirectIntent = new Intent();
        contentText = "";

    }

    //public String getTicker(){return ticker;}
    public void setTicker(String ticker){this.ticker = ticker;}

    //public String getContentTitle(){return contentTitle;}
    public void setContentTitle(String contentTitle){this.contentTitle = contentTitle;}

    //public String getContentText(){return contentText;}
    public void setContentText(String contentText){this.contentText = contentText;}

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


    public void ShowInputNotification(Context context, NotificationManager mNotificationManager, Intent resultIntent, String message, TaskStackBuilder stackBuilder, int numMessages) {


   /* Invoking the default notification service */
        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(context);

        mBuilder.setContentTitle(contentTitle);
        mBuilder.setContentText(contentText);
        mBuilder.setTicker(ticker);
        mBuilder.setSmallIcon(icon);

   /* Increase notification number every time a new notification arrives */
        mBuilder.setNumber(numMessages);

   /* Add Big View Specific Configuration */
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle(contentTitle);

        // Moves events into the big view

        inboxStyle.addLine(message);
        mBuilder.setStyle(inboxStyle);

   /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

   /* notificationID allows you to update the notification later on. */
        mNotificationManager.notify(0, mBuilder.build());

    }
}
