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
		List<MerchantProduct> merchantProductModelList = merchantProductRepository.findAll();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("Total de Productos del Negocio", merchantProductModelList.size());
		response.put("MerchantProduct", merchantProductModelList);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{/{merchantproductid}")
	public MerchantProduct GetMerchantProductsById(@PathVariable("merchantproductid") String merchantProductId)
	{
		return merchantProductRepository.findOne(merchantProductId);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> CreateMerchantProduct(@RequestBody Map<String, Object> merchantProductMap )
	{
		ArrayList<Product> productList = new ArrayList<Product>();
		
		if(merchantProductMap.get("productlist") != null)
		{			
			productList = (ArrayList<Product>) merchantProductMap.get("productlist");			
		}

		MerchantProduct MerchantProductModel = new MerchantProduct(
				merchantProductMap.get("merchantId").toString(),
				merchantProductMap.get("shopZoneId").toString(),
				productList);   
		
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "MerchantProduct creado correctamente");
	    response.put("MerchantProduct", MerchantProductModel); 
		
	    merchantProductRepository.save(MerchantProductModel);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.PUT, value = "/{merchantproductid}")
	public Map<String, Object> UpdateMerchantProduct(@PathVariable ("merchantproductid")String merchantProductId ,@RequestBody Map<String, Object> merchantProductMap )
	{
		ArrayList<Product> productList = new ArrayList<Product>();
		
		if(merchantProductMap.get("productlist") != null)
		{			
			productList = (ArrayList<Product>) merchantProductMap.get("productlist");			
		}

		MerchantProduct MerchantProductModel = new MerchantProduct(
				merchantProductMap.get("merchantId").toString(),
				merchantProductMap.get("shopZoneId").toString(),
				productList);   
		
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "MerchantProduct actualizado correctamente");
	    response.put("MerchantProduct", MerchantProductModel); 
		
	    merchantProductRepository.save(MerchantProductModel);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/{merchantproductid}")
	public Map<String, String> deleteMerchantProductId(@PathVariable("merchantproductid") String merchantProductId){
		merchantProductRepository.delete(merchantProductId);
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "MerchantProductId eliminado correctamente");

	    return response;
	}

}
