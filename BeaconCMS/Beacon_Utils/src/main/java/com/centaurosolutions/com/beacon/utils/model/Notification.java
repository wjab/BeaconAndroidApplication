package com.centaurosolutions.com.beacon.utils.model;

import java.util.Date;
import org.springframework.data.annotation.Id;

public class Notification 
{
	@Id
	private String id;
	private String userId;
	private String message;
	private boolean read;
	private Date creationDate;
	private Date readDate;
	
	public Notification(String userId, String message, boolean read, Date creationDate, Date readDate) 
	{
		this.userId = userId;
		this.message = message;
		this.read = read;
		this.creationDate = creationDate;
		this.readDate = readDate;
	}
	
	public void setId(String id) { this.id = id; }
	public String getId() { return id; }
	
	public void setMessage(String message) { this.message = message; }
	public String getMessage() { return message; }
	
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
