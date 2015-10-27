package com.centaurosolutions.com.beacon.user.model;

import org.springframework.data.annotation.Id;

public class User {

	@Id 
	private String id;
	
	private String user;
	
	private String password;
	
	private Boolean enable;	

	private int category_id;
	
	private int total_gift_points;
	
	private String  creationDate;
	
	private String  modifiedDate;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String pUser, String pPassword, Boolean pEnable, int pCategory_id, int pTotal_gift_points, String pCreationDate, String pModifiedDate ) {
		// TODO Auto-generated constructor stub
		this.user = pUser;
		this.password = pPassword;
		this.enable = pEnable;
		this.category_id = pCategory_id;
		this.total_gift_points = pTotal_gift_points;
		this.creationDate = pCreationDate;
		this.modifiedDate = pModifiedDate;		
	}	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getTotal_gift_points() {
		return total_gift_points;
	}

	public void setTotal_gift_points(int total_gift_points) {
		this.total_gift_points = total_gift_points;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
