package com.centaurosolutions.com.beacon.merchantprofile.controller;

import java.util.*;

import com.centaurosolutions.com.beacon.merchantprofile.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.centaurosolutions.com.beacon.merchantprofile.repository.MerchantInvoiceHistoryRepository;

@RestController
@RequestMapping("/merchantinvoicehistory")
public class MerchantInvoiceHistoryController 
{
	@Autowired
	private MerchantInvoiceHistoryRepository merchantInvoiceHistoryRepository;
	
	
	/****************************************************************************************************************
	   * 
	   * Codigo para agregar la informacion de la factura relacionada a los puntos que uso el usuario para cancelarla
	   * 
	   * **************************************************************************************************************/
	  
	@RequestMapping(method = RequestMethod.POST )
	public Map<String, Object> createMerchantInvoiceHistory(@RequestBody Map<String, Object> merchantInvoiceHistoryMap)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
  
		try
		{
			MerchantInvoiceHistory invoiceHistory = new MerchantInvoiceHistory(
					merchantInvoiceHistoryMap.get("userId").toString(),
					merchantInvoiceHistoryMap.get("merchantId").toString(),					
					merchantInvoiceHistoryMap.get("invoiceId").toString(),
					Float.parseFloat(merchantInvoiceHistoryMap.get("invoiceAmount").toString()),
					merchantInvoiceHistoryMap.get("securityCode").toString(),
					merchantInvoiceHistoryMap.get("paymentType").toString(),
					Float.parseFloat( merchantInvoiceHistoryMap.get("usedPoints").toString() ),
					new Date()
					);
  
			merchantInvoiceHistoryRepository.save(invoiceHistory);
			response.put("message", "Historial de factura guardado correctamente"); 
			response.put("invoiceHistory", invoiceHistory); 
			response.put("status", "200");			  
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
			response.put("invoiceHistory", null); 
			response.put("status", "500");
		}		  
  
		return response;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
	public Map<String, Object> GetHistoryByUserId(@PathVariable("userId") String userId)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();

		try
		{
			List<MerchantInvoiceHistory> merchantInvoiceModelList =  merchantInvoiceHistoryRepository.findByUserId(userId);

			if(merchantInvoiceModelList.size() > 0) {

				response.put("message", "Total de registros: " + merchantInvoiceModelList.size());
				response.put("invoiceHistory", merchantInvoiceModelList);
				response.put("status", "200");
			}
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
			response.put("invoiceHistory", null);
			response.put("status", "500");
		}

		return response;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/merchant/{merchantId}")
	public Map<String, Object> GetHistoryByMerchantId(@PathVariable("merchantId") String userId)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();

		try
		{
			List<MerchantInvoiceHistory> merchantInvoiceModelList =  merchantInvoiceHistoryRepository.findByMerchantId(userId);

			if(merchantInvoiceModelList.size() > 0){

				response.put("message", "Total de registros: " + merchantInvoiceModelList.size());
				response.put("invoiceHistory", merchantInvoiceModelList);
				response.put("status", "500");
			}
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
			response.put("invoiceHistory", null);
			response.put("status", "400");
		}

		return response;
	}
	  
}
