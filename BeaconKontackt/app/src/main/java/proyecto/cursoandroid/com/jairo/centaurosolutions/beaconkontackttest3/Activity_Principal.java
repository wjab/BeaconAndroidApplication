package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

public class Activity_Principal extends TabActivity implements TabHost.OnTabChangeListener {
    TabHost tabHost;
    TextView totalPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__principal);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
