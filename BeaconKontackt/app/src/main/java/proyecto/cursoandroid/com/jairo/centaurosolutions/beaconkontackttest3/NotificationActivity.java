package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.PagerAdapterNotifications;
import utils.NonStaticUtils;

public class NotificationActivity extends AppCompatActivity {
    ViewPager pager;
    TabLayout tabLayout;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    LinearLayout back;
    private CharSequence mpoints,mTitle;
    private String idUser,userAcumulatedPoints;
    private ServiceController serviceController;
    private Button addImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar_promodetail,
                null);
        pager= (ViewPager) findViewById(R.id.view_pager_notication);
        tabLayout= (TabLayout) findViewById(R.id.tab_layout_notification);
        FragmentManager manager=getSupportFragmentManager();
        PagerAdapterNotifications adapter=new PagerAdapterNotifications(manager);
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#a1d940"));

        mpoints = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getInt("points", 0)+"";
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel),mpoints);
        idUser = preferences.getString("userId", "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        TextView pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        addImage = (Button) actionBarLayout.findViewById(R.id.buttonAdd);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WishListActivity.class);
                intent.putExtra("idUser", idUser);
                startActivity(intent);
            }
        });
        back = (LinearLayout) actionBarLayout.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();

            }
        });
        pointsAction.setText(userAcumulatedPoints.toString());
        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpoints.toString().equals("0"))
                {
                    Toast.makeText(getApplication(), getString(R.string.dontHavePoints), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    openHistory();
                }
            }
        });

        NotificationManagerCompat notifManager= NotificationManagerCompat.from(this);
        notifManager.cancelAll();

    }
    public void openHistory(){
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser",idUser);
        startActivity(intent);
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this.getBaseContext(), BackgroundScanActivity.class);
        startActivity(intent);
        return true;
    }

}
