package proyecto.cursoandroid.com.jairo.centaurosolutions.beaconkontackttest3.Entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Centauro on 16/06/2016.
 */
public class History  implements Serializable {
    private String id;
    private String points;
    private String merchantName;
    private String adressMerchant;
    private String promoTitle;
    private String scanDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAdressMerchant() {
        return adressMerchant;
    }

    public void setAdressMerchant(String adressMerchant) {
        this.adressMerchant = adressMerchant;
    }

    public String getPromoTitle() {
        return promoTitle;
    }

    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }
}
