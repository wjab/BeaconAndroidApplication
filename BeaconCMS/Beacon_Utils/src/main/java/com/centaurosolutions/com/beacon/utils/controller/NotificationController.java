package com.centaurosolutions.com.beacon.utils.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centaurosolutions.com.beacon.utils.model.Notification;
import com.centaurosolutions.com.beacon.utils.repository.NotificationRepository;

@RestController
@RequestMapping("/notification")
public class NotificationController 
{
	@Autowired 
	private NotificationRepository notificationRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/userId/{userId}" )
	public Map<String, Object> Get(@PathVariable("userId") String userId)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			ArrayList<Notification> notificationList = notificationRepository.findAllByUserIdAndRead(userId, false);
			
			response.put("message", "Notificaciones sin leer"); 
			response.put("notificationResult", notificationList); 
			response.put("status", "200");
		}
		catch(Exception ex)
		{
			response.put("message", "Error al obtener otificaciones"); 
			response.put("notificationResult", null); 
			response.put("status", "500");
		}
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "all/{userId}" )
	public Map<String, Object> GetAll(@PathVariable("userId") String userId)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			ArrayList<Notification> notificationList = notificationRepository.findAllByUserId(userId);
			
			response.put("message", "Todas las Notificaciones"); 
			response.put("notificationResult", notificationList); 
			response.put("status", "200");
		}
		catch(Exception ex)
		{
			response.put("message", "Error al obtener  notificaciones"); 
			response.put("notificationResult", null); 
			response.put("status", "500");
		}
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> Add(@RequestBody Map<String, Object> notificationMap)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			Notification notification = CreateNotification(
					notificationMap.get("userId").toString(),
					notificationMap.get("message").toString(), 
					notificationMap.get("type").toString()
					);
			
			response.put("message", "Notificación guardada correctamente"); 
			response.put("notificationResult", notification); 
			response.put("status", "200");
		}
		catch(Exception ex)
		{
			response.put("message", "Error al agregar notificación"); 
			response.put("notificationResult", null); 
			response.put("status", "500");
		}
		
		return response;
	}

	public Notification CreateNotification(String userId, String message, String type) 
	{
		Notification notification = new Notification(
				userId,
				message,
				type,
				false, 
				new Date(), 
				null);
		notificationRepository.save(notification);
		return notification;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{notificationId}" )
	public Map<String, Object> Update(@PathVariable("notificationId") String notificationId)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			Notification notification = notificationRepository.findOne(notificationId);
			
			if(notification != null)
			{
				notification.setRead(true);
				notification.setReadDate(new Date());
				notificationRepository.save(notification);
				
				response.put("message", "Notificación actualizada correctamente"); 
				response.put("notificationResult", notification); 
				response.put("status", "201");
			}			
			else
			{
				response.put("message", "Notificación no encontrada"); 
				response.put("notificationResult", null); 
				response.put("status", "404");
			}			
		}
		catch(Exception ex)
		{
			response.put("message", "Error al actualizar notificación"); 
			response.put("notificationResult", null); 
			response.put("status", "500");
		}
		
		return response;
	}	
}
