package com.centaurosolutions.com.beacon.visitorhistory.model;
import org.springframework.data.annotation.Id;
import java.util.Date;
public class VisitorHistory {
	
	@Id
	private String id;
	private String userId;
	private String merchantId;
	private String shopzoneId;
	private Date rowDate;
	/**
	 * @param id
	 * @param userId
	 * @param merchantId
	 * @param shopzoneId
	 * @param rowDate
	 */
	public VisitorHistory(String userId, String merchantId, String shopzoneId, Date rowDate) 
	{
		super();
		this.userId = userId;
		this.merchantId = merchantId;
		this.shopzoneId = shopzoneId;
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
	public String getUserId() {
		return userId;
	}


	/**
	 * @param userId the user_id to set
	 */
	public void setUseId(String userId) {
		this.userId = userId;
	}


	/**
	 * @return the merchant_id
	 */
	public String getMerchantId() {
		return merchantId;
	}


	/**
	 * @param merchantId the merchant_id to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}


	/**
	 * @return the shopzone_id
	 */
	public String getShopzoneId() {
		return shopzoneId;
	}


	/**
	 * @param shopzoneId the shopzone_id to set
	 */
	public void setShopzoneId(String shopzoneId) {
		this.shopzoneId = shopzoneId;
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
