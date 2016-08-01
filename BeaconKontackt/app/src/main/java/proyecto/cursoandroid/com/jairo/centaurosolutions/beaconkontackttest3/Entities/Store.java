package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Centauro on 16/06/2016.
 */
public class Store implements Serializable {
    private int pointToGive;
    private String urlImagen;
    private String address;
    private String id;
    private String merchantName;
    private String country;
    private String city;
    private ArrayList<Department> departments;
    private TotalGiftPoints totalGiftPoints;

    public int getPointToGive() {
        return pointToGive;
    }

    public void setPointToGive(int pointToGive) {
        this.pointToGive = pointToGive;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<Department> getDepartments() { return departments; }

    public void setDepartments(ArrayList<Department> departments) { this.departments = departments; }

    public TotalGiftPoints getTotalGiftPoints() { return totalGiftPoints; }

    public void setTotalGiftPoints(TotalGiftPoints totalGiftPoints) { this.totalGiftPoints = totalGiftPoints; }

}