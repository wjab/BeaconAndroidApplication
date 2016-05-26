package com.centaurosolutions.com.beacon.utils.model;

/**
 * Created by Eduardo on 20/5/2016.
 */
public class DateDiffResponse {

    private String status;
    private String message;
    private DateDiffValues dateDiffValues;

    public DateDiffResponse(String status, DateDiffValues dateDiffValues, String message) {
        this.status = status;
        this.dateDiffValues = dateDiffValues;
        this.message = message;
    }

    public DateDiffResponse(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DateDiffValues getDateDiffValues() {
        return dateDiffValues;
    }

    public void setDateDiffValues(DateDiffValues dateDiffValues) {
        this.dateDiffValues = dateDiffValues;
    }
}
