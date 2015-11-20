package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;

import task.BeaconMessageSyncTask;

public class BeaconSyncMessageService extends Service  {



    public static final long SYNC_INTERVAL = 5 * 1000;

    private Timer mTimer = null;

    public BeaconSyncMessageService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("BSMS", "Service onCreate");

        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new BeaconMessageSyncTask(), 0, SYNC_INTERVAL);
        Log.i("BSMS", "Task Started");
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
