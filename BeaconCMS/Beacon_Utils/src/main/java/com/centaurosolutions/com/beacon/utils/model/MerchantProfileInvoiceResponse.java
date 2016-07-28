package com.centaurosolutions.com.beacon.utils.model;

/**
 * Created by Eduardo on 28/7/2016.
 */
public class MerchantProfileInvoiceResponse {

    private int status;
    private String message;
    private Object invoiceHistory;

    public MerchantProfileInvoiceResponse(){

    }

    public MerchantProfileInvoiceResponse(int status, String message, Object invoiceHistory) {
        this.status = status;
        this.message = message;
        this.invoiceHistory = invoiceHistory;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getInvoiceHistory() {
        return invoiceHistory;
    }

    public void setInvoiceHistory(Object invoiceHistory) {
        this.invoiceHistory = invoiceHistory;
    }
}
