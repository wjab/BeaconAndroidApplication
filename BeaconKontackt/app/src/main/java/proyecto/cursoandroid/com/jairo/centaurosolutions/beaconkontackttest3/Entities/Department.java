package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Centauro on 16/06/2016.
 */
public class Department implements Serializable {

    private String name;
    private ArrayList<ProductStore> products;
    private String id;
    private String urlImageShop;
    private String urlDepartment;

    public String getName() { return name; }
    public void setName(String name) { this.name = name;  }

    public ArrayList<ProductStore> getProducts() { return products;   }
    public void setProducts(ArrayList<ProductStore> products) { this.products = products; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlImageShop() {
        return urlImageShop;
    }

    public void setUrlImageShop(String urlImageShop) {
        this.urlImageShop = urlImageShop;
    }

    public String getUrlDepartment() {
        return urlDepartment;
    }

    public void setUrlDepartment(String urlDepartment) {
        this.urlDepartment = urlDepartment;
    }
}
