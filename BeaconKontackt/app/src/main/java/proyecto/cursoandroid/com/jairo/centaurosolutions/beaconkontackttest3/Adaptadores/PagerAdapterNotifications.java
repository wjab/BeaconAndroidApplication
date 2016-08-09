package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabNotificationsAllFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabNotificationsNewFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabPointsObtenerFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabPointsRedimirFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabPointsRegalarFragment;

/**
 * Created by Centauro on 26/07/2016.
 */
public class PagerAdapterNotifications extends FragmentStatePagerAdapter {
    public PagerAdapterNotifications(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new TabNotificationsNewFragment();
                break;
            case 1:
                frag=new TabNotificationsAllFragment();
                break;

        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="Nuevas";
                break;
            case 1:
                title="Todas";
                break;

        }

        return title;
    }
}
