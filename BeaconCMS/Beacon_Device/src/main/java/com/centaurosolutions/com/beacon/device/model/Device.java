package com.centaurosolutions.com.beacon.device.model;
import org.springframework.data.annotation.Id;
import java.util.Date;

import java.util.ArrayList;

public class Device {
	
	@Id
	private String id;
	private ArrayList<Range> ranges;
	private boolean enable;
	private int txPower;
	private Date creationDate;
	private Date modifiedDate;
	private String updatedBy;
	private String proximityUUID;
	private String uniqueID;
	private String MasterPassword;
	private String DevicePassword;
	


	/**
	 * @param ranges
	 * @param enable
	 * @param txPower
	 * @param creationDate
	 * @param modifiedDate
	 * @param updatedBy
	 * @param proximityUUID
	 * @param uniqueID
	 */
	public Device(ArrayList<Range> ranges, boolean enable, int txPower, Date creationDate, Date modifiedDate,
			String updatedBy, String proximityUUID, String uniqueID, String MasterPassword,String DevicePassword) {
		super();
		this.ranges = ranges;
		this.enable = enable;
		this.txPower = txPower;
		this.creationDate = creationDate;
		this.modifiedDate = modifiedDate;
		this.updatedBy = updatedBy;
		this.proximityUUID = proximityUUID;
		this.uniqueID = uniqueID;
		this.DevicePassword = DevicePassword;
		this.MasterPassword = MasterPassword;
	}




	public Device(){
	}
	
	/**
	 * @return the masterPassword
	 */
	public String getMasterPassword() {
		return MasterPassword;
	}

	/**
	 * @param masterPassword the masterPassword to set
	 */
	public void setMasterPassword(String masterPassword) {
		MasterPassword = masterPassword;
	}

	/**
	 * @return the devicePassword
	 */
	public String getDevicePassword() {
		return DevicePassword;
	}


	/**
	 * @param devicePassword the devicePassword to set
	 */
	public void setDevicePassword(String devicePassword) {
		DevicePassword = devicePassword;
	}




	/**
	 * @return the proximityUUID
	 */
	public String getProximityUUID() {
		return proximityUUID;
	}



	/**
	 * @param proximityUUID the proximityUUID to set
	 */
	public void setProximityUUID(String proximityUUID) {
		this.proximityUUID = proximityUUID;
	}



	/**
	 * @return the uniqueID
	 */
	public String getUniqueID() {
		return uniqueID;
	}



	/**
	 * @param uniqueID the uniqueID to set
	 */
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}



	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the ranges
	 */
	public ArrayList<Range> getRanges() {
		return ranges;
	}

	/**
	 * @param ranges the ranges to set
	 */
	public void setRanges(ArrayList<Range> ranges) {
		this.ranges = ranges;
	}

	/**
	 * @return the enable
	 */
	public boolean getEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return the txPower
	 */
	public int getTxPower() {
		return txPower;
	}

	/**
	 * @param txPower the txPower to set
	 */
	public void setTxPower(int txPower) {
		this.txPower = txPower;
	}

	/**
	 * @return the creatioDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creatioDate the creatioDate to set
	 */
	public void setCreatioDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	

}
