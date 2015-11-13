package com.centaurosolutions.com.beacon.device.controller;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	public Map<String, Object> createDevice(@RequestBody Map<String, Object> deviceMap){
		

		ArrayList<Range> ranges = new ArrayList<Range>();
		
		if(deviceMap.get("ranges") != null){
			
			ranges = (ArrayList<Range>) deviceMap.get("ranges");
			
		}

		Device deviceModel = new Device( ranges,(Boolean)deviceMap.get("enable"),Integer.parseInt(deviceMap.get("txPower").toString()),DateFormatter(deviceMap.get("creationDate").toString()), DateFormatter(deviceMap.get("modifiedDate").toString()),deviceMap.get("updatedBy").toString(),deviceMap.get("proximityUUID").toString(), deviceMap.get("uniqueID").toString(), deviceMap.get("masterPassword").toString(), deviceMap.get("devicePassword").toString());    
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Dispositivo creado correctamente");
	    response.put("device", deviceModel); 
		
	    deviceRepository.save(deviceModel);
		return response;
	}
	
	  @RequestMapping(method = RequestMethod.GET, value="/{DeviceId}")
	  public Device getDeviceDetails(@PathVariable("DeviceId") String deviceId){
		  return deviceRepository.findOne(deviceId);
	  }
	  
	  @RequestMapping(method = RequestMethod.GET, value="/UID/{DeviceId}")
	  public Device getDeviceDetailsByUniqueId(@PathVariable("DeviceId") String deviceId){
		  return deviceRepository.findByUniqueID(deviceId);
	  }
	  
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getAllDeviceDetails(){
		List<Device> deviceModelList = deviceRepository.findAll();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("Total de Dispositivos", deviceModelList.size());
		response.put("Dispositivo", deviceModelList);
		return response;
	}
	  
	  
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.PUT, value="/{DeviceId}")
	public Map<String, Object> editDevice(@PathVariable("DeviceId") String DeviceId,
	      @RequestBody Map<String, Object> deviceMap){
		  
		ArrayList<Range> ranges = new ArrayList<Range>();
		
		if(deviceMap.get("ranges") != null)
		{			
			ranges = (ArrayList<Range>) deviceMap.get("ranges");			
		}
		  
		Device deviceModel = new Device( ranges,(Boolean)deviceMap.get("enable"),Integer.parseInt(deviceMap.get("txPower").toString()),DateFormatter(deviceMap.get("creationDate").toString()), DateFormatter(deviceMap.get("modifiedDate").toString()),deviceMap.get("updatedBy").toString(),deviceMap.get("proximityUUID").toString(), deviceMap.get("uniqueID").toString(), deviceMap.get("masterPassword").toString(), deviceMap.get("devicePassword").toString());
		deviceModel.setId(DeviceId);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Dispositivo actualizado correctamente");
	    response.put("Dispositivo", deviceRepository.save(deviceModel));
	    return response;
	}
	  
	  
	@RequestMapping(method = RequestMethod.DELETE, value="/{DeviceId}")
	public Map<String, String> deleteDevice(@PathVariable("DeviceId") String deviceId)
	{
	    deviceRepository.delete(deviceId);
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "Dispositivo eliminado correctamente");

	    return response;
	}
	
	  
	private Date DateFormatter(String pDate){
			
		Date finalDate = new Date();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS", Locale.ENGLISH);
		try {
			finalDate = format.parse(pDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return finalDate;		
	}

}
