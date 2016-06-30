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
	private String shopZone_id;
	
	public MerchantUser() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param user
	 * @param password
	 * @param shopZone_id
	 */
	public MerchantUser(String user, String password, String shopZone_id) {
		super();
		this.user = user;
		this.password = password;
		this.shopZone_id = shopZone_id;
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
	public String getShopZone_id() {
		return shopZone_id;
	}

	/**
	 * @param shopZone_id the shopZone_id to set
	 */
	public void setShopZone_id(String shopZone_id) {
		this.shopZone_id = shopZone_id;
	}
	
	

}
