package com.centaurosolutions.com.beacon.utils.model;

/**
 * Created by Eduardo on 1/7/2016.
 */
public class OfferHistoryResponse {

    public String message;
    public int status;
    public Object offerHistory;

    public OfferHistoryResponse(){}

    public OfferHistoryResponse(String message, int status, Object offerHistory) {
        this.message = message;
        this.status = status;
        this.offerHistory = offerHistory;
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
        return offerHistory;
    }

    public void setOfferhistory(Object offerHistory) {
        this.offerHistory = offerHistory;
    }
}
