package com.centaurosolutions.com.beacon.merchantprofile.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centaurosolutions.com.beacon.merchantprofile.model.MerchantBusinessType;
import com.centaurosolutions.com.beacon.merchantprofile.repository.MerchantBusinessTypeRepository;

@RestController
@RequestMapping("/merchantbusinesstype")
public class MerchantBusinessTypeController 
{
	@Autowired
	private MerchantBusinessTypeRepository merchantBusinessTypeRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{typeId}" )
	public Map<String, Object> Get(@PathVariable("typeId") String typeId)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			MerchantBusinessType merchantBusinessType = merchantBusinessTypeRepository.findOne(typeId);
			
			response.put("message", "MerchantBusinessType"); 
			response.put("merchantBusinessTypeResult", merchantBusinessType); 
			response.put("status", "200");
		}
		catch(Exception ex)
		{
			response.put("message", "Error al obtener MerchantBusinessType"); 
			response.put("merchantBusinessTypeResult", null); 
			response.put("status", "500");
		}
		
		return response;
	} 
	
	@RequestMapping(method = RequestMethod.GET, value = "/typeName/{type}" )
	public Map<String, Object> GetByName(@PathVariable("type") String type)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			MerchantBusinessType merchantBusinessType = merchantBusinessTypeRepository.findByType(type);
			
			response.put("message", "MerchantBusinessType"); 
			response.put("merchantBusinessTypeResult", merchantBusinessType); 
			response.put("status", "200");
		}
		catch(Exception ex)
		{
			response.put("message", "Error al obtener MerchantBusinessType"); 
			response.put("merchantBusinessTypeResult", null); 
			response.put("status", "500");
		}
		
		return response;
	} 

	@RequestMapping(method = RequestMethod.GET, value = "/all" )
	public Map<String, Object> GetAll()
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			ArrayList<MerchantBusinessType> merchantBusinessTypeList = (ArrayList<MerchantBusinessType>) merchantBusinessTypeRepository.findAll();
			
			response.put("message", "MerchantBusinessType"); 
			response.put("merchantBusinessTypeResult", merchantBusinessTypeList); 
			response.put("status", "200");
		}
		catch(Exception ex)
		{
			response.put("message", "Error al obtener todos los merchantBusinessTypes"); 
			response.put("merchantBusinessTypeResult", null); 
			response.put("status", "500");
		}
		
		return response;
	} 

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> Add(@RequestBody Map<String, Object> merchantBusinessTypeMap)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			MerchantBusinessType merchantBusinessType = new MerchantBusinessType(
					merchantBusinessTypeMap.get("type").toString().toUpperCase(), 
					merchantBusinessTypeMap.get("description").toString(), 
					merchantBusinessTypeMap.get("imageUrl").toString());
			
			merchantBusinessTypeRepository.save(merchantBusinessType);
			
			response.put("message", "MerchantBusinessType"); 
			response.put("merchantBusinessTypeResult", merchantBusinessType); 
			response.put("status", "200");
		}
		catch(Exception ex)
		{
			response.put("message", "Error al agregar MerchantBusinessType"); 
			response.put("merchantBusinessTypeResult", null); 
			response.put("status", "500");
		}
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{typeId}" )
	public Map<String, Object> Update(@PathVariable("typeId") String typeId,
		      @RequestBody Map<String, Object> merchantBusinessTypeMap)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			MerchantBusinessType merchantBusinessType = merchantBusinessTypeRepository.findOne(typeId);
			
			if(merchantBusinessType != null)
			{
				merchantBusinessType.setDescription(merchantBusinessTypeMap.get("description").toString());
				merchantBusinessType.setType(merchantBusinessTypeMap.get("type").toString().toUpperCase());
				merchantBusinessType.setImageUrl(merchantBusinessTypeMap.get("imageUrl").toString());
				
				merchantBusinessTypeRepository.save(merchantBusinessType);
				
				response.put("message", "MerchantBusinessType actualizado correctamente"); 
				response.put("merchantBusinessTypeResult", merchantBusinessType); 
				response.put("status", "200");
			}
			else
			{
				response.put("message", "MerchantBusinessType no encontrada"); 
				response.put("merchantBusinessTypeResult", null); 
				response.put("status", "404");
			}
		}
		catch(Exception ex)
		{
			response.put("message", "Error al actualizar MerchantBusinessType"); 
			response.put("notificationResult", null); 
			response.put("status", "500");
		}
		
		return response;
	}	
	 	
}
