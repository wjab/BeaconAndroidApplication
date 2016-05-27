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
	private String user_id;
	private String promo_id;
	private String merchant_id;
	private String shopZone_id;
	private Date scanDate;
	private Date creationDate;
	private Date modifiedDate;
	
	
	public OfferHistory(){
		
	}



	/**
	 * @param user_id
	 * @param merchant_id
	 * @param shopZone_id
	 * @param scanDate
	 * @param creationDate
	 * @param modifiedDate
	 */
	public OfferHistory(String user_id, String promo_id, String merchant_id, String shopZone_id, Date scanDate, Date creationDate,
			Date modifiedDate) {
		super();
		this.user_id = user_id;
		this.promo_id = promo_id;
		this.merchant_id = merchant_id;
		this.shopZone_id = shopZone_id;
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
		return user_id;
	}


	/**
	 * @param user_id the user_id to set
	 */
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}


	/**
	 * @return the merchant_id
	 */
	public String getMerchantId() {
		return merchant_id;
	}


	/**
	 * @param merchant_id the merchant_id to set
	 */
	public void setMerchantId(String merchant_id) {
		this.merchant_id = merchant_id;
	}


	/**
	 * @return the shopZone_id
	 */
	public String getShopZoneId() {
		return shopZone_id;
	}


	/**
	 * @param shopZone_id the shopZone_id to set
	 */
	public void setShopZoneId(String shopZone_id) {
		this.shopZone_id = shopZone_id;
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
	 * @return the promo_id
	 */
	public String getPromoId() {
		return promo_id;
	}



	/**
	 * @param promo_id the promo_id to set
	 */
	public void setPromoId(String promo_id) {
		this.promo_id = promo_id;
	}

	


	

}
