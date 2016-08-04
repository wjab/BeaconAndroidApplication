package com.centaurosolutions.com.beacon.merchantprofile.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Date;

public class MerchantProfile {
	
	@Id
	public String id;
	public String country;
	public String city;
	public ArrayList<MerchantContactData> contactNumbers;
	public String timeZone;
	public String merchantName;
	public String address;
	public String image;
	public String businessType;
	public ArrayList<MerchantUser> users;
	public boolean enable;
	public int pointsToGive;
	public Date creationDate;
	public Date modifiedDate;
	public String updatedBy;
	public String latitude ;
	public String longitude;

	
	public ArrayList<Department> departments;
	public TotalGiftPoints totalGiftPoints;
	


	public MerchantProfile(){
		
	}


	


	/**
	 * @param country
	 * @param city
	 * @param contactNumbers
	 * @param timeZone
	 * @param merchantName
	 * @param address
	 * @param image
	 * @param businessType
	 * @param users
	 * @param enable
	 * @param pointsToGive
	 * @param creationDate
	 * @param modifiedDate
	 * @param updatedBy
	 * @param latitude
	 * @param longitude
	 */
	public MerchantProfile(String country, String city, ArrayList<MerchantContactData> contactNumbers, String timeZone,
			String merchantName, String address, String image, String businessType,
			ArrayList<MerchantUser> users, boolean enable, int pointsToGive,
			Date creationDate, Date modifiedDate, String updatedBy,
			String latitude,String longitude, ArrayList<Department> departments, TotalGiftPoints totalGiftPoints) {
		super();
		this.country = country;
		this.city = city;
		this.contactNumbers = contactNumbers;
		this.timeZone = timeZone;
		this.merchantName = merchantName;
		this.address = address;
		this.image = image;
		this.businessType = businessType;
		this.users = users;
		this.enable = enable;
		this.pointsToGive = pointsToGive;
		this.creationDate = creationDate;
		this.modifiedDate = modifiedDate;
		this.updatedBy = updatedBy;
		this.latitude = latitude;
		this.longitude = longitude;
		this.departments = departments;
		this.totalGiftPoints = totalGiftPoints;
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
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the contactNumbers
	 */
	public ArrayList<MerchantContactData> getContactNumbers() {
		return contactNumbers;
	}

	/**
	 * @param contactNumbers the contactNumbers to set
	 */
	public void setContactNumbers(ArrayList<MerchantContactData> contactNumbers) {
		this.contactNumbers = contactNumbers;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the businessType
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * @param businessType the businessType to set
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * @return the users
	 */
	public ArrayList<MerchantUser> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(ArrayList<MerchantUser> users) {
		this.users = users;
	}

	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return the pointsToGive
	 */
	public int getPointsToGive() {
		return pointsToGive;
	}

	/**
	 * @param pointsToGive the pointsToGive to set
	 */
	public void setPointsToGive(int pointsToGive) {
		this.pointsToGive = pointsToGive;
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
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}




	/**
	 * @param longitude the longitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}




	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}




	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
	
	
