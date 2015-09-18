package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    protected void setUpActionBar(final Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected void setUpActionBarTitle(final String title) {
        getSupportActionBar().setTitle(title);
    }

}
