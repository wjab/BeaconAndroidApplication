package com.centaurosolutions.com.beacon.visitorhistory.model;
import org.springframework.data.annotation.Id;
import java.util.Date;
public class VisitorHistory {
	
	@Id
	private String id;
	private String user_id;
	private String merchant_id;
	private String shopzone_id;
	private Date rowDate;
	/**
	 * @param id
	 * @param user_id
	 * @param merchant_id
	 * @param shopzone_id
	 * @param rowDate
	 */
	public VisitorHistory(String user_id, String merchant_id, String shopzone_id, Date rowDate) {
		super();
		this.user_id = user_id;
		this.merchant_id = merchant_id;
		this.shopzone_id = shopzone_id;
		this.rowDate = rowDate;
	}
	
	
	public VisitorHistory(){
		
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
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}


	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}


	/**
	 * @return the merchant_id
	 */
	public String getMerchant_id() {
		return merchant_id;
	}


	/**
	 * @param merchant_id the merchant_id to set
	 */
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}


	/**
	 * @return the shopzone_id
	 */
	public String getShopzone_id() {
		return shopzone_id;
	}


	/**
	 * @param shopzone_id the shopzone_id to set
	 */
	public void setShopzone_id(String shopzone_id) {
		this.shopzone_id = shopzone_id;
	}


	/**
	 * @return the rowDate
	 */
	public Date getRowDate() {
		return rowDate;
	}


	/**
	 * @param rowDate the rowDate to set
	 */
	public void setRowDate(Date rowDate) {
		this.rowDate = rowDate;
	}
	
	
	
	

}
