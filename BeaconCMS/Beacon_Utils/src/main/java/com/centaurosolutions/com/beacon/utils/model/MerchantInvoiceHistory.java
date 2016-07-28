package com.centaurosolutions.com.beacon.utils.model;

import java.util.Date;

/**
 * Created by Eduardo on 28/7/2016.
 */
public class MerchantInvoiceHistory
{
    public String id;
    private String merchantId;
    private String invoiceId;
    private float invoiceAmount;
    private String securityCode;
    private String paymentType;
    private float usedPoints;
    private Date creationDate;
    private String userId;

    public MerchantInvoiceHistory(String userId, String merchantId, String invoiceId, float invoiceAmount,
                                  String securityCode, String paymentType, float usedPoints, Date creationDate)
    {
        this.setMerchantId(merchantId);
        this.setInvoiceId(invoiceId);
        this.setInvoiceAmount(invoiceAmount);
        this.setSecurityCode(securityCode);
        this.setPaymentType(paymentType);
        this.setUsedPoints(usedPoints);
        this.setCreationDate(creationDate);
        this.setUserId(userId);
    }

    public MerchantInvoiceHistory(){

    }

    public void setId(String id) { this.id = id; }
    public String getId() { return id; }

    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    public String getMerchantId() { return merchantId; }

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


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}