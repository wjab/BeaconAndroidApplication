/**
 * 
 */
package com.centaurosolutions.com.beacon.utils.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * @author Eduardo
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferHistory {
	

	private String id;
	private String userId;
	private String promoId;
	private String merchantId;
	private String shopZoneId;
	private Date scanDate;
	private Date creationDate;
	private Date modifiedDate;
	
	
	public OfferHistory(){
		
	}



	/**
	 * @param userId
	 * @param merchantId
	 * @param shopZoneId
	 * @param scanDate
	 * @param creationDate
	 * @param modifiedDate
	 */
	public OfferHistory(String userId, String promoId, String merchantId, String shopZoneId, Date scanDate, Date creationDate,
			Date modifiedDate) {
		super();
		this.userId = userId;
		this.promoId = promoId;
		this.merchantId = merchantId;
		this.shopZoneId = shopZoneId;
		this.scanDate = scanDate;
		this.creationDate = creationDate;
		this.modifiedDate = modifiedDate;
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
	public void setUserId(String userId) {
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
	 * @return the shopZone_id
	 */
	public String getShopZoneId() {
		return shopZoneId;
	}


	/**
	 * @param shopZoneId the shopZone_id to set
	 */
	public void setShopZoneId(String shopZoneId) {
		this.shopZoneId = shopZoneId;
	}


	/**
	 * @return the scanDate
	 */
	public Date getScanDate() {
		return scanDate;
	}


	/**
	 * @param scanDate the scanDate to set
	 */
	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
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
	 * @return the promoId
	 */
	public String getPromoId() {
		return promoId;
	}



	/**
	 * @param promoId the promo_id to set
	 */
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}

	


	

}
