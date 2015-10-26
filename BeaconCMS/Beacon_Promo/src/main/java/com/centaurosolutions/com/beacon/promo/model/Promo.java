package com.centaurosolutions.com.beacon.promo.model;
import org.springframework.data.annotation.Id;
import java.util.Date;

public class Promo {
	

	@Id
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
	
	
	
	public Promo (){
		
	}
	
	public Promo(boolean enable, String profile_id, String code, int gift_points, int attempt, Date startDate,
			Date endDate, String type, int availability, Date creationDate, Date modifiedDate, String updatedby) {
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
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(String profile_id) {
		this.profile_id = profile_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getGift_points() {
		return gift_points;
	}

	public void setGift_points(int gift_points) {
		this.gift_points = gift_points;
	}

	public int getAttempt() {
		return attempt;
	}

	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}



	

	



}
