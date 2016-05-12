package com.centaurosolutions.com.beacon.user.model;

import org.springframework.data.annotation.Id;

public class Configuration {
	@Id 
	private String id;
	
	private String MaxReccordsSQLite;
	
	private String TimeScan;

	public Configuration(){}
	
	public Configuration(String MaxReccordsSQLite, String TimeScan){
		super();
		this.MaxReccordsSQLite = MaxReccordsSQLite;
		this.TimeScan = TimeScan;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaxReccordsSQLite() {
		return MaxReccordsSQLite;
	}

	public void setMaxReccordsSQLite(String maxReccordsSQLite) {
		MaxReccordsSQLite = maxReccordsSQLite;
	}

	public String getTimeScan() {
		return TimeScan;
	}

	public void setTimeScan(String timeScan) {
		TimeScan = timeScan;
	}
	

}
