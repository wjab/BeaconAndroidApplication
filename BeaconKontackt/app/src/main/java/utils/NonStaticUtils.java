package utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import model.cache.BeaconCache;
import service.BeaconSyncMessageService;
import service.GivePointToUserService;

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

    public SharedPreferences loadLoginInfo(Context context){

        SharedPreferences prefs =  null;

        try{

            prefs =  context.getSharedPreferences("SQ_UserLogin", MODE_PRIVATE);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }


        return prefs;

    }

    public void saveLogin(Context context,String username,String password, String userId, int points, boolean isAuth/*, String loginType*/){

        SharedPreferences prefs = null;
        SharedPreferences.Editor editor = null;

        prefs = context.getSharedPreferences("SQ_UserLogin", MODE_PRIVATE);
        editor = prefs.edit();
        editor.putString("userId", userId);
        editor.putString("username", username);
        editor.putString("password",Utils.setEncryptedText(password));
        editor.putInt("points",points);
        editor.putBoolean("isAuthenticated", isAuth);
        //editor.putString("loginType", loginType);
        editor.commit();
    }

    public void StartGiftpointService(Context context, String promoId)
    {
        try
        {
            Intent intent = new Intent(context, GivePointToUserService.class);
            if(!promoId.isEmpty() && promoId != null)
            {
                intent.putExtra("promoId", promoId);
                context.startService(intent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            ex.getMessage();
        }

    }



}
