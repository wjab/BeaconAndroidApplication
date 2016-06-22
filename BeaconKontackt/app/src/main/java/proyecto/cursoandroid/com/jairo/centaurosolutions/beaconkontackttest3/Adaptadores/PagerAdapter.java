package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.PromoFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.ProductsFragment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.ShopFragment;

/**
 * Created by Centauro on 15/06/2016.
 */
public class PagerAdapter  extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new PromoFragment();
                break;
            case 1:
                frag=new ShopFragment();
                break;
            case 2:
                frag=new ProductsFragment();
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
                title="Promociones";
                break;
            case 1:
                title="Tiendas";
                break;
            case 2:
                title="Products";
                break;
        }

        return title;
    }
}
