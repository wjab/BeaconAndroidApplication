package com.centaurosolutions.com.beacon.utils.model;

/**
 * Created by Eduardo on 1/7/2016.
 */
public class OfferHistoryResponse {

    public String message;
    public int status;
    public Object offerhistory;

    public OfferHistoryResponse(){}

    public OfferHistoryResponse(String message, int status, Object offerhistory) {
        this.message = message;
        this.status = status;
        this.offerhistory = offerhistory;
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

    public Object getOfferhistory() {
        return offerhistory;
    }

    public void setOfferhistory(Object offerhistory) {
        this.offerhistory = offerhistory;
    }
}
