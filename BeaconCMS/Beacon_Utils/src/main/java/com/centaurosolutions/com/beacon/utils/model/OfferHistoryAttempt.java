package com.centaurosolutions.com.beacon.utils.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferHistoryAttempt {

	private String userId;
	private String promoId;
	private int attempts;
	private Date lastScan;

	
	
	public OfferHistoryAttempt(){
		
	}


	/**
	 * @param userId
	 * @param promoId
	 * @param attempts
	 */
	public OfferHistoryAttempt(String userId, String promoId, int attempts) {
		super();
		this.userId = userId;
		this.promoId = promoId;
		this.attempts = attempts;
	}



	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}



	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}



	/**
	 * @return the promoId
	 */
	public String getPromoId() {
		return promoId;
	}



	/**
	 * @param promoId the promoId to set
	 */
	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}



	/**
	 * @return the attempts
	 */
	public int getAttempts() {
		return attempts;
	}



	/**
	 * @param attempts the attempts to set
	 */
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}


	/**
	 * @return the lastScan
	 */
	public Date getLastScan() {
		return lastScan;
	}


	/**
	 * @param lastScan the lastScan to set
	 */
	public void setLastScan(Date lastScan) {
		this.lastScan = lastScan;
	}
	
	
	
	


}