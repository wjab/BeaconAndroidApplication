package com.centaurosolutions.com.beacon.merchantprofile.controller;


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

import com.centaurosolutions.com.beacon.merchantprofile.model.*;
import com.centaurosolutions.com.beacon.merchantprofile.repository.MerchantProfileRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;


@RestController
@RequestMapping("/merchantprofile")
public class MerchantProfileController {
	
	@Autowired
	private MerchantProfileRepository merchantProfileRepository;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createMerchantProfile(@RequestBody Map<String, Object> merchantProfileMap){
		

		ArrayList<MerchantContactData> contacts = new ArrayList<MerchantContactData>();
		ArrayList<MerchantUser> users = new ArrayList<MerchantUser>();
		
		if(merchantProfileMap.get("contacts") != null){
			contacts = (ArrayList<MerchantContactData>) merchantProfileMap.get("contacts");
		}
		
		if(merchantProfileMap.get("users") != null){
			users = (ArrayList<MerchantUser>) merchantProfileMap.get("users");
		}
		
		
		MerchantProfile merchantProfileModel =new MerchantProfile(merchantProfileMap.get("country").toString(), merchantProfileMap.get("city").toString(), contacts ,merchantProfileMap.get("timeZone").toString(),merchantProfileMap.get("merchantName").toString(),merchantProfileMap.get("address").toString(),merchantProfileMap.get("image").toString(),merchantProfileMap.get("businessType").toString(),users, (Boolean)merchantProfileMap.get("enable"),Integer.parseInt(merchantProfileMap.get("pointsToGive").toString()), DateFormatter(merchantProfileMap.get("creationDate").toString()) , DateFormatter(merchantProfileMap.get("modifiedDate").toString()),merchantProfileMap.get("updatedBy").toString(),merchantProfileMap.get("latitude").toString(),merchantProfileMap.get("longitude").toString());
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Perfil de Tiendas creado correctamente");
	    response.put("promo", merchantProfileModel); 
		
	    merchantProfileRepository.save(merchantProfileModel);
		return response;
	}
	
	  @RequestMapping(method = RequestMethod.GET, value="/{MerchantProfileId}")
	  public MerchantProfile getMerchantProfileDetails(@PathVariable("MerchantProfileId") String merchantProfileId){
	    return merchantProfileRepository.findOne(merchantProfileId);
	  }
	  
	  @RequestMapping(method = RequestMethod.GET)
	  public Map<String, Object> getAllMerchantProfileDetails(){
		  List<MerchantProfile> merchantProfileModelList = merchantProfileRepository.findAll();
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  response.put("Total de Perfiles de Tiendas", merchantProfileModelList.size());
		  response.put("MerchantProfile", merchantProfileModelList);
		  return response;
	  }
	  
	  @SuppressWarnings("unchecked")
	  @RequestMapping(method = RequestMethod.PUT, value="/{MerchantProfileId}")
	  public Map<String, Object> editMerchantProfile(@PathVariable("MerchantProfileId") String MerchantProfileId,
	      @RequestBody Map<String, Object> merchantProfileMap){
		  
		  
		ArrayList<MerchantContactData> contacts = new ArrayList<MerchantContactData>();
	
		ArrayList<MerchantUser> users = new ArrayList<MerchantUser>();
		
		if(merchantProfileMap.get("contacts") != null){
			contacts = (ArrayList<MerchantContactData>) merchantProfileMap.get("contacts");
		}
		
		if(merchantProfileMap.get("users") != null){
			users = (ArrayList<MerchantUser>) merchantProfileMap.get("users");
		}
		
		  
		MerchantProfile merchantProfileModel = new MerchantProfile(merchantProfileMap.get("country").toString(), merchantProfileMap.get("city").toString(), contacts ,merchantProfileMap.get("timeZone").toString(),merchantProfileMap.get("merchantName").toString(),merchantProfileMap.get("address").toString(),merchantProfileMap.get("image").toString(),merchantProfileMap.get("businessType").toString(),users, (Boolean)merchantProfileMap.get("enable"),Integer.parseInt(merchantProfileMap.get("pointsToGive").toString()), DateFormatter(merchantProfileMap.get("creationDate").toString()) , DateFormatter(merchantProfileMap.get("modifiedDate").toString()),merchantProfileMap.get("updatedBy").toString(),merchantProfileMap.get("latitude").toString(),merchantProfileMap.get("longitude").toString());
			   		  
        merchantProfileModel.setId(MerchantProfileId);
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Perfil de Tiendas actualizado correctamente");
	    response.put("MerchantProfile", merchantProfileRepository.save(merchantProfileModel));
	    return response;
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.DELETE, value="/{MerchantProfileId}")
	  public Map<String, String> deleteMerchantProfile(@PathVariable("MerchantProfileId") String merchantProfileId){
	    merchantProfileRepository.delete(merchantProfileId);
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "Perfil de Tienda eliminado correctamente");

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