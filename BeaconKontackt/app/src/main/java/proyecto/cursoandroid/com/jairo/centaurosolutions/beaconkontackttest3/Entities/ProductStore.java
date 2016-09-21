package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Centauro on 20/06/2016.
 */
public class ProductStore  implements Serializable {
    private String productId;
    private String productName;
    private float price;
    private String details;
    private ArrayList<String> imageUrlList;
    private int stateWishList;
    private String urlImageShow;
    private boolean allowScan;
    private float pointsByScan;
    private int pointsByPrice;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ArrayList<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(ArrayList<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public int getStateWishList() {
        return stateWishList;
    }

    public void setStateWishList(int stateWishList) {
        this.stateWishList = stateWishList;
    }

    public String getUrlImageShow() {
        return urlImageShow;
    }

    public void setUrlImageShow(String urlImageShow) {
        this.urlImageShow = urlImageShow;
    }

    public boolean getAllowScan() {
        return allowScan;
    }

    public void setAllowScan(boolean allowScan) {
        this.allowScan = allowScan;
    }

    public float getPointsByScan() {
        return pointsByScan;
    }

    public void setPointsByScan(float pointsByScan) {
        this.pointsByScan = pointsByScan;
    }

    public int getPointsByPrice() {  return pointsByPrice;  }

    public void setPointsByPrice(int pointsByPrice) { this.pointsByPrice = pointsByPrice;  }

}
