package com.centaurosolutions.com.beacon.offerhistory.model;
import java.util.Date;

import org.springframework.data.annotation.Id;

public class OfferHistory 
{	
	@Id
	private String id;
	private String userId;
	private String promoId;
	private String merchantId;
	private String shopZoneId;
	private Date scanDate;
	private Date creationDate;
	private Date modifiedDate;
	
	
	public OfferHistory()
	{
		
	}

	/**
	 * @param user_id
	 * @param merchant_id
	 * @param shopZone_id
	 * @param scanDate
	 * @param creationDate
	 * @param modifiedDate
	 */
	public OfferHistory(String userId, String promoId, String merchantId, String shopZoneId, Date scanDate, Date creationDate,
			Date modifiedDate) 
	{
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
	public String getId() 
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) 
	{
		this.id = id;
	}

	/**
	 * @return the user_id
	 */
	public String getUserId() 
	{
		return userId;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	/**
	 * @return the merchant_id
	 */
	public String getMerchantId() 
	{
		return merchantId;
	}

	/**
	 * @param merchant_id the merchant_id to set
	 */
	public void setMerchantId(String merchantId) 
	{
		this.merchantId = merchantId;
	}

	/**
	 * @return the shopZone_id
	 */
	public String getShopZoneId() 
	{
		return shopZoneId;
	}

	/**
	 * @param shopZone_id the shopZone_id to set
	 */
	public void setShopZoneId(String shopZoneId) 
	{
		this.shopZoneId = shopZoneId;
	}

	/**
	 * @return the scanDate
	 */
	public Date getScanDate() 
	{
		return scanDate;
	}

	/**
	 * @param scanDate the scanDate to set
	 */
	public void setScanDate(Date scanDate) 
	{
		this.scanDate = scanDate;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() 
	{
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) 
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() 
	{
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) 
	{
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the promo_id
	 */
	public String getPromoId() 
	{
		return promoId;
	}

	/**
	 * @param promo_id the promo_id to set
	 */
	public void setPromoId(String promoId) 
	{
		this.promoId = promoId;
	}
}
