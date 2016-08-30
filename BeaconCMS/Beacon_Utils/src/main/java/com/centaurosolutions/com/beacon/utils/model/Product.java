package com.centaurosolutions.com.beacon.utils.model;

import java.util.ArrayList;

public class Product 
{
	private String productId;
	private String productName;
	private float price;
	private ArrayList<String> imageUrlList;
	private String details;
	private String code;
	private Boolean allowScan;
	private int pointsByScan;
	private int pointsByPrice;

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
			String details, String code, Boolean allowScan, int pointsByScan, int pointsByPrice)
	{
		this.productName = productname;
		this.price = price;
		this.imageUrlList = imageurlList;
		this.details = details;
		this.code = code;
		this.allowScan = allowScan;
		this.pointsByScan = pointsByScan;
		this.pointsByPrice = pointsByPrice;
	}


	/**
	 * @return productid
	 */
	public String getProductId()
	{
		return this.productId;
	}

	/**
	 *  @param id
	 */
	public void setProductId (String productId)
	{
		this.productId = productId;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getAllowScan() {
		return allowScan;
	}

	public void setAllowScan(Boolean allowScan) {
		this.allowScan = allowScan;
	}

	public int getPointsByScan() {
		return pointsByScan;
	}

	public void setPointsByScan(int pointsByScan) {
		this.pointsByScan = pointsByScan;
	}
	public int getPointsByPrice() {
		return pointsByPrice;
	}

	public void setPointsByPrice(int pointsByPrice) {
		this.pointsByPrice = pointsByPrice;
	}

	
}
