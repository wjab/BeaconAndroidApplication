package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import utils.NonStaticUtils;

public class RedimirRegalarActivity extends AppCompatActivity {
    private String type, messageToSend;
    private TextView message, code, expiration;
    private Button sendData;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    LinearLayout back;
    private CharSequence mpoints;
    private Date dateExpiration;
    Button addImage;
    private String idUser, userAcumulatedPoints, date;
    public static final Locale COSTARICA = new Locale("es", "CR");

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redimir_regalar);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        date = intent.getStringExtra("expiration");
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar_promodetail,
                null);
        message = (TextView) findViewById(R.id.messageToShow);
        code = (TextView) findViewById(R.id.code);
        expiration = (TextView) findViewById(R.id.expiration);
        sendData = (Button) findViewById(R.id.button);

        long unixSeconds = Long.parseLong(date);
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d-MMM-yy K:mm a");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);

        expiration.setText(getString(R.string.expiracionPuntos) + formattedDate);
        if (type.equals("redimir")) {
            sendData.setVisibility(View.GONE);
            message.setText(getString(R.string.redimirPuntos));
            code.setText(intent.getStringExtra("code"));
        } else {
            message.setText(getString(R.string.regalarPuntos));
            sendData.setText(getString(R.string.sendMessageButton));
            messageToSend = intent.getStringExtra("message");
            code.setText(intent.getStringExtra("code"));
            sendData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendPoints();
                }
            });

        }

        mpoints = getSharedPreferences("SQ_UserLogin", MODE_PRIVATE).getInt("points", 0) + "";
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
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
                intent.putExtra("code", 1);
                startActivityForResult(intent, 2);
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
                if (mpoints.toString().equals("0")) {
                    Toast.makeText(getApplication(), getString(R.string.dontHavePoints), Toast.LENGTH_SHORT).show();
                } else {
                    openHistory();
                }
            }
        });
        addImage.setText(String.valueOf(BackgroundScanActivity.size));

    }

    public void openHistory() {
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser", idUser);
        intent.putExtra("code", 1);
        startActivityForResult(intent, 2);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        int code = intent.getIntExtra("code", 0);
        if (code == 1) {
            super.onActivityResult(requestCode, resultCode, intent);
            addImage.setText(String.valueOf(BackgroundScanActivity.size));
        }
    }
    private void sendPoints() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messageToSend + " este es el código: " + code.getText().toString());

        startActivity(Intent.createChooser(intent, getString(R.string.sendCode)));
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this.getBaseContext(), PointsActivity.class);
        startActivity(intent);
        return true;
    }

}
