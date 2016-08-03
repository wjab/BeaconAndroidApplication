package com.centaurosolutions.com.beacon.merchantprofile.model;

public class ExtendedProduct extends Product 
{
	private String merchantId;
	private String businessTypeId;
	private String departmentId;	
	
	public ExtendedProduct(){}
	
	public ExtendedProduct(String merchantId, String businessTypeId, String departmentId) 
	{
		this.merchantId = merchantId;
		this.businessTypeId = businessTypeId;
		this.departmentId = departmentId;
	}
	
	
	
}
