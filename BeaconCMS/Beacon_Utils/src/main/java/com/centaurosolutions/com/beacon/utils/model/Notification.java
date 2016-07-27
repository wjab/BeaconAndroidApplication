package com.centaurosolutions.com.beacon.utils.model;

import java.util.Date;
import org.springframework.data.annotation.Id;

public class Notification 
{
	@Id
	private String id;
	private String message;
	private boolean read;
	private Date creationDate;
	private Date readDate;
	
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
}
