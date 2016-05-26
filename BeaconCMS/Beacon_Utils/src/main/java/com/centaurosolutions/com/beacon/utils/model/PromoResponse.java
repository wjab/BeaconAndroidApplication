package com.centaurosolutions.com.beacon.utils.model;

/**
 * Created by Eduardo on 20/5/2016.
 */
public class PromoResponse {

    private int status;
    private String message;
    private Promo promo;


    public PromoResponse(int status, String message, Promo promo) {
        this.status = status;
        this.message = message;
        this.promo = promo;
    }

    public PromoResponse(){

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }



}
