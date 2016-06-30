/**
 * 
 */
package com.centaurosolutions.com.beacon.utils.model;

/**
 * @author Eduardo
 *
 */
public class MerchantContactData {
	
	private String contactType;
	private String contactData;
	
	/**
	 * 
	 */
	public MerchantContactData() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param type
	 * @param contactData
	 */
	public MerchantContactData(String contactType, String contactData) {
		super();
		this.contactType = contactType;
		this.contactData = contactData;
	}



	/**
	 * @return the type
	 */
	public String getContactType() {
		return contactType;
	}



	/**
	 * @param type the type to set
	 */
	public void setType(String contactType) {
		this.contactType = contactType;
	}



	/**
	 * @return the contactData
	 */
	public String getContactData() {
		return contactData;
	}



	/**
	 * @param contactData the contactData to set
	 */
	public void setContactData(String contactData) {
		this.contactData = contactData;
	}
	
	

}
