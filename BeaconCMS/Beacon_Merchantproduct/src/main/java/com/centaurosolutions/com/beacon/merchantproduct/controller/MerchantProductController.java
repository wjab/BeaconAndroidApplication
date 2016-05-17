package com.centaurosolutions.com.beacon.merchantproduct.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.centaurosolutions.com.beacon.merchantproduct.model.MerchantProduct;
import com.centaurosolutions.com.beacon.merchantproduct.model.Product;
import com.centaurosolutions.com.beacon.merchantproduct.repository.MerchantProductRepository;

@RestController
@RequestMapping("/merchantproduct")
public class MerchantProductController 
{
	@Autowired
	private MerchantProductRepository merchantProductRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> GetAllMerchantProducts()
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		try
		{
			List<MerchantProduct> merchantProductModelList = merchantProductRepository.findAll();
			response.put("Total de Productos del Negocio", merchantProductModelList.size());
			response.put("MerchantProduct", merchantProductModelList);
			response.put("Status", "200");
		}
		catch(Exception ex)
		{
			response.put("Message", ex.getMessage());
			response.put("MerchantProduct", null);
			response.put("Status", "400");
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{/{merchantproductid}")
	public Map<String, Object> GetMerchantProductsById(@PathVariable("merchantproductid") String merchantProductId)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		try
		{		
			MerchantProduct merchant_Finded= merchantProductRepository.findOne(merchantProductId);
			if(merchant_Finded!=null){
				response.put("Message", "MerchantProduct encontrado");
				response.put("MerchantProduct", null);
				response.put("Status", "200");
			}
			else
			{
				response.put("Message", "MerchantProduct no encontrado");
				response.put("MerchantProduct", null);
				response.put("Status", "401");
			}
		}
		catch(Exception ex)
		{
			response.put("Message", ex.getMessage());
			response.put("MerchantProduct", null);
			response.put("Status", "400");
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> CreateMerchantProduct(@RequestBody Map<String, Object> merchantProductMap )
	{
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
		try{
			ArrayList<Product> productList = new ArrayList<Product>();
			if(merchantProductMap.get("productlist") != null)
			{			
				productList = (ArrayList<Product>) merchantProductMap.get("productlist");			
			}
			MerchantProduct MerchantProductModel = new MerchantProduct(
														merchantProductMap.get("merchantId").toString(),
														merchantProductMap.get("shopZoneId").toString(),
														productList,
														merchantProductMap.get("longitude").toString(),
														merchantProductMap.get("latitude").toString());   
		    merchantProductRepository.save(MerchantProductModel);
			response.put("message", "MerchantProduct creado correctamente");
			response.put("MerchantProduct", MerchantProductModel); 
			response.put("Status", "200");
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
			response.put("MerchantProduct", null); 
			response.put("Status", "400");			
		}
	    return response;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.PUT, value = "/{merchantproductid}")
	public Map<String, Object> UpdateMerchantProduct(@PathVariable ("merchantproductid")String merchantProductId ,@RequestBody Map<String, Object> merchantProductMap )
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		try
		{
			MerchantProduct merchant_Finded= merchantProductRepository.findOne(merchantProductId);
			if(merchant_Finded!=null)
			{
				ArrayList<Product> productList = new ArrayList<Product>();
				if(merchantProductMap.get("productlist") != null)
				{			
					productList = (ArrayList<Product>) merchantProductMap.get("productlist");			
				}
				MerchantProduct MerchantProductModel = new MerchantProduct(merchantProductMap.get("merchantId").toString(),
																		   merchantProductMap.get("shopZoneId").toString(),
																		   productList,
																		   merchantProductMap.get("longitude").toString(),
																		   merchantProductMap.get("latitude").toString());   
				merchantProductRepository.save(MerchantProductModel);
				response.put("message", "MerchantProduct actualizado correctamente");
				response.put("MerchantProduct", MerchantProductModel); 
				response.put("Status", "200");		
			}
			else
			{
				response.put("message", "MerchantProduct no encontrado");
				response.put("MerchantProduct", null); 
				response.put("Status", "401");
			}
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
			response.put("MerchantProduct", null); 
			response.put("Status", "400");			
		}
	    return response;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/{merchantproductid}")
	public Map<String, String> deleteMerchantProductId(@PathVariable("merchantproductid") String merchantProductId){
		Map<String, String> response = new HashMap<String, String>();
		try
		{
			merchantProductRepository.delete(merchantProductId);		
			response.put("message", "MerchantProductId eliminado correctamente");
			response.put("Status", "200");
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
			response.put("MerchantProduct", null); 
			response.put("Status", "400");			
		}
    return response;
	}

}
