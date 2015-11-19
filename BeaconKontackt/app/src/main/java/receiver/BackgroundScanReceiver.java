package receiver;

import android.content.Context;

import broadcast.AbstractBroadcastInterceptor;
import broadcast.NotificationBroadcastInterceptor;


public final class BackgroundScanReceiver extends AbstractScanBroadcastReceiver {

    @Override
    protected AbstractBroadcastInterceptor createBroadcastHandler(Context context) {
        return new NotificationBroadcastInterceptor(context);
    }
}
