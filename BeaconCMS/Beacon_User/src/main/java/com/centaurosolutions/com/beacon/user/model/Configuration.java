package com.centaurosolutions.com.beacon.user.model;

import org.springframework.data.annotation.Id;

public class Configuration {
	@Id 
	private String id;
	
	private String maxRecordsSQLite;
	
	private String timeScan;

	public Configuration(){}
	
	public Configuration(String maxRecordsSQLite, String timeScan){
		super();
		this.maxRecordsSQLite = maxRecordsSQLite;
		this.timeScan = timeScan;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaxRecordsSQLite() {
		return maxRecordsSQLite;
	}

	public void setMaxRecordsSQLite(String maxRecordsSQLite) {
		maxRecordsSQLite = maxRecordsSQLite;
	}

	public String getTimeScan() {
		return timeScan;
	}

	public void setTimeScan(String timeScan) {
		timeScan = timeScan;
	}
	

}
