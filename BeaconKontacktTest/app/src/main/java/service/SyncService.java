package service;

import android.app.IntentService;
import android.content.Intent;

import com.kontakt.sdk.android.common.model.Config;
import com.kontakt.sdk.android.common.util.SDKPreconditions;
import com.kontakt.sdk.android.http.KontaktApiClient;
import com.kontakt.sdk.android.http.exception.ClientException;

public class SyncService extends IntentService {

    public static final String EXTRA_REQUEST_CODE = "extra_request_code";

    public static final String EXTRA_ITEM = "extra_item";

    public static final int REQUEST_SYNC_CONFIG = 1;

    public static final String TAG = SyncService.class.getSimpleName();

    private KontaktApiClient kontaktApiClient;

    public SyncService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        kontaktApiClient = new KontaktApiClient();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SDKPreconditions.checkState(intent.hasExtra(EXTRA_REQUEST_CODE), "Missing operation request code.");

        final int request = intent.getIntExtra(EXTRA_REQUEST_CODE, Integer.MIN_VALUE);

        switch (request) {
            case REQUEST_SYNC_CONFIG:
                Config config = intent.getParcelableExtra(EXTRA_ITEM);
                SDKPreconditions.checkNotNull(config, "Config is null");
                applyConfig(config);
                break;

            default:
                throw new IllegalArgumentException("Unsupported request code: " + request);
        }
    }

    private void applyConfig(Config config) {
        try {
            final int httpStatus = kontaktApiClient.applyConfig(config);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        kontaktApiClient.close();
        kontaktApiClient = null;
    }
}
