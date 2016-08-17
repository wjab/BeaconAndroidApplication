package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabPreferenciasFragment extends Fragment {

    public TabPreferenciasFragment() {
        // Required empty public constructor
    }

    public static TabPreferenciasFragment newInstance() {
        TabPreferenciasFragment fragment = new TabPreferenciasFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_preferencias, container, false);
    }


}
