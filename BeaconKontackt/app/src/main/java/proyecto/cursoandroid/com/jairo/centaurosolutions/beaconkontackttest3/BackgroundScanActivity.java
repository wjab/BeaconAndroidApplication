package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kontakt.sdk.android.common.log.Logger;
import com.kontakt.sdk.android.common.util.SDKPreconditions;

import java.util.ArrayList;

import adapter.menu.MenuAdapter;
import broadcast.AbstractBroadcastInterceptor;
import broadcast.ForegroundBroadcastInterceptor;
import butterknife.InjectView;
import model.elementMenu.ElementMenu;
import receiver.AbstractScanBroadcastReceiver;
import service.BackgroundScanService;
import utils.Utils;

public class BackgroundScanActivity extends BaseActivity {

    public static final String TAG = BackgroundScanActivity.class.getSimpleName();

    public static final int MESSAGE_START_SCAN = 16;
    public static final int MESSAGE_STOP_SCAN = 25;

    private static final IntentFilter SCAN_INTENT_FILTER;




    private ArrayList<ElementMenu> mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    static {
        SCAN_INTENT_FILTER = new IntentFilter(BackgroundScanService.BROADCAST);
        SCAN_INTENT_FILTER.setPriority(2);
    }

    private ServiceConnection serviceConnection;

    private Messenger serviceMessenger;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private final BroadcastReceiver scanReceiver = new ForegrondScanReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.background_scan_activity);
      //  ButterKnife.inject(this);
        mTitle = "test";

        mPlanetTitles = new ArrayList<ElementMenu>();
        mPlanetTitles.add(new ElementMenu(R.drawable.pass,"One"));
        mPlanetTitles.add(new ElementMenu(R.drawable.pass,"two"));
        mPlanetTitles.add(new ElementMenu(R.drawable.log_out,"Log Out"));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new MenuAdapter(this, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.user,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                //  getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                //    getActionBar().setTitle(mTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
      //  setUpActionBar(toolbar);
       // setUpActionBarTitle(getString(R.string.foreground_background_scan));

        serviceConnection = createServiceConnection();
        mTitle = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getString("username", "");
        setTitle(mTitle);
        bindServiceAndStartMonitoring();
    }


    public void logOut(){
        
        SharedPreferences prefs;
        SharedPreferences.Editor editor;
        prefs = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE);
        editor = prefs.edit();
        editor.putString("userId", null);
        editor.putString("username", null);
        editor.putString("password",null);
        editor.putInt("points",0);
        editor.putBoolean("isAuthenticated", false);
        editor.commit();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__principal, menu);
        return true;
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
        Toast.makeText(this, mTitle, Toast.LENGTH_SHORT).show();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles.get(position).elemento);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getString("username","");
        getSupportActionBar().setTitle(mTitle);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
