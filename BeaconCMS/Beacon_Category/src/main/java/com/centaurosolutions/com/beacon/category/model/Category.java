package com.centaurosolutions.com.beacon.category.model;
import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * @author Jairo
 *
 */
public class Category {
	/**
	 * 
	 */
	@Id
	private String id;
	private int startRange;
	private int endRange;
	private int giftPoints;
	private Date creationDate;
	private Date modifieldDate;
	private String categoryName;
	private Boolean enable;
	
	
	 
	public Category(int startRange, int endRange, int giftPoints, Date creationDate, Date modifieldDate, String categoryName, Boolean enable){
		// TODO Auto-generated constructor stub
		this.startRange = startRange;
		this.endRange = endRange;
		this.giftPoints = giftPoints;
		this.modifieldDate = modifieldDate;
		this.creationDate = creationDate;
		this.categoryName = categoryName;
		this.enable = enable;
	}
	
	public Category(){}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public void setStartRange(int startRange) {
		this.startRange = startRange;
	}
	
	public void setEndRange(int endRange) {
		this.endRange = endRange;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public void setModifieldDate(Date modificationDate) {
		this.modifieldDate = modificationDate;
	}
	
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
	public void setGiftPoints(int giftPoints) {
		this.giftPoints = giftPoints;
	}

	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public Boolean getEnable() {
		return enable;
	}
	public int getEndRange() {
		return endRange;
	}
	
	public Date getModifieldDate() {
		return modifieldDate;
	}
	
	public int getGiftPoints() {
		return giftPoints;
	}
	
	public String getId() {
		return id;
	}
	
	public int getStartRange() {
		return startRange;
	}
}
