package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabDeseosFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabPerfilFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabSesionFragment;

/**
 * Created by Centauro on 08/07/2016.
 */
public class PagerAdapterFaq  extends FragmentStatePagerAdapter {
    public PagerAdapterFaq(FragmentManager fm) {
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
            case 2:
                frag=new TabSesionFragment();
                break;
            case 3:
                frag=new TabSesionFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="Perfil";
                break;
            case 1:
                title="Deseos";
                break;
            case 2:
                title="Sesion";
                break;
            case 3:
                title="Puntos";
                break;
        }

        return title;
    }
}
