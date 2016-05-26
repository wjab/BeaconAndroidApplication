package com.centaurosolutions.com.beacon.utils.model;

/**
 * Created by Eduardo on 20/5/2016.
 */
public class OfferHistoryAttemptResponse {

    private  int status;
    private  String message;
    private  OfferHistoryAttempt attemptData;



    public OfferHistoryAttemptResponse(){

    }


    public OfferHistoryAttemptResponse(String message, int status, OfferHistoryAttempt attemptData) {
        this.message = message;
        this.status = status;
        this.attemptData = attemptData;
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

    public OfferHistoryAttempt getAttemptData() {
        return attemptData;
    }

    public void setAttemptData(OfferHistoryAttempt attemptData) {
        this.attemptData = attemptData;
    }
}
