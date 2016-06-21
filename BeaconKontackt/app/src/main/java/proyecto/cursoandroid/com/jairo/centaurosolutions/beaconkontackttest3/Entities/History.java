package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Centauro on 16/06/2016.
 */
public class History  implements Serializable {
    private String id;
    private String user_id;
    private String promo_id;
    private String merchant_id;
    private String shopZone_id;
    private Date scanDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPromo_id() {
        return promo_id;
    }

    public void setPromo_id(String promo_id) {
        this.promo_id = promo_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getShopZone_id() {
        return shopZone_id;
    }

    public void setShopZone_id(String shopZone_id) {
        this.shopZone_id = shopZone_id;
    }

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }
}
