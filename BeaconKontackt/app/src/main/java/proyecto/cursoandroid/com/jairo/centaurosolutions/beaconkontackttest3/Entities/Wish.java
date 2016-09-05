package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities;

import java.io.Serializable;

/**
 * Created by Centauro on 11/07/2016.
 */
public class Wish implements Serializable{
    private String productId;
    private String productName;
    private int price;
    private int pointsByPrice;
    private String imageUrlList;
    private String dateCreation;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(String imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getPointsByPrice() {  return pointsByPrice;   }

    public void setPointsByPrice(int pointsByPrice) {  this.pointsByPrice = pointsByPrice;   }

}
