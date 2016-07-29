package com.centaurosolutions.com.beacon.points.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by Eduardo on 26/7/2016.
 */
public class Points {

    @Id
    private String id;
    private String userId;
    private Date creationDate;
    private Date expirationDate;
    private String redeemedByUserId;
    private Date redeemedDate;
    private String code;
    private int points;
    private String status;

    public Points(String userId, Date creationDate, Date expirationDate, String redeemedByUserId, Date redeemedDate, String code,int points, String status) {
        this.userId = userId;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.redeemedByUserId = redeemedByUserId;
        this.redeemedDate = redeemedDate;
        this.code = code;
        this.points = points;
        this.status = status;
    }

    public Points(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
