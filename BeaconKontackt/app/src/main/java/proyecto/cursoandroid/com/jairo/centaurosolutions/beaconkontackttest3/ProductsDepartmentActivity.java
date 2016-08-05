package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterProductDepartment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Department;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.ProductStore;
import utils.NonStaticUtils;

public class ProductsDepartmentActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    Intent intent;
    String mpoints, userAcumulatedPoints;
    private static String webServiceUser;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    private static String idUser;
    GridView grid;
    CustomAdapterProductDepartment adapter;
    private ArrayList<ProductStore> ranges;
    TextView pointsAction, name;
    ImageView openHistoryPoints;
    private static Context context;
    private int activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_department);
        activity = 1;
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
        context = this;
        Department department = (Department)intent.getSerializableExtra("department");
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_promodetail, null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        openHistoryPoints = (ImageView) actionBarLayout.findViewById(R.id.openHistoryPoints);
        pointsAction = (TextView) actionBarLayout.findViewById(R.id.userPointsAction);
        name = (TextView)findViewById(R.id.nameDepartment);
        name.setText(department.getName());
        pointsAction.setText(userAcumulatedPoints.toString());
        ranges = department.getProducts();
        grid = (GridView)findViewById(R.id.products);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductStore product = new ProductStore();
                product = ranges.get(position);
                Intent intentSuccess = new Intent(getBaseContext(),ProductDetailActivity.class);
                intentSuccess.putExtra("product", product);
                startActivity(intentSuccess);

            }
        });
        pointsAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mpoints.toString().equals("0")) {
                    Toast.makeText(getApplication(), "Aun no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                } else {
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
        webServiceUser=getString(R.string.WebService_User);
        chargeDepartments();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    public void openHistory(){
        Intent intent = new Intent(context, HistotyPointsActivity.class);
        intent.putExtra("idUser",idUser);
        startActivity(intent);
    }
    public void chargeDepartments(){

        adapter = new CustomAdapterProductDepartment(this, ranges,activity);
        grid.setAdapter(adapter);
    }

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;

    public void service(String productId,String productName,float price){
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", idUser);
        mapParams.put("productId", productId);
        mapParams.put("productName",productName);
        mapParams.put("price",price);
        mapParams.put("imageUrlList", "http://www.evga.com/products/images/gallery/02G-P4-2958-KR_MD_1.jpg");

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = webServiceUser+"user/wishlist/add";
        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        try
        {
            if (response.getString("message").toString().equals("User updated"))
            {
                Toast.makeText(context, "Añadido correctamente", Toast.LENGTH_SHORT).show();
            }
            if (response.getString("message").toString().equals("Product already added to wishlist"))
            {
                Toast.makeText(context, "El producto ya existe en la lista de deseos", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {

            Toast.makeText(context, "Hubo un problema al añadirlo", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    /************ Metodos para el escaneo ***************/
    public void ActivateScan()
    {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt( context.getString(R.string.textoIntentScan));
        IntentIntegrator intentIntegrator = integrator.setResultDisplayDuration(0);
        integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    /**
     * function handle scan result
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null)
        {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();


        }
        else
        {
            Toast toast = Toast.makeText(this.getApplicationContext(),this.getText(R.string.codigoNoEscaneado), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
