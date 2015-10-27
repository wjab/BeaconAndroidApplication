package com.centaurosolutions.com.beacon.merchantproduct.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class MerchantProduct 
{
	@Id
	private String shopZoneId;
	private String merchantId;
	private ArrayList<Product> productList;
	
	public MerchantProduct(){}
	
	/**
	 * Constructor
	 * @param merchantid
	 * @param shopzoneid
	 * @param productlist
	 */
	public MerchantProduct(String merchantid, String shopzoneid, ArrayList<Product> productlist)
	{
		this.merchantId = merchantid;
		this.shopZoneId = shopzoneid;
		this.productList = productlist;
	}
	
	/**
	 * @return shopZoneId
	 */
	public String getMerchantId()
	{
		return this.merchantId;
	}
	
	/**
	 *  @param shopzoneid
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
