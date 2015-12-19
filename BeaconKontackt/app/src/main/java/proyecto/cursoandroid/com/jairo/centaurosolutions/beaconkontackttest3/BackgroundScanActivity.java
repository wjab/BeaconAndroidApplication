package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.ActionBar;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kontakt.sdk.android.common.log.Logger;
import com.kontakt.sdk.android.common.util.SDKPreconditions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.menu.MenuAdapter;
import broadcast.AbstractBroadcastInterceptor;
import broadcast.ForegroundBroadcastInterceptor;
import butterknife.InjectView;
import controllers.ServiceController;
import model.cache.BeaconCache;
import model.elementMenu.ElementMenu;
import model.promo.Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.Adaptador_Promo;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Promociones;
import receiver.AbstractScanBroadcastReceiver;
import service.BackgroundScanService;
import utils.Utils;

public class BackgroundScanActivity extends BaseActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    public static final String TAG = BackgroundScanActivity.class.getSimpleName();

    public static final int MESSAGE_START_SCAN = 16;
    public static final int MESSAGE_STOP_SCAN = 25;

    private static final IntentFilter SCAN_INTENT_FILTER;




    private ArrayList<ElementMenu> mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mpoints;
    private CharSequence mTitle;
    public Adaptador_Promo adapter;
    public ListView llistviewPromo;
    private ActionBarDrawerToggle mDrawerToggle;
    public  ArrayList<Promociones> listPromoArray;
    static {
        SCAN_INTENT_FILTER = new IntentFilter(BackgroundScanService.BROADCAST);
        SCAN_INTENT_FILTER.setPriority(2);
    }

    private ServiceConnection serviceConnection;

    private Messenger serviceMessenger;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    private String[] titulos;
    private TypedArray NavIcons;
    private final BroadcastReceiver scanReceiver = new ForegrondScanReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.background_scan_activity);
        // Inflate your custom layout
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar_layout,
                null);
        llistviewPromo= (ListView) findViewById(R.id.listviewPromo);
        // Set up your ActionBar
        mTitle = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getString("username", "");
        mpoints = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getInt("points", 0)+"";
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        TextView pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);

        pointsAction.setText(mpoints + " pts");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_options);

        llistviewPromo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Promociones promo = new Promociones();
                promo = listPromoArray.get(position);
                Intent intentSuccess = new Intent(getApplicationContext(), Detail_Promo.class);
                intentSuccess.putExtra("Detail", promo);
                startActivity(intentSuccess);
            }
        });



        NavIcons = getResources().obtainTypedArray(R.array.navigation_iconos);
        //Tomamos listado  de titulos desde el string-array de los recursos @string/nav_options
        titulos = getResources().getStringArray(R.array.nav_options);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        mPlanetTitles= new ArrayList<ElementMenu>();
        //Principal
        mPlanetTitles.add(new ElementMenu(titulos[0], NavIcons.getResourceId(0, -1)));
        //Perfil
        mPlanetTitles.add(new ElementMenu(titulos[1], NavIcons.getResourceId(1, -1)));
        //Preferencias
        mPlanetTitles.add(new ElementMenu(titulos[2], NavIcons.getResourceId(2, -1)));
        //Lista de deseos
        mPlanetTitles.add(new ElementMenu(titulos[3], NavIcons.getResourceId(3, -1)));
        //Invitar
        mPlanetTitles.add(new ElementMenu(titulos[4], NavIcons.getResourceId(4, -1)));
        //FAQ
        mPlanetTitles.add(new ElementMenu(titulos[5], NavIcons.getResourceId(5, -1)));
        //Logout
        mPlanetTitles.add(new ElementMenu(titulos[6], NavIcons.getResourceId(6, -1)));



        // Set the adapter for the list view
        mDrawerList.setAdapter(new MenuAdapter(this, mPlanetTitles));
        //Declaramos el header el caul sera el layout de header.xml
        View header = getLayoutInflater().inflate(R.layout.header_menu, null);
        TextView text=((TextView) header.findViewById(R.id.usernameheader));
        text.setText(mTitle);
        mDrawerList.addHeaderView(header);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_deseos,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
               /* getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_options);
                setTitle("");*/
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
              /*  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
                setTitle("");*/
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

      //  setUpActionBar(toolbar);
       // setUpActionBarTitle(getString(R.string.foreground_background_scan));

        serviceConnection = createServiceConnection();

        bindServiceAndStartMonitoring();
        promoService();

    }


    public void logOut(){
        
        SharedPreferences prefs;
        SharedPreferences.Editor editor;
        prefs = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE);
        editor = prefs.edit();
        editor.putString("userId", null);
        editor.putString("username", null);
        editor.putString("password", null);
        editor.putInt("points", 0);
        editor.putBoolean("isAuthenticated", false);
        editor.commit();
        Intent intent= new Intent(getApplicationContext(),LoginMainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.cancelNotifications(this, BackgroundScanService.INFO_LIST);
        registerReceiver(scanReceiver, SCAN_INTENT_FILTER);
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            moveTaskToBack(true);
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(scanReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendMessage(Message.obtain(null, MESSAGE_STOP_SCAN));
        serviceMessenger = null;
        unbindService(serviceConnection);
        serviceConnection = null;
      //  ButterKnife.reset(this);
    }

    private void bindServiceAndStartMonitoring() {
        final Intent intent = new Intent(this, BackgroundScanService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection createServiceConnection() {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                serviceMessenger = new Messenger(service);

                sendMessage(Message.obtain(null, MESSAGE_START_SCAN));
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    private void sendMessage(final Message message) {
        SDKPreconditions.checkNotNull(serviceMessenger, "ServiceMessenger is null.");
        SDKPreconditions.checkNotNull(message, "Message is null");

        try {
            serviceMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
            Logger.d(": message not sent(" + message.toString() + ")");
        }
    }

    private static class ForegrondScanReceiver extends AbstractScanBroadcastReceiver {

        @Override
        protected AbstractBroadcastInterceptor createBroadcastHandler(Context context) {
            return new ForegroundBroadcastInterceptor(context);
        }
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        mTitle = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getString("username","");
        //Toast.makeText(this, mTitle, Toast.LENGTH_SHORT).show();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
       // setTitle(mPlanetTitles.get(position).elemento);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {

        getSupportActionBar().setTitle(title);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position-1);
            if(mPlanetTitles.get(position-1).getElemento().equals("Cerrar Session")){
                logOut();
            }
        }
    }
    @Override
    public void onResponse(JSONObject response) {


        try {
            listPromoArray = new ArrayList<Promociones>();
            Gson gson= new Gson();
            JSONArray ranges= response.getJSONArray("Promo");
            String range = "";
            String message = "";
            String messageType = "";
            String promo = "";

            for(int i=0; i < ranges.length(); i++ ){
                JSONObject currRange = ranges.getJSONObject(i);

                Promociones promoelement = new Promociones();
                promoelement.setTitulo(currRange.getString("title"));
                promoelement.setDescripcion(currRange.getString("description"));
                promoelement.setPuntos(currRange.getInt("gift_points"));promoelement.setUrlImagen(currRange.getString("images"));

                listPromoArray.add(promoelement);
            }

            adapter=new Adaptador_Promo(this, listPromoArray);
            llistviewPromo.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Login Error", error.toString());
    }
    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;


    public void promoService(){
            serviceController = new ServiceController();
            responseError = this;
            response = this;

            Map<String, String> nullMap = new HashMap<String, String>();

            Map<String, String> map = new HashMap<String, String>();
            map.put("Content-Type", "application/json");
            String url = "http://bpromodev.cfapps.io/promo/";
            serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);

    }
}
