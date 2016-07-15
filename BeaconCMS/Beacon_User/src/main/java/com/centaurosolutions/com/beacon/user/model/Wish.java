package com.centaurosolutions.com.beacon.user.model;

public class Wish {
	String productId;
	String productName;
	int price;
	String imageUrlList;
	

	public Wish() {
	}


	public Wish(String productId,String productName,int price,String imageUrlList) {
		super();
		this.productId=productId;
		this.productName=productName;
		this.price=price;
		this.imageUrlList=imageUrlList;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public String getImageUrlList() {
		return imageUrlList;
	}


	public void setImageUrlList(String imageUrlList) {
		this.imageUrlList = imageUrlList;
	}



	}
