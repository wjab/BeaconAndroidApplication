package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
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
    String idUser;
    GridView grid;
    CustomAdapterDepartments adapter;
    private ArrayList<Department> arryaDepartment, ranges;
    TextView pointsAction,descriptionMerchant, nameMerchant,scan,purchase,walkin;
    ImageView imageStore, openHistoryPoints,imageStoreName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shop);

        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /* Obtiene de las preferencias compartidas, la cantidad de los puntos*/
        nonStaticUtils = new NonStaticUtils();
        preferences = nonStaticUtils.loadLoginInfo(this);
        mpoints = preferences.getInt("points", 0) + "";
        ServiceController imageRequest =  new ServiceController();
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        idUser = preferences.getString("userId", "");
        intent = getIntent();
        Store store = (Store)intent.getSerializableExtra("detailShop");
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_promodetail, null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        openHistoryPoints=(ImageView) actionBarLayout.findViewById(R.id.openHistoryPoints);

        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        descriptionMerchant=(TextView)findViewById(R.id.detailMerchantStoreDetail);
        nameMerchant=(TextView)findViewById(R.id.merchantNameStoreDetail);
        scan=(TextView)findViewById(R.id.scanDetail);
        purchase=(TextView)findViewById(R.id.purchaseDetail);
        walkin=(TextView)findViewById(R.id.walkinDetail);

        imageStoreName = (ImageView) findViewById(R.id.imageShopDetail);
        imageRequest.imageRequest(store.getUrlImagen(), imageStoreName, 0, 0);
        imageStore = (ImageView) findViewById(R.id.imagenDetail);
        imageRequest.imageRequest(store.getUrlImagen(), imageStore, 0, 0);
        pointsAction.setText(userAcumulatedPoints.toString());
        nameMerchant.setText(store.getMerchantName());
        scan.setText(store.getTotalGiftPoints().getScan());
        walkin.setText(store.getTotalGiftPoints().getWalkin());
        purchase.setText(store.getTotalGiftPoints().getPurchase());
        descriptionMerchant.setText(store.getAddress());
        ranges=store.getDepartments();
        grid= (GridView)findViewById(R.id.departments);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Promociones promo = new Promociones();
                promo = listPromoArray.get(position);
                Intent intentSuccess = new Intent(getActivity().getBaseContext(), DetailPromo.class);
                intentSuccess.putExtra("Detail", promo);
                startActivity(intentSuccess);
                */
            }
        });
        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpoints.toString().equals("0"))
                {
                    Toast.makeText(getApplication(), "Aun no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    openHistory();
                }
            }
        });
        openHistoryPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpoints.toString().equals("0"))
                {
                    Toast.makeText(getApplication(), "Aun no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    openHistory();
                }
            }
        });
        chargeDepartments();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    public void openHistory(){
        Intent intent = new Intent(this.getBaseContext(), HistotyPointsActivity.class);
        intent.putExtra("idUser",idUser);
        startActivity(intent);
    }
    public void chargeDepartments(){

        adapter=new CustomAdapterDepartments(this, ranges);
        grid.setAdapter(adapter);
    }

}
