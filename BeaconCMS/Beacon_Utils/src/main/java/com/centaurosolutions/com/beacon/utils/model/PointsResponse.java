package com.centaurosolutions.com.beacon.utils.model;

/**
 * Created by Eduardo on 26/7/2016.
 */
public class PointsResponse {


    private int status;
    private String message;
    private Object points;


    public PointsResponse(){

    }

    public PointsResponse(int status, String message, Points points) {
        this.status = status;
        this.message = message;
        this.points = points;
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

    public Object getPoints() {
        return points;
    }

    public void setPoints(Object points) {
        this.points = points;
    }
}
