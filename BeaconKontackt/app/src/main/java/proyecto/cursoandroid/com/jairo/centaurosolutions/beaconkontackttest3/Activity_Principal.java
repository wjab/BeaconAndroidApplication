package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.menu.MenuAdapter;
import model.cache.BeaconCache;
import model.elementMenu.ElementMenu;
import service.BackgroundScanService;

public class Activity_Principal extends TabActivity implements TabHost.OnTabChangeListener {
    TabHost tabHost;
    TextView totalPoints;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ArrayList<ElementMenu> elementosMenu;
    MenuAdapter adaptador;
    String[] tagTitles;
    String itemTitle;
    ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity__principal);




        Intent intentService = new Intent(this, BackgroundScanService.class);
        startService(intentService);

        totalPoints = (TextView)findViewById(R.id.totalPoints);
        Intent intent2 = getIntent();
        totalPoints.setText("Puntos canjeables: "+String.valueOf(intent2.getIntExtra("totalPoints",0)));

        // Get TabHost Refference
        tabHost = getTabHost();

        // Set TabChangeListener called when tab changed
        tabHost.setOnTabChangedListener(this);
        TabHost.TabSpec spec;
        Intent intent;

        /************* TAB1 ************/
        // Create  Intents to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, TabPromocionesActivity.class);
        spec = tabHost.newTabSpec("First").setIndicator("Promociones")
                .setContent(intent);

        //Add intent to tab
        tabHost.addTab(spec);

        /************* TAB2 ************/
        intent = new Intent().setClass(this, TabPromocionesActivity.class);
        spec = tabHost.newTabSpec("Second").setIndicator("Beacon Store")
                .setContent(intent);
        tabHost.addTab(spec);

        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.color.selectedTab);
        tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.color.unselectedTab);

        tabHost.getTabWidget().setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.color.selectedTab);


        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setAllCaps(false);
            tv.setTextColor(Color.WHITE);
        }
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setAllCaps(false);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(15);

        }

        // Set drawable images to tab
        // tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab2);
        //tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab3);

        // Set Tab1 as Default tab and change image
        tabHost.getTabWidget().setCurrentTab(0);
    }
    /* La escucha del ListView en el Drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            itemTitle=elementosMenu.get(position).getElemento();
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincronizar el estado del drawer
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Cambiar las configuraciones del drawer si hubo modificaciones
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            // Toma los eventos de selección del toggle aquí
            return true;
        }



        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTabChanged(String tabId) {



        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            if(i==0)
                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.unselectedTab);
            else if(i==1)
                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.unselectedTab);

        }




        if(tabHost.getCurrentTab()==0)
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.color.selectedTab);
        else if(tabHost.getCurrentTab()==1)
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.color.selectedTab);
        else if(tabHost.getCurrentTab()==2)
            tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.color.selectedTab);

    }
}
