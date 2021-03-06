/**
 * 
 */
package com.centaurosolutions.com.beacon.utils.model;

import java.util.ArrayList;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Eduardo
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Promo {
	


	private String id;
	private boolean enable;
	private String profile_id;
	private String code;
	private int gift_points;
	private int attempt;
	private Date startDate;
	private Date endDate;
	private String type;
	private int availability;
	private Date creationDate;
	private Date modifiedDate;
	private String updatedby;
	private String description;
	private Boolean isAutomatic;
	private String image;
	private String title;
	private int interval;
	
	
	
	public Promo (){
		
	}



	/**
	 * @param enable
	 * @param profile_id
	 * @param code
	 * @param gift_points
	 * @param attempt
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param availability
	 * @param creationDate
	 * @param modifiedDate
	 * @param updatedby
	 * @param description
	 * @param isAutomatic
	 * @param images
	 */
	public Promo(boolean enable, String profile_id, String code, int gift_points, int attempt, Date startDate,
			Date endDate, String type, int availability, Date creationDate, Date modifiedDate, String updatedby, String title,
			String description, Boolean isAutomatic, String image, int interval) {
		super();
		this.enable = enable;
		this.profile_id = profile_id;
		this.code = code;
		this.gift_points = gift_points;
		this.attempt = attempt;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.availability = availability;
		this.creationDate = creationDate;
		this.modifiedDate = modifiedDate;
		this.updatedby = updatedby;
		this.title = title;
		this.description = description;
		this.isAutomatic = isAutomatic;
		this.image = image;
		this.interval = interval;
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
	 * @return the profile_id
	 */
	public String getProfile_id() {
		return profile_id;
	}



	/**
	 * @param profile_id the profile_id to set
	 */
	public void setProfile_id(String profile_id) {
		this.profile_id = profile_id;
	}



	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}



	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}



	/**
	 * @return the gift_points
	 */
	public int getGift_points() {
		return gift_points;
	}



	/**
	 * @param gift_points the gift_points to set
	 */
	public void setGift_points(int gift_points) {
		this.gift_points = gift_points;
	}



	/**
	 * @return the attempt
	 */
	public int getAttempt() {
		return attempt;
	}



	/**
	 * @param attempt the attempt to set
	 */
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
	

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}



	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}



	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}



	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}



	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}



	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}



	/**
	 * @return the availability
	 */
	public int getAvailability() {
		return availability;
	}



	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(int availability) {
		this.availability = availability;
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
	 * @return the updatedby
	 */
	public String getUpdatedby() {
		return updatedby;
	}



	/**
	 * @param updatedby the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @return the isAutomatic
	 */
	public Boolean getIsAutomatic() {
		return isAutomatic;
	}



	/**
	 * @param isAutomatic the isAutomatic to set
	 */
	public void setIsAutomatic(Boolean isAutomatic) {
		this.isAutomatic = isAutomatic;
	}



	/**
	 * @return the images
	 */
	public String getImages() {
		return image;
	}



	/**
	 * @param images the images to set
	 */
	public void setImages(String image) {
		this.image = image;
	}


	/**
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}


	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	
	
	
	


	


}
