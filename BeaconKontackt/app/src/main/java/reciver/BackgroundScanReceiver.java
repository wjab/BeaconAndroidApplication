package reciver;

import android.content.Context;

import broudcast.AbstractBroadcastInterceptor;
import broudcast.NotificationBroadcastInterceptor;


public final class BackgroundScanReceiver extends AbstractScanBroadcastReceiver {

    @Override
    protected AbstractBroadcastInterceptor createBroadcastHandler(Context context) {
        return new NotificationBroadcastInterceptor(context);
    }
}
