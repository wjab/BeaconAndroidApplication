package com.centaurosolutions.com.beacon.points.model;

import java.util.Date;

/**
 * Created by Eduardo on 26/7/2016.
 */
public class Points {

    private String userId;
    private String type;
    private Date creationDate;
    private Date expirationDate;
    private String redeemedByUserId;
    private Date redeemedDate;
    private String securityCode;
    private String userType;
    private int points;


    public Points(String userId, String type, Date creationDate, Date expirationDate, String redeemedByUserId, Date redeemedDate, String securityCode, String userType, int points) {
        this.userId = userId;
        this.type = type;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.redeemedByUserId = redeemedByUserId;
        this.redeemedDate = redeemedDate;
        this.securityCode = securityCode;
        this.userType = userType;
        this.points = points;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getRedeemedDate() {
        return redeemedDate;
    }

    public void setRedeemedDate(Date redeemedDate) {
        this.redeemedDate = redeemedDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getRedeemedByUserId() {
        return redeemedByUserId;
    }

    public void setRedeemedByUserId(String redeemedByUserId) {
        this.redeemedByUserId = redeemedByUserId;
    }
}
