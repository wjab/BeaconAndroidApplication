package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;


import com.kontakt.sdk.android.common.model.Config;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.BaseActivity;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.R;

public class ConfigFormActivity extends BaseActivity {

    public static final String EXTRA_RESULT_CONFIG = "extra_result_config";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.generate_button)
    Button generateProximityUUIDButton;

    @InjectView(R.id.submit_button)
    Button submitButton;

    @InjectView(R.id.proximity_uuid_text)
    EditText proximityUUIDText;

    @InjectView(R.id.major_text)
    EditText majorText;

    @InjectView(R.id.minor_text)
    EditText minorText;

    @InjectView(R.id.power_level_text)
    EditText powerLevelText;

    @InjectView(R.id.advertising_interval_text)
    EditText advertisingIntervalText;

    @InjectView(R.id.beacon_unique_id_text)
    EditText beaconUniqueIdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_form_activity);
       // ButterKnife.inject(this);
        //setUpActionBar(toolbar);
        //(getString(R.string.configuration));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ButterKnife.reset(this);
    }

    @OnClick(R.id.generate_button)
    void onGenerateRandomProximityUUID() {
        proximityUUIDText.setText(UUID.randomUUID().toString());
    }

    @OnClick(R.id.submit_button)
    void onSubmit() {
        Config config = createConfig();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_RESULT_CONFIG, config);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private Config createConfig() {
        return new Config.Builder()
                     .setProximityUUID(UUID.fromString(proximityUUIDText.getText().toString().trim()))
                     .setMajor(Integer.parseInt(majorText.getText().toString()))
                     .setMinor(Integer.parseInt(minorText.getText().toString()))
                     .setTxPower(Integer.parseInt(powerLevelText.getText().toString()))
                     .setDeviceUniqueId(beaconUniqueIdText.getText().toString().trim())
                     .setInterval(Integer.parseInt(advertisingIntervalText.getText().toString()))
                     .build();
    }
}
