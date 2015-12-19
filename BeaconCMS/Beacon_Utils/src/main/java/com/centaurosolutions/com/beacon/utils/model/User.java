package com.centaurosolutions.com.beacon.utils.model;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	private String id;
	
	private String user;
	
	private String password;
	
	private Boolean enable;	

	private int category_id;
	
	private int total_gift_points;
	
	private Date  creationDate;
	
	private Date  modifiedDate;
	
	private String name;
	
	private String lastName;
	
	private String email;
	
	private String phone;
	
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @param user
	 * @param password
	 * @param enable
	 * @param category_id
	 * @param total_gift_points
	 * @param creationDate
	 * @param modifiedDate
	 * @param name
	 * @param lastName
	 * @param email
	 * @param phone
	 */
	public User(String user, String password, Boolean enable, int category_id, int total_gift_points, Date creationDate,
			Date modifiedDate, String name, String lastName, String email, String phone) {
		super();
		this.user = user;
		this.password = password;
		this.enable = enable;
		this.category_id = category_id;
		this.total_gift_points = total_gift_points;
		this.creationDate = creationDate;
		this.modifiedDate = modifiedDate;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
	}





	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enable
	 */
	public Boolean getEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return the category_id
	 */
	public int getCategory_id() {
		return category_id;
	}

	/**
	 * @param category_id the category_id to set
	 */
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	/**
	 * @return the total_gift_points
	 */
	public int getTotal_gift_points() {
		return total_gift_points;
	}

	/**
	 * @param total_gift_points the total_gift_points to set
	 */
	public void setTotal_gift_points(int total_gift_points) {
		this.total_gift_points = total_gift_points;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}



	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}



	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	

}

