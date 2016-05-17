/**
 * 
 */
package com.centaurosolutions.com.beacon.mobile.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centaurosolutions.com.beacon.mobile.model.Mobile;
import com.centaurosolutions.com.beacon.mobile.repository.MobileRepository;

/**
 * @author Eduardo
 *
 */

@RestController
@RequestMapping("/mobile")
public class MobileController {
	
    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
	
	@Autowired
	private MobileRepository mobileRepository;
	

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createMobile(@RequestBody Map<String, Object> mobileMap){
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		try
		{
			Mobile mobileModel = new Mobile(mobileMap.get("deviceName").toString(),
										mobileMap.get("deviceModel").toString(),
										mobileMap.get("osName").toString(),
										mobileMap.get("osVersion").toString(),
										mobileMap.get("userId").toString(),
										DateFormatter(new Date().toString()));       
		
			response.put("message", "Dispositivo Móvil creado correctamente");
			response.put("mobile", mobileModel);
			response.put("status","200");						
			mobileRepository.save(mobileModel);	
		}
		catch(Exception ex){
			response.put("message", ex.getMessage());
			response.put("mobile", null);
			response.put("status","400");	
		}
		return response;
	}
	
	  @RequestMapping(method = RequestMethod.GET, value="/{MobileId}")
	  public Map<String, Object>  getMobileDetails(@PathVariable("MobileId") String MobileId){
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  try
		  {
			  Mobile mobile= mobileRepository.findOne(MobileId);
			  if(mobile!=null)
			  {
				  response.put("message",  "Dispositivo Móvil creado correctamente");
				  response.put("mobile", mobile);
				  response.put("status","200");	
			  }
			  else
			  {
				  response.put("message", "Dispositivo Móvil no encontrado");
				  response.put("mobile", null);
				  response.put("status","401");	 
			  }		  
		  }
		  catch(Exception ex)
		  {
			  response.put("message", ex.getMessage());
			  response.put("mobile", null);
			  response.put("status","400");	
		  }
		  return response;
	  }
	  
	  @RequestMapping(method = RequestMethod.GET, value="/user/{UserId}")
	  public Map<String, Object>  getMobileByUser(@PathVariable("UserId") String UserId){
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  try
		  {
			  List<Mobile> mobileModelList = mobileRepository.findByUserId(UserId);
			  response.put("Total de dispositivos móviles", mobileModelList.size());
			  response.put("MobileList", mobileModelList);
			  response.put("status","200");	
		  }
		  catch(Exception ex)
		  {
			  response.put("message", ex.getMessage());
			  response.put("mobile", null);
			  response.put("status","400");	
		  }
		  return response;
	  }
	  
	  @RequestMapping(method = RequestMethod.GET)
	  public Map<String, Object> getAllPromoDetails(){
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  try
		  {
			  List<Mobile> mobileModelList = mobileRepository.findAll();
			  response.put("Total de dispositivos móviles", mobileModelList.size());
			  response.put("MobileList", mobileModelList);
			  response.put("status","200");	
		  }
		  catch(Exception ex)
		  {
			  response.put("message", ex.getMessage());
			  response.put("mobile", null);
			  response.put("status","400");	
		  }
		  return response;
	}
	  
	   
	  @RequestMapping(method = RequestMethod.PUT, value="/{MobileId}")
	  public Map<String, Object> editMobile(@PathVariable("MobileId") String MobileId,
	      @RequestBody Map<String, Object> mobileMap){
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  try
		  {
			  Mobile mobile_finded=mobileRepository.findOne(MobileId);
			  if(mobile_finded!=null)
			  {
				  Mobile mobileModel = new Mobile(mobileMap.get("deviceName").toString(),
						  						  mobileMap.get("deviceModel").toString(),
						  						  mobileMap.get("osName").toString(),
						  						  mobileMap.get("osVersion").toString(),
						  						  mobileMap.get("userId").toString(),
						  						  DateFormatter(new Date().toString()));
				  mobileModel.setId(MobileId);
				  response.put("message", "Dispositivo Móvil actualizado correctamente");
				  response.put("mobile", mobileRepository.save(mobileModel));
				  response.put("status","200");	
			  }
			  else
			  {
				  response.put("message", "Dispositivo Móvil no existe");
				  response.put("mobile", null);
				  response.put("status","401");	
			  }
		  }
		  catch(Exception ex)
		  {
			  response.put("message", ex.getMessage());
			  response.put("mobile", null);
			  response.put("status","400");	
		  }
		  return response;
	  }
	
	  @RequestMapping(method = RequestMethod.DELETE, value="/{MobileId}")
	  public Map<String, String> deleteMobile(@PathVariable("MobileId") String MobileId){
		  Map<String, String> response = new HashMap<String, String>();
		  try{
			  mobileRepository.delete(MobileId);
			  response.put("message", "Dispositivo Móvil eliminado correctamente");
			  response.put("status","200");
		  }
		  catch(Exception ex)
		  {
			  response.put("message", ex.getMessage());
			  response.put("mobile", null);
			  response.put("Status","400");	
		  }
		  return response;
	  }

		private Date DateFormatter(String pDate){
			
			Date finalDate = new Date();
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
			try {
				finalDate = format.parse(pDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			return finalDate;		
		}		
	}

		
		
		
		