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
}
