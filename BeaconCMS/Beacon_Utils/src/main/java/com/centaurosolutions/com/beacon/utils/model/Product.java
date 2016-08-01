package com.centaurosolutions.com.beacon.utils.model;

import java.util.ArrayList;

public class Product 
{
	private String id;
	private String productName;
	private float price;
	private ArrayList<String> imageUrlList;
	private String details;
	
	public Product(){}
	
	/**
	 * @param productname
	 * @param price
	 * @param imageurlList
	 * @param details
	 */
	public Product(
			String productname, 
			float price, 
			ArrayList<String> imageurlList, 
			String details)
	{
		this.productName = productname;
		this.price = price;
		this.imageUrlList = imageurlList;
		this.details = details;
	}
	
	
	/**
	 * @return productid
	 */
	public String getId()
	{
		return this.id;
	}
	
	/**
	 *  @param id
	 */
	public void setId (String id)
	{
		this.id = id;
	}
	
	/**
	 * @return productName
	 */
	public String getProductName()
	{
		return this.productName;
	}
	
	/**
	 * @param productname
	 */
	public void setProductName (String productname)
	{
		this.productName = productname;
	}
	
	/**
	 * @return price
	 */
	public float getPrice()
	{
		return this.price;
	}
	
	/**
	 * @param price
	 */
	public void setPrice(float price)
	{
		this.price = price;
	}
	
	/**
	 * @return imageUrlList
	 */
	public ArrayList<String> getImageUrlList()
	{
		return this.imageUrlList;
	}
	
	/**
	 * @param imageUrlList
	 */
	public void setImageUrlList(ArrayList<String> imageUrlList)
	{
		this.imageUrlList = imageUrlList;
	}
	
	/**
	 * @return details
	 */
	public String get()
	{
		return this.details;
	}
	
	/**
	 * @param details
	 */
	public void set (String details)
	{
		this.details = details;
	}
}
