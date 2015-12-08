package model.promo;

/**
 * Created by Eduardo on 01/12/2015.
 */
public class PromoExp  {


    private String promoId;
    private Double expiration;


    public PromoExp(){

    }

    public PromoExp(Double expiration, String promoId) {
        this.expiration = expiration;
        this.promoId = promoId;
    }




    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public Double getExpiration() {
        return expiration;
    }

    public void setExpiration(Double expiration) {
        this.expiration = expiration;
    }






}
