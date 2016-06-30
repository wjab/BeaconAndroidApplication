package com.centaurosolutions.com.beacon.utils.model;

import java.util.ArrayList;

public class MerchantProduct 
{
	private String id;
	private String shopZoneId;
	private String merchantId;
	private String latitude;
	private String longitude;
	private ArrayList<Product> productList;
	
	public MerchantProduct(){}
	
	/**
	 * Constructor
	 * @param merchantid
	 * @param shopzoneid
	 * @param productlist
	 */
	public MerchantProduct(String merchantid, String shopzoneid, ArrayList<Product> productlist,String longitude,String latitude)
	{
		this.merchantId = merchantid;
		this.shopZoneId = shopzoneid;
		this.productList = productlist;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return id
	 */
	public String getId()
	{
		return this.id;
	}
	
	/**
	 *  @param id
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	
	/**
	 * @return shopZoneId
	 */
	public String getMerchantId()
	{
		return this.merchantId;
	}
	
	/**
	 *  @param merchantid
	 */
	public void setMerchantId(String merchantid)
	{
		this.merchantId = merchantid;
	}
		
		
	/**
	 * @return shopZoneId
	 */
	public String getShopZoneId()
	{
		return this.shopZoneId;
	}
	
	/**
	 *  @param shopzoneid
	 */
	public void setShopZoneId(String shopzoneid)
	{
		this.shopZoneId = shopzoneid;
	}
	
	/**
	 * @return productList
	 */
	public ArrayList<Product> getProductList()
	{
		return this.productList;
	}
	
	/**
	 *  @param productlist
	 */
	public void setProductList(ArrayList<Product> productlist)
	{
		this.productList = productlist;
	}
}
