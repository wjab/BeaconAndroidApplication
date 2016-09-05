package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3;

import android.app.Activity;
import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.ServiceController;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Adaptadores.CustomAdapterProductDepartment;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Department;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.ProductStore;
import proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities.Wish;
import utils.NonStaticUtils;

public class ProductsDepartmentActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener
{
    Intent intent;
    String mpoints, userAcumulatedPoints;
    private static String webServiceUser, webServiceUtils;
    SharedPreferences preferences;
    NonStaticUtils nonStaticUtils;
    private static String idUser, merchantId;
    private static GridView grid;
    CustomAdapterProductDepartment adapter;
    private static ArrayList<ProductStore> ranges;
    TextView pointsAction, name;
    ImageView openHistoryPoints, departmentImage;
    LinearLayout back;
    private static Context context;
    private static Activity thisActivity;
    private int activity = 1;
    public static ArrayList<Wish> listArray;

    ServiceController serviceController;
    Response.Listener<JSONObject> response;
    Response.ErrorListener responseError;
   private static Button addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_department);
        activity = 1;

        /* Obtiene de las preferencias compartidas, la cantidad de los puntos*/
        nonStaticUtils = new NonStaticUtils();

        preferences = nonStaticUtils.loadLoginInfo(this);
        mpoints = preferences.getInt("points", 0) + "";
        ServiceController imageRequest =  new ServiceController();
        userAcumulatedPoints = String.format(getString(R.string.totalPointsLabel), mpoints);
        idUser = preferences.getString("userId", "");
        intent = getIntent();
        context = this;
        thisActivity = this;
        Department department = (Department)intent.getSerializableExtra("department");
        merchantId = intent.getStringExtra("merchantId");

        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_promodetail, null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(actionBarLayout);
        openHistoryPoints = (ImageView) actionBarLayout.findViewById(R.id.openHistoryPoints);
        departmentImage = (ImageView) findViewById(R.id.departmentImageDetail);
        back = (LinearLayout) actionBarLayout.findViewById(R.id.back);

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
        name = (TextView)findViewById(R.id.nameDepartment);
        Picasso.with(thisActivity).load(department.getUrlDepartment()).error(R.drawable.department).into(departmentImage);
        name.setText(department.getName().toUpperCase());
        pointsAction.setText(userAcumulatedPoints.toString());
        ranges = department.getProducts();
        addImage = (Button) actionBarLayout.findViewById(R.id.buttonAdd);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WishListActivity.class);
                intent.putExtra("idUser",idUser);
                intent.putExtra("code", 1);
                startActivityForResult(intent, 2);
            }
        });
        addImage.setText(String.valueOf(BackgroundScanActivity.size));
        grid = (GridView)findViewById(R.id.products);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductStore product = new ProductStore();
                product = ranges.get(position);
                Intent intentSuccess = new Intent(getBaseContext(),ProductDetailActivity.class);
                intentSuccess.putExtra("product", product);
                intentSuccess.putExtra("activity", activity);
                intentSuccess.putExtra("code", 1);
                startActivityForResult(intentSuccess, 2);
            }
        });

        pointsAction.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mpoints.toString().equals("0"))
                {
                    Toast.makeText(getApplication(), "Aun no ha obtenido puntos", Toast.LENGTH_SHORT).show();
                } else {
                    openHistory();
                }
            }
        });

        openHistoryPoints.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
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

        webServiceUser = getString(R.string.WebService_User);
        webServiceUtils = getString(R.string.WebService_Utils);
        productWishList();
    }
    @Override
    public boolean onSupportNavigateUp()
    {
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
    public void openHistory()
    {
        Intent intent = new Intent(context, HistotyPointsActivity.class);
        intent.putExtra("idUser",idUser);
        intent.putExtra("code", 1);
        startActivityForResult(intent, 2);
    }
    public void chargeDepartments()
    {
        String idProductWishList, idProduct;

        for(int j = 0; j < ranges.size(); j++ )
        {
            ranges.get(j).setStateWishList(0);
            idProduct = ranges.get(j).getProductId();
            for(int i = 0; i < listArray.size(); i++ )
                {
                    idProductWishList=listArray.get(i).getProductId();
                    if(idProductWishList.equals(idProduct))
                    {
                        ranges.get(j).setStateWishList(1);
                    }
                }
        }
        adapter = new CustomAdapterProductDepartment(thisActivity, ranges, 1);
        grid.setAdapter(adapter);
    }

    public void service(String productId, String productName, float price,String urlImage, int pointsByPrice)
    {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("userId", idUser);
        mapParams.put("productId", productId);
        mapParams.put("productName", productName);
        mapParams.put("price", price);
        mapParams.put("imageUrlList", urlImage);
        mapParams.put("pointsByPrice", pointsByPrice);

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        String url = webServiceUser+"user/wishlist/add";
        serviceController.jsonObjectRequest(url, Request.Method.POST, mapParams, map, response, responseError);
    }

    public void productWishList()
    {
        serviceController = new ServiceController();
        responseError = this;
        response = this;

        Map<String, String> map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        String url = webServiceUser + "user/id/" + idUser;
        serviceController.jsonObjectRequest(url, Request.Method.GET, null, map, response, responseError);
    }
    @Override
    public void onErrorResponse(VolleyError error)
    {

    }

    @Override
    public void onResponse(JSONObject response)
    {
        try
        {
            if(response.getString("message").toString().equals(""))
            {
                response = response.getJSONObject("user");
                JSONArray ranges1 = response.getJSONArray("productWishList");
                listArray= new ArrayList<Wish>();

                for(int i = 0; i < ranges1.length(); i++ )
                {
                    Wish element = new Wish();
                    JSONObject currRange = ranges1.getJSONObject(i);
                    element.setProductId(currRange.getString("productId"));
                    element.setProductName(currRange.getString("productName"));
                    element.setImageUrlList(currRange.getString("imageUrlList"));
                    element.setPrice(currRange.getInt("price"));
                    listArray.add(element);
                }
                chargeDepartments();
            }
            else if (response.getString("message").toString().equals("User updated"))
            {
                response = response.getJSONObject("user");
                JSONArray ranges1 = response.getJSONArray("productWishList");
                listArray = new ArrayList<Wish>();

                for(int i = 0; i < ranges1.length(); i++ )
                {
                    Wish element = new Wish();
                    JSONObject currRange = ranges1.getJSONObject(i);
                    element.setProductId(currRange.getString("productId"));
                    element.setProductName(currRange.getString("productName"));
                    element.setImageUrlList(currRange.getString("imageUrlList"));
                    element.setPrice(currRange.getInt("price"));
                    listArray.add(element);
                }
                BackgroundScanActivity.size=listArray.size();
                addImage.setText(String.valueOf(BackgroundScanActivity.size));
                Toast.makeText(context, "Añadido correctamente", Toast.LENGTH_SHORT).show();
                chargeDepartments();
            }
            else if (response.getString("message").toString().equals("Product already added to wishlist"))
            {
                Toast.makeText(context, "El producto ya existe en la lista de deseos", Toast.LENGTH_SHORT).show();
            }
            else if(response.getString("message").toString().equals("Puntos asignados correctamente"))
            {
                response = response.getJSONObject("user");

                // Actualiza los puntos del del usuario en sharedPreferences
                nonStaticUtils.UpdateUserPoints(thisActivity, response.getInt("totalGiftPoints"));
                pointsAction.setText(String.valueOf(response.getInt("totalGiftPoints")));
                Toast.makeText(context, "Puntos asignados por escaneo", Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    /************ Metodos para el escaneo ***************/
    public void ActivateScan()
    {
        IntentIntegrator integrator = new IntentIntegrator(thisActivity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt(context.getString(R.string.textoIntentScan));
        integrator.setResultDisplayDuration(0);
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
        int code = intent.getIntExtra("code", 0);

        if (code != 1)
        {
            if (scanningResult != null)
            {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();

                serviceController = new ServiceController();
                responseError = this;
                response = this;

                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("userId", idUser);
                params.put("code", scanContent);
                params.put("merchantId", merchantId);

                String url = webServiceUtils + "utils/savePointsByCode";
                serviceController.jsonObjectRequest(url, Request.Method.POST, params, headers, response, responseError);
            }
            else
            {
                Toast toast = Toast.makeText(this.getApplicationContext(), this.getText(R.string.codigoNoEscaneado), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, intent);
            addImage.setText(String.valueOf(BackgroundScanActivity.size));
            productWishList();
        }
    }

}
