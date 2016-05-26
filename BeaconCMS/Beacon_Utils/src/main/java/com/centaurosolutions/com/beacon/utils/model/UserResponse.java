package com.centaurosolutions.com.beacon.utils.model;

/**
 * Created by Eduardo on 20/5/2016.
 */
public class UserResponse {

    private int status;
    private String message;
    private User user;

    public UserResponse(){

    }

    public UserResponse(int status, String message, User user) {
        this.status = status;
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
