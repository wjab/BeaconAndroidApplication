package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabPointsObtenerFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabPointsRedimirFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabPointsRegalarFragment;

/**
 * Created by Centauro on 26/07/2016.
 */
public class PagerAdapterPoints extends FragmentStatePagerAdapter {
    public PagerAdapterPoints(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new TabPointsRedimirFragment();
                break;
            case 1:
                frag=new TabPointsRegalarFragment();
                break;
            case 2:
                frag=new TabPointsObtenerFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="Redimir";
                break;
            case 1:
                title="Regalar";
                break;
            case 2:
                title="Obtener";
                break;
        }

        return title;
    }
}
