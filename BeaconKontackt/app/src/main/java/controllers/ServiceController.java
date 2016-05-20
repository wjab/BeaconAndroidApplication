package controllers;

import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dcortess on 9/4/15.
 */
public class ServiceController
{
    String url;
    int method;
    Map<String, Object> params;
    Map<String, String> header;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void jsonObjectRequest(String url, int method, Map<String, Object> params, Map<String,String> headers, Response.Listener<JSONObject> response, Response.ErrorListener error) {
        this.url = url;
        this.method = method;
        this.params = params;
        this.header = headers;
        String tag_json_obj = "json_obj_req";

        JsonObjectRequest objectRequest;

        if (this.getParams() != null) {
            objectRequest = new JsonObjectRequest(this.getMethod(), this.getUrl(), new JSONObject(this.getParams()), response, error){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return  header;
                }

            };
        } else {
            objectRequest = new JsonObjectRequest(this.getMethod(), this.getUrl(), response, error){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return  header;
                }

            };
        }

        // {}
        // []

        AppController.getInstance().addToRequestQueue(objectRequest, tag_json_obj);

    }

    public void jsonArrayRequest(String url, int method, Map<String, Object> params,Map<String,String> headers, Response.Listener<JSONArray> response, Response.ErrorListener error) {
        this.url = url;
        this.method = method;
        this.params = params;
        this.header = headers;

        String tag_json_arry = "json_array_req";

        JsonArrayRequest arrayRequest;

        if (this.getParams() != null) {
            arrayRequest = new JsonArrayRequest(this.getMethod(), this.getUrl(), new JSONObject(this.getParams()), response, error){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header;
                }
            };
        } else {
            arrayRequest = new JsonArrayRequest(this.getMethod(), this.getUrl(), response, error){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header;
                }

            };
        }
        //Arreglo []
        AppController.getInstance().addToRequestQueue(arrayRequest, tag_json_arry);
    }

    public void stringRequest(String url, int method, Map<String, Object> params, Map<String, String> headers, Response.Listener<String> response, Response.ErrorListener error) {
        this.url = url;
        this.method = method;
        this.params = params;
        this.header = headers;

        String tag_string_req = "string_req";

        StringRequest strReq;

        strReq = new StringRequest(this.getMethod(), this.getUrl(), response, error){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return header;
            }

        };

        //String
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public void setCustomHeader(){

    }

    public void imageRequest(String url, ImageView imageView, int icon_loading, int icon_error) {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView, icon_loading, icon_error));
    }

    public void imageRequest(String url, CircleImageView imageView, int icon_loading, int icon_error) {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView, icon_loading, icon_error));
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }
}
