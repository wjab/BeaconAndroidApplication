package com.centaurosolutions.com.beacon.merchantprofile.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class MerchantInvoiceHistory 
{
	@Id
	public String id;
	private String invoiceId;
	private float invoiceAmount;
	private String securityCode;
	private String paymentType;
	private float usedPoints;
	private Date creationDate; 
	
	public void setId(String id) { this.id = id; }
	public String getId() { return id; }
	
	public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }
	public String getInvoiceId() { return invoiceId; }
	
	public void setInvoiceAmount(float invoiceAmount) { this.invoiceAmount = invoiceAmount; }
	public float getInvoiceAmount(){ return this.invoiceAmount; }
		
	public void setSecurityCode(String securityCode) { this.securityCode = securityCode; }
	public String getSecurityCode() { return securityCode; }
	
	public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
	public String getPaymentType() { return paymentType; }
	
	public void setUsedPoints(float usedPoints) { this.usedPoints = usedPoints; }
	public float getUsedPoints() { return usedPoints; }
	
	public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
	public Date getCreationDate() { return creationDate; }
	
	
}
