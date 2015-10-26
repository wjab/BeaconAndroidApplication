/**
 * 
 */
package com.centaurosolutions.com.beacon.device.model;

import java.util.ArrayList;

/**
 * @author Eduardo
 *
 */
public class Range {
	


	private String type;
	private String message;
	private String messageType;
	private int notificationFrequency;
	private int notificationPeriodicity;


	/**
	 * 
	 */
	public Range() {
		// TODO Auto-generated constructor stub
	}
	
	public Range(String type, String message, String messageType, int notificationFrequency,
			int notificationPeriodicity) {
		super();
		this.type = type;
		this.message = message;
		this.messageType = messageType;
		this.notificationFrequency = notificationFrequency;
		this.notificationPeriodicity = notificationPeriodicity;
	}

	
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public int getNotificationFrequency() {
		return notificationFrequency;
	}

	public void setNotificationFrequency(int notificationFrequency) {
		this.notificationFrequency = notificationFrequency;
	}

	public int getNotificationPeriodicity() {
		return notificationPeriodicity;
	}

	public void setNotificationPeriodicity(int notificationPeriodicity) {
		this.notificationPeriodicity = notificationPeriodicity;
	}
}
