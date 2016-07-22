package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabDeseosFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabPerfilFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabSesionFragment;

/**
 * Created by Centauro on 22/07/2016.
 */
public class PagerAdapterWishList  extends FragmentStatePagerAdapter {
    public PagerAdapterWishList(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new TabPerfilFragment();
                break;
            case 1:
                frag=new TabDeseosFragment();
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
                title="Productos";
                break;
            case 1:
                title="Promociones";
                break;

        }

        return title;
    }
}
