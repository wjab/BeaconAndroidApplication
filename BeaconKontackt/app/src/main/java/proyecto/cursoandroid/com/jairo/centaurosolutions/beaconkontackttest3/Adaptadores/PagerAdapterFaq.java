package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.TabPerfilFragment;

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
                frag=new TabPerfilFragment();
                break;
            case 2:
                frag=new TabPerfilFragment();
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
                title="Perfil";
                break;
            case 1:
                title="Deseos";
                break;
            case 2:
                title="Cerrar Sesion";
                break;
        }

        return title;
    }
}
