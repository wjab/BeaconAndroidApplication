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
	
	/**
	 * @param id
	 * @param ranges
	 * @param enable
	 * @param txPower
	 * @param creationDate
	 * @param modifiedDate
	 * @param updatedBy
	 */
	public Device( ArrayList<Range> ranges, boolean enable, int txPower, Date creationDate,
			Date modifiedDate, String updatedBy) {
		super();
		this.ranges = ranges;
		this.enable = enable;
		this.txPower = txPower;
		this.creationDate = creationDate;
		this.modifiedDate = modifiedDate;
		this.updatedBy = updatedBy;
	}
	
	public Device(){
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
	public boolean isEnable() {
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
