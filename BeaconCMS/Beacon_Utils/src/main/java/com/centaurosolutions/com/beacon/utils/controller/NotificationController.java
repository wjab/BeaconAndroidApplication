package com.centaurosolutions.com.beacon.utils.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centaurosolutions.com.beacon.utils.repository.NotificationRepository;

@RestController
@RequestMapping("/notification")
public class NotificationController 
{
	@Autowired 
	private NotificationRepository notificationRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{userId}" )
	public Map<String, Object> Get()
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			
		}
		catch(Exception ex)
		{
			
		}
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "all/{userId}" )
	public Map<String, Object> GetAll()
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			
		}
		catch(Exception ex)
		{
			
		}
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> Add()
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			
		}
		catch(Exception ex)
		{
			
		}
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Map<String, Object> Update()
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			
		}
		catch(Exception ex)
		{
			
		}
		
		return response;
	}	
}
