package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterDepartments;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Department;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Store;
import utils.NonStaticUtils;

public class DetailShopActivity extends AppCompatActivity {
    Intent intent;
    String mpoints, userAcumulatedPoints;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    String idUser, merchantId;
    GridView grid;
    CustomAdapterDepartments adapter;
    private ArrayList<Department> arrayDepartment, ranges;
    TextView pointsAction, descriptionMerchant, nameMerchant, scan, purchase, walkin, scanDetails, purchaseDetails, walkingDetails;
    ImageView imageStore, openHistoryPoints, imageStoreName, purchaseImage, scanImage, walkinImage;
    Button addImage;
    LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shop);

        /* Obtiene de las preferencias compartidas, la cantidad de los puntos*/
        nonStaticUtils = new NonStaticUtils();

        preferences = nonStaticUtils.loadLoginInfo(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mpoints = String.valueOf(preferences.getInt("points", 0));
        ServiceController imageRequest = new ServiceController();
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        idUser = preferences.getString("userId", "");

        intent = getIntent();
        final Store store = (Store) intent.getSerializableExtra("detailShop");
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_promodetail, null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        openHistoryPoints = (ImageView) actionBarLayout.findViewById(R.id.openHistoryPoints);
        back = (LinearLayout) actionBarLayout.findViewById(R.id.back);
        addImage = (Button) actionBarLayout.findViewById(R.id.buttonAdd);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WishListActivity.class);
                intent.putExtra("idUser", idUser);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("code",1);
                setResult(2,intent);
                finish();
            }
        });

        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        descriptionMerchant = (TextView) findViewById(R.id.detailMerchantStoreDetail);
        nameMerchant = (TextView) findViewById(R.id.merchantNameStoreDetail);
        scan = (TextView) findViewById(R.id.scanDetail);
        purchase = (TextView) findViewById(R.id.purchaseDetail);
        walkin = (TextView) findViewById(R.id.walkinDetail);
        scanDetails = (TextView) findViewById(R.id.scanDetailText);
        purchaseDetails = (TextView) findViewById(R.id.purchaseDetailText);
        walkingDetails = (TextView) findViewById(R.id.walkinDetailText);

        imageStoreName = (ImageView) findViewById(R.id.imageShopDetail);
        walkinImage = (ImageView) findViewById(R.id.imageView3);
        scanImage = (ImageView) findViewById(R.id.imageView4);
        purchaseImage = (ImageView) findViewById(R.id.imageView2);

        imageRequest.imageRequest(store.getUrlImagen(), imageStoreName, 0, 0);
        imageStore = (ImageView) findViewById(R.id.imagenDetail);
        imageRequest.imageRequest(store.getUrlImagen(), imageStore, 0, 0);
        pointsAction.setText(userAcumulatedPoints.toString());
        nameMerchant.setText(store.getCity());
        descriptionMerchant.setText(store.getAddress());
        ranges = store.getDepartments();
        merchantId = store.getId();
        grid = (GridView) findViewById(R.id.departments);
        addImage.setText(String.valueOf(BackgroundScanActivity.size));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Department department = new Department();
                department = ranges.get(position);
                Intent intentSuccess = new Intent(getBaseContext(), ProductsDepartmentActivity.class);
                intentSuccess.putExtra("department", department);
                intentSuccess.putExtra("merchantId", merchantId);
                intentSuccess.putExtra("code", 1);
                startActivityForResult(intentSuccess, 2);

            }
        });

        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mpoints.equals("0")) {
                    Toast.makeText(getApplication(), getString(R.string.dontHavePoints), Toast.LENGTH_SHORT).show();
                } else {
                    openHistory();
                }
            }
        });

        openHistoryPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mpoints.equals("0")) {
                    Toast.makeText(getApplication(), getString(R.string.dontHavePoints), Toast.LENGTH_SHORT).show();
                } else {
                    openHistory();
                }
            }
        });

        setImgeTextColor(store);
        chargeDepartments();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent();
        intent.putExtra("code",1);
        setResult(2,intent);
        finish();
        super.onBackPressed();
    }
    public void openHistory() {
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        int code = intent.getIntExtra("code", 0);
        if (code == 1) {
            super.onActivityResult(requestCode, resultCode, intent);
            addImage.setText(String.valueOf(BackgroundScanActivity.size));
        }
    }
    public void chargeDepartments() {

        adapter = new CustomAdapterDepartments(this, ranges);
        grid.setAdapter(adapter);
    }

    public void setImgeTextColor(Store store) {
        String walkinText, purchaseText, scanText;
        walkinText = store.getTotalGiftPoints().getWalkin();
        scanText = store.getTotalGiftPoints().getScan();
        purchaseText = store.getTotalGiftPoints().getPurchase();
        scan.setText(scanText);
        if (scanText.equals("0")) {
            scanDetails.setTextColor(getBaseContext().getResources().getColor(R.color.mediumGrey));
            scan.setTextColor(getBaseContext().getResources().getColor(R.color.mediumGrey));
            scanImage.setBackground(getBaseContext().getResources().getDrawable(R.drawable.scan_gray));
        } else {
            scanDetails.setTextColor(getBaseContext().getResources().getColor(R.color.darkBlue));
            scan.setTextColor(getBaseContext().getResources().getColor(R.color.darkBlue));
            scanImage.setBackground(getBaseContext().getResources().getDrawable(R.drawable.scan_blue));
        }
        walkin.setText(walkinText);
        if (walkinText.equals("0")) {
            walkingDetails.setTextColor(getBaseContext().getResources().getColor(R.color.mediumGrey));
            walkin.setTextColor(getBaseContext().getResources().getColor(R.color.mediumGrey));
            walkinImage.setBackground(getBaseContext().getResources().getDrawable(R.drawable.walk_in_gray));
        } else {
            walkingDetails.setTextColor(getBaseContext().getResources().getColor(R.color.darkBlue));
            walkin.setTextColor(getBaseContext().getResources().getColor(R.color.darkBlue));
            walkinImage.setBackground(getBaseContext().getResources().getDrawable(R.drawable.walk_in_blue));
        }
        purchase.setText(purchaseText);
        if (purchaseText.equals("0")) {

            purchaseDetails.setTextColor(getBaseContext().getResources().getColor(R.color.mediumGrey));
            purchase.setTextColor(getBaseContext().getResources().getColor(R.color.mediumGrey));
            purchaseImage.setBackground(getBaseContext().getResources().getDrawable(R.drawable.purchase_gray));
        } else {

            purchaseDetails.setTextColor(getBaseContext().getResources().getColor(R.color.darkBlue));
            purchase.setTextColor(getBaseContext().getResources().getColor(R.color.darkBlue));
            purchaseImage.setBackground(getBaseContext().getResources().getDrawable(R.drawable.purchase_blue));
        }

    }
}
