package com.centaurosolutions.com.beacon.merchantprofile.model;

import org.springframework.data.annotation.Id;

public class MerchantBusinessType 
{
	@Id
	private String id;
	private String type;	
	private String description;
	private String imageUrl;
	
	public MerchantBusinessType(String type, String description, String imageUrl) 
	{
		this.type = type;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
