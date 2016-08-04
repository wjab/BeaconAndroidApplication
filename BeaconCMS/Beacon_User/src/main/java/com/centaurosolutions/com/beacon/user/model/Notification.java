package com.centaurosolutions.com.beacon.user.model;

import java.util.Date;

/**
 * Created by Eduardo on 4/8/2016.
 */
public class Notification {

    private String id;
    private String userId;
    private String message;
    private String type;
    private boolean read;
    private Date creationDate;
    private Date readDate;

    public Notification(String userId, String message, String type, boolean read, Date creationDate, Date readDate)
    {
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.read = read;
        this.creationDate = creationDate;
        this.readDate = readDate;
    }

    public void setId(String id) { this.id = id; }
    public String getId() { return id; }

    public void setMessage(String message) { this.message = message; }
    public String getMessage() { return message; }

    public void setType(String type) { this.type = type; }
    public String getType() { return type; }

    public void setRead(boolean read) { this.read = read; }
    public boolean getRead() { return read; }

    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public Date getCreationDate() { return creationDate; }

    public void setReadDate(Date readDate) { this.readDate = readDate; }
    public Date getReadDate() { return readDate; }

    /**
     * @return the userId
     */
    public String getUserId() { return userId; }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) { this.userId = userId; }
}
