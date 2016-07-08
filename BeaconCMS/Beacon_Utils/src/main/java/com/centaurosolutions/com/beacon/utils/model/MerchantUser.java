/**
 * 
 */
package com.centaurosolutions.com.beacon.utils.model;

/**
 * @author Eduardo
 *
 */
public class MerchantUser {
	
	/**
	 * 
	 */
	
	private String user;
	private String password;
	private String shopZoneId;
	
	public MerchantUser() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param user
	 * @param password
	 * @param shopZoneId
	 */
	public MerchantUser(String user, String password, String shopZoneId) {
		super();
		this.user = user;
		this.password = password;
		this.shopZoneId = shopZoneId;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the shopZone_id
	 */
	public String getShopZoneId() {
		return shopZoneId;
	}

	/**
	 * @param shopZoneId the shopZone_id to set
	 */
	public void setShopZoneId(String shopZoneId) {
		this.shopZoneId = shopZoneId;
	}
	
	

}
