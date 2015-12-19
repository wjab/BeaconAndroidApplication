package utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import model.cache.BeaconCache;
import service.BeaconSyncMessageService;

/**
 * Created by Administrador on 12/18/2015.
 */
public class NonStaticUtils extends Activity {

    public void StartPromoService(Context context, BeaconCache beaconCache)
    {
        try
        {
            Intent intent = new Intent(context, BeaconSyncMessageService.class);
            intent.putExtra("beaconCacheRef", beaconCache);
            context.startService(intent);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            ex.getMessage();
        }

    }
}
