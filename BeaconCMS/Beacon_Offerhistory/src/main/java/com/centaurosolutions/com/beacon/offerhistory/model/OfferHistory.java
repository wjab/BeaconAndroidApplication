package com.centaurosolutions.com.beacon.offerhistory.model;
import java.util.Date;

import org.springframework.data.annotation.Id;

public class OfferHistory {
	
	@Id
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
	 * @return the shopZone_id
	 */
	public String getShopZone_id() {
		return shopZone_id;
	}


	/**
	 * @param shopZone_id the shopZone_id to set
	 */
	public void setShopZone_id(String shopZone_id) {
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
	public String getPromo_id() {
		return promo_id;
	}



	/**
	 * @param promo_id the promo_id to set
	 */
	public void setPromo_id(String promo_id) {
		this.promo_id = promo_id;
	}

	


	

}
