package com.centaurosolutions.com.beacon.device.controller;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centaurosolutions.com.beacon.device.model.Device;
import com.centaurosolutions.com.beacon.device.model.Range;
import com.centaurosolutions.com.beacon.device.repository.DeviceRepository;

import java.util.ArrayList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/device")
public class DeviceController {
	
	@Autowired
	private DeviceRepository deviceRepository;
	

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createDevice(@RequestBody Map<String, Object> deviceMap)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			Device device_Finded=deviceRepository.findByUniqueID(deviceMap.get("uniqueID").toString());
			
			if(device_Finded == null)
			{
				ArrayList<Range> ranges = new ArrayList<Range>();
				
				if(deviceMap.get("ranges") != null)
				{
					ranges = (ArrayList<Range>) deviceMap.get("ranges");
				}
				
				Device deviceModel = new Device( ranges,
						Boolean.valueOf(deviceMap.get("enable").toString()),
						Integer.parseInt(deviceMap.get("txPower").toString()),
						DateFormatter(deviceMap.get("creationDate").toString()),
						DateFormatter(deviceMap.get("modifiedDate").toString()),
						deviceMap.get("updatedBy").toString(),
						deviceMap.get("proximityUUID").toString(),
						deviceMap.get("uniqueID").toString(),
						deviceMap.get("masterPassword").toString(),
						deviceMap.get("devicePassword").toString());    

				deviceRepository.save(deviceModel);
				response.put("message", "Dispositivo creado correctamente");
				response.put("device", deviceModel);
				response.put("status", "200");
				
			}
			else
			{
				response.put("message", "Dispositivo con el Id "+deviceMap.get("uniqueID").toString()+"ya existe");
				response.put("device", null);
				response.put("status", "200");
			}
		}
		catch(Exception ex){
			response.put("message", ex.getMessage());
			response.put("device", null);
			response.put("status", "400");
		}
	    return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{DeviceId}")
	public Map<String, Object> getDeviceDetails(@PathVariable("DeviceId") String deviceId)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			Device device_Finded=deviceRepository.findOne(deviceId);
			if(device_Finded != null)
			{
				response.put("message", "Dispositivo encontrado");
				response.put("device", device_Finded);
				response.put("status", "200");
			}
			else
			{
				response.put("message", "Dispositivo no encontrado");
				response.put("device", null);
				response.put("status", "401");
			}
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
			response.put("device", null);
			response.put("status", "400");
		}
	    return response;
	}
	  
	  @RequestMapping(method = RequestMethod.GET, value="/UID/{DeviceId}")
	  public Map<String, Object> getDeviceDetailsByUniqueId(@PathVariable("DeviceId") String deviceId)
	  {
			Map<String, Object> response = new LinkedHashMap<String, Object>();
			
			try
			{
				Device device_Finded=deviceRepository.findByUniqueID(deviceId);
				if(device_Finded != null)
				{
					response.put("message", "Dispositivo encontrado");
					response.put("device", device_Finded);
					response.put("status", "200");
				}
				else
				{
					response.put("message", "Dispositivo no encontrado");
					response.put("device", null);
					response.put("status", "401");
				}
			}
			catch(Exception ex)
			{
				response.put("message", ex.getMessage());
				response.put("device", null);
				response.put("status", "400");
			}
			
		    return response;
	  }
	  
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getAllDeviceDetails()
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			List<Device> deviceModelList = deviceRepository.findAll();
			response.put("Total de Dispositivos", deviceModelList.size());
			response.put("Dispositivo", deviceModelList);
			response.put("status", "200");
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
			response.put("device", null);
			response.put("status", "400");
		}
		return response;
	}
	  
	  
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.PUT, value="/{DeviceId}")
	public Map<String, Object> editDevice(@PathVariable("DeviceId") String DeviceId,
	      @RequestBody Map<String, Object> deviceMap)
	{
		  
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			Device device_Finded=deviceRepository.findOne(DeviceId);
			
			if(device_Finded != null)
			{
				ArrayList<Range> ranges = new ArrayList<Range>();
				
				if(deviceMap.get("ranges") != null)
				{			
					ranges = (ArrayList<Range>) deviceMap.get("ranges");			
				}
				
				Device deviceModel = new Device( ranges,
						Boolean.valueOf(deviceMap.get("enable").toString()),
						Integer.parseInt(deviceMap.get("txPower").toString()),
						DateFormatter(deviceMap.get("creationDate").toString()),
						DateFormatter(deviceMap.get("modifiedDate").toString()),
						deviceMap.get("updatedBy").toString(),
						deviceMap.get("proximityUUID").toString(),
						deviceMap.get("uniqueID").toString(),
						deviceMap.get("masterPassword").toString(),
						deviceMap.get("devicePassword").toString());
				
				deviceModel.setId(DeviceId);
				response.put("message", "Dispositivo actualizado correctamente");
				response.put("Dispositivo", deviceRepository.save(deviceModel));
				response.put("status", "200");
			}
			else
			{
				response.put("message", "Dispositivo no existe");
				response.put("Dispositivo", null);
				response.put("status", "200");
			}
		}
		catch(Exception ex){
			response.put("message", ex.getMessage());
			response.put("device", null);
			response.put("status", "400");
		}
		return response;
	} 
	  
	  
	@RequestMapping(method = RequestMethod.DELETE, value="/{DeviceId}")
	public Map<String, String> deleteDevice(@PathVariable("DeviceId") String deviceId)
	{
		Map<String, String> response = new HashMap<String, String>();
		
		try
		{
		    deviceRepository.delete(deviceId);
		    response.put("message", "Dispositivo eliminado correctamente");
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
			response.put("device", null);
			response.put("status", "400");
		}
		return response;
	}
	
	  
	private Date DateFormatter(String pDate)
	{			
		Date finalDate = new Date();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		try 
		{
			finalDate = format.parse(pDate);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return finalDate;		
	}

}
