package com.centaurosolutions.com.beacon.user.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;


public class User {

	@Id 
	private String id;
	
	private String user;
	
	private String password;
	
	private Boolean enable;	

	private int categoryId;
	
	private int totalGiftPoints;
	
	private Date  creationDate;
	
	private Date  modifiedDate;
	
	private String name;
	
	private String lastName;
	
	private String email;
	
	private String phone;
	
	private String socialNetworkId;
	
	private String socialNetworkType;
	
	private String socialNetworkJson;

	private String gender;

	private ArrayList<String> productWishList;
	
	private String pathImage;
	
	public ArrayList<Preferences> preference;
	
	public User() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @param user
	 * @param password
	 * @param enable
	 * @param categoryId
	 * @param totalGiftPoints
	 * @param creationDate
	 * @param modifiedDate
	 * @param name
	 * @param lastName
	 * @param email
	 * @param phone
	 * @param socialNetworkId
	 * @param socialNetworkType
	 * @param socialNetworkJson
	 * @param gender
	 * @param productWishList
	 */
	public User(String user, String password, Boolean enable, int categoryId,
			int totalGiftPoints, Date creationDate, Date modifiedDate, String name,
			String lastName, String email, String phone, String socialNetworkId,
			String socialNetworkType, String socialNetworkJson, String gender, ArrayList<String> productWishList,
			String pathImage,ArrayList<Preferences> preference) {
		super();
		this.user = user;
		this.password = password;
		this.enable = enable;
		this.categoryId = categoryId;
		this.totalGiftPoints = totalGiftPoints;
		this.creationDate = creationDate;
		this.modifiedDate = modifiedDate;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.socialNetworkId = socialNetworkId;
		this.socialNetworkType = socialNetworkType;
		this.socialNetworkJson = socialNetworkJson;
		this.gender = gender;
		this.productWishList = productWishList;
		this.pathImage=pathImage;
		this.preference=preference;
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
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the category_id to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the total_gift_points
	 */
	public int getTotalGiftPoints() {
		return totalGiftPoints;
	}

	/**
	 * @param totalGiftPoints the total_gift_points to set
	 */
	public void setTotalGiftPoints(int totalGiftPoints) {
		this.totalGiftPoints = totalGiftPoints;
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
	
	
	/**
	 * @return the socialNetworkId
	 */
	public String getSocialNetworkId() {
		return socialNetworkId;
	}

	/**
	 * @param socialNetworkId  to set
	 */
	public void setSocialNetworkId(String socialNetworkId) {
		this.socialNetworkId = socialNetworkId;
	}
	
	/**
	 * @return the socialNetworkType
	 */
	public String getSocialNetworkType() {
		return socialNetworkType;
	}

	/**
	 * @param socialNetworkType to set
	 */
	public void setSocialNetworkType(String socialNetworkType) {
		this.socialNetworkType = socialNetworkType;
	}
	
	/**
	 * @return the socialNetworkJson
	 */
	public String getSocialNetworkJson() {
		return socialNetworkJson;
	}

	/**
	 * @param socialNetworkJson to set
	 */
	public void setSocialNetworkJson(String socialNetworkJson) {
		this.socialNetworkJson = socialNetworkJson;
	}


	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	public ArrayList<String> getProductWishList() {
		return productWishList;
	}

	public void setProductWishList(ArrayList<String> productWishList) {
		this.productWishList = productWishList;
	}
	/**
	 * @return the path_image
	 */
	public String getPathImage() {
		return pathImage;
	}

	/**
	 * @param id the path_image to set
	 */
	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}
	
	/**
	 * @return the preferences
	 * */
	public ArrayList<Preferences> getPreference() {
		return preference;
	}

	/**
	 * @param id the preferences to set
	 */
	public void setPreference(ArrayList<Preferences> preference) {
		this.preference = preference;
	}
}

