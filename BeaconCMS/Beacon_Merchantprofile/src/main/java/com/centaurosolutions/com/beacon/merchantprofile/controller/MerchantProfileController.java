package com.centaurosolutions.com.beacon.merchantprofile.controller;


import com.centaurosolutions.com.beacon.merchantprofile.model.Department;
import com.centaurosolutions.com.beacon.merchantprofile.model.MerchantContactData;
import com.centaurosolutions.com.beacon.merchantprofile.model.MerchantProfile;
import com.centaurosolutions.com.beacon.merchantprofile.model.MerchantUser;
import com.centaurosolutions.com.beacon.merchantprofile.model.TotalGiftPoints;
import com.centaurosolutions.com.beacon.merchantprofile.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/merchantprofile")
public class MerchantProfileController 
{
	
	@Autowired
	private MerchantProfileRepository merchantProfileRepository;
		
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createMerchantProfile(@RequestBody Map<String, Object> merchantProfileMap)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			ArrayList<MerchantContactData> contacts = new ArrayList<MerchantContactData>();
			ArrayList<MerchantUser> users = new ArrayList<MerchantUser>();
			ArrayList<Department> departments = new ArrayList<Department>();
			TotalGiftPoints totalGiftPoints = new TotalGiftPoints();
			ObjectMapper mapper = new ObjectMapper();
			if(merchantProfileMap.get("contacts") != null)
			{
				contacts = (ArrayList<MerchantContactData>) merchantProfileMap.get("contacts");
			}
			
			if(merchantProfileMap.get("users") != null)
			{
				users = (ArrayList<MerchantUser>) merchantProfileMap.get("users");
			}
			if(merchantProfileMap.get("departments") != null)
			{
				departments = (ArrayList<Department>) merchantProfileMap.get("departments");
			}
			if(merchantProfileMap.get("totalGiftPoints") != null)
			{				
				totalGiftPoints = mapper.convertValue(merchantProfileMap.get("totalGiftPoints"), new TypeReference<TotalGiftPoints>() { });
			}
			
			MerchantProfile merchantProfileModel = new MerchantProfile(
					merchantProfileMap.get("country").toString(),
				    merchantProfileMap.get("city").toString(), 
				    contacts,
				    merchantProfileMap.get("timeZone").toString(),
				    merchantProfileMap.get("merchantName").toString(),
				    merchantProfileMap.get("address").toString(),
				    merchantProfileMap.get("image").toString(),
				    merchantProfileMap.get("businessType").toString(),
				    users,
				    Boolean.valueOf(merchantProfileMap.get("enable").toString()),
				    Integer.parseInt(merchantProfileMap.get("pointsToGive").toString()),
				    DateFormatter(merchantProfileMap.get("creationDate").toString()),
				    DateFormatter(merchantProfileMap.get("modifiedDate").toString()),
				    merchantProfileMap.get("updatedBy").toString(),
				    merchantProfileMap.get("latitude").toString(),
				    merchantProfileMap.get("longitude").toString(),
				    departments,
				    totalGiftPoints);
			
