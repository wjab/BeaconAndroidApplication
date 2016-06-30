package com.centaurosolutions.com.beacon.utils.model;

/**
 * Created by Eduardo on 28/6/2016.
 */
public class MerchantProductResponse {


    private int status;
    private String message;
    private MerchantProduct merchantProduct;


    public MerchantProductResponse(int status, MerchantProduct merchantProduct, String message) {
        this.status = status;
        this.merchantProduct = merchantProduct;
        this.message = message;
    }

    public  MerchantProductResponse(){}


    public MerchantProduct getMerchantProduct() {
        return merchantProduct;
    }

    public void setMerchantProduct(MerchantProduct merchantProduct) {
        this.merchantProduct = merchantProduct;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



}
