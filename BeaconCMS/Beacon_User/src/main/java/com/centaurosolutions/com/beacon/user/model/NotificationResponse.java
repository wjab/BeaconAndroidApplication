package com.centaurosolutions.com.beacon.user.model;

/**
 * Created by Eduardo on 4/8/2016.
 */
public class NotificationResponse {

    private int status;
    private String message;
    private Object notificationResult;


    public NotificationResponse(int status, String message, Notification notificationResult) {
        this.status = status;
        this.message = message;
        this.notificationResult = notificationResult;
    }

    public NotificationResponse(){

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

    public Object getNotificationResult() {
        return notificationResult;
    }

    public void setNotificationResult(Object notificationResult) {
        this.notificationResult = notificationResult;
    }
}
