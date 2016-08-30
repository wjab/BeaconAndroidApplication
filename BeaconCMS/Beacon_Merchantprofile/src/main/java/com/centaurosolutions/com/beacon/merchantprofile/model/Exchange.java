package com.centaurosolutions.com.beacon.merchantprofile.model;

/**
 * Created by Eduardo on 29/8/2016.
 */
public class Exchange {

    private String currency;
    private int value;

    public Exchange(String currency, int value) {
        this.currency = currency;
        this.value = value;
    }

    public Exchange(){

    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