			merchantProfileRepository.save(merchantProfileModel);
			response.put("message", "Perfil de Tiendas creado correctamente");
		    response.put("merchantProfile", merchantProfileModel);
		    response.put("status", "200");
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
		    response.put("merchantProfile", null);
		    response.put("status", "400");
		}
	    return response;
	}
	
	  @RequestMapping(method = RequestMethod.GET, value="/{MerchantProfileId}")
	  public Map<String, Object> getMerchantProfileDetails(@PathVariable("MerchantProfileId") String merchantProfileId)
	  {
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  
		  try
		  {
			  MerchantProfile merchant = merchantProfileRepository.findOne(merchantProfileId);
		      if(merchant != null)
		      {
		    	  response.put("message", "Perfil de tiendaa encontrado");
		    	  response.put("merchantProfile", merchant);
		    	  response.put("status", "200");
		      }
		      else
		      {
		    	  response.put("message", "Perfil de tiendaa no encontrado");
				  response.put("merchantProfile", null);
				  response.put("status", "401");
		      } 
		  }
		  catch(Exception ex)
		  {
			  response.put("message", ex.getMessage());
			  response.put("merchantProfile", null);
			  response.put("status", "400");
		  }
		  
		  return response;
	  }
	  
	  @RequestMapping(method = RequestMethod.GET)
	  public Map<String, Object> getAllMerchantProfileDetails()
	  {
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  
		  try
		  {
		      List<MerchantProfile> merchantProfileModelList = merchantProfileRepository.findAll();
		      response.put("message", "Total de tiendas: "+ merchantProfileModelList.size());
		      response.put("merchantProfile", merchantProfileModelList);
		      response.put("status", "200");
		  }
		  catch(Exception ex)
		  {
			  response.put("message", ex.getMessage());
			  response.put("merchantProfile", null);
			  response.put("status", "400");
		  }
		  return response;  
	  }
	  
	  @SuppressWarnings("unchecked")
	  @RequestMapping(method = RequestMethod.PUT, value="/{MerchantProfileId}")
	  public Map<String, Object> editMerchantProfile(@PathVariable("MerchantProfileId") String MerchantProfileId,
	      @RequestBody Map<String, Object> merchantProfileMap)
	  {
	
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{  
			MerchantProfile merchant = merchantProfileRepository.findOne(MerchantProfileId);
		    if(merchant != null)
		    {
		    	ArrayList<MerchantContactData> contacts = new ArrayList<MerchantContactData>();
		    	ArrayList<MerchantUser> users = new ArrayList<MerchantUser>();
		    	ArrayList<Department> departments = new ArrayList<Department>();
				TotalGiftPoints totalGiftPoints = new TotalGiftPoints();
				ObjectMapper mapper = new ObjectMapper();
		    	if(merchantProfileMap.get("contacts") != null)
		    	{
		    		contacts = (ArrayList<MerchantContactData>) merchantProfileMap.get("contacts");
		    	}		
		    	if(merchantProfileMap.get("users") != null)
		    	{
		    		users = (ArrayList<MerchantUser>) merchantProfileMap.get("users");
		    	}
				if(merchantProfileMap.get("departments") != null)
				{
					departments = (ArrayList<Department>) merchantProfileMap.get("departments");
				}
				if(merchantProfileMap.get("totalGiftPoints") != null)
				{				
					totalGiftPoints = mapper.convertValue(merchantProfileMap.get("totalGiftPoints"), new TypeReference<TotalGiftPoints>() { });
				}
		    	MerchantProfile merchantProfileModel = new MerchantProfile(
		    			merchantProfileMap.get("country").toString(),
					    merchantProfileMap.get("city").toString(),
					    contacts,
					    merchantProfileMap.get("timeZone").toString(),
					    merchantProfileMap.get("merchantName").toString(),
					    merchantProfileMap.get("address").toString(),
					    merchantProfileMap.get("image").toString(),
					    merchantProfileMap.get("businessType").toString(),
					    users,
					    Boolean.valueOf(merchantProfileMap.get("enable").toString()),
					    Integer.parseInt(merchantProfileMap.get("pointsToGive").toString()),
					    DateFormatter(merchantProfileMap.get("creationDate").toString()),
					    DateFormatter(merchantProfileMap.get("modifiedDate").toString()),
					    merchantProfileMap.get("updatedBy").toString(),
					    merchantProfileMap.get("latitude").toString(),
					    merchantProfileMap.get("longitude").toString(),
					    departments,
					    totalGiftPoints);
		    	
		    	merchantProfileModel.setId(MerchantProfileId);
		    	response.put("message", "Perfil de Tiendas actualizado correctamente");
		    	response.put("merchantProfile", merchantProfileRepository.save(merchantProfileModel));
		    }
		    else
		    {
		    	response.put("message", "El id de la tiendaa no fue encontrado");
				response.put("merchantProfile", null);
				response.put("status", "401");
		    } 
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
			response.put("merchantProfile", null);
			response.put("status", "400");
		}
		return response;  
	 }
	  	  
	  @RequestMapping(method = RequestMethod.DELETE, value="/{MerchantProfileId}")
	  public Map<String, String> deleteMerchantProfile(@PathVariable("MerchantProfileId") String merchantProfileId)
	  {
		  Map<String, String> response = new HashMap<String, String>();
		  		  
		  try
		  {
			  merchantProfileRepository.delete(merchantProfileId);
			  response.put("message", "Perfil de Tienda eliminado correctamente");
			  response.put("status", "200");
		  }
		  catch(Exception ex)
		  {
			  response.put("message", ex.getMessage());
			  response.put("merchantProfile", null);
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