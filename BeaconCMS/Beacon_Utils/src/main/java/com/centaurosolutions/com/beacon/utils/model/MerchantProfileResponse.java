package com.centaurosolutions.com.beacon.utils.model;

/**
 * Created by Eduardo on 29/6/2016.
 */
public class MerchantProfileResponse {

    private int status;
    private String message;
    private MerchantProfile merchantProfile;


    public MerchantProfileResponse(){

    }

    public MerchantProfileResponse(int status, String message, MerchantProfile merchantProfile) {
        this.status = status;
        this.message = message;
        this.merchantProfile = merchantProfile;
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

    public MerchantProfile getMerchantProfile() {
        return merchantProfile;
    }

    public void setMerchantProfile(MerchantProfile merchantProfile) {
        this.merchantProfile = merchantProfile;
    }
}
