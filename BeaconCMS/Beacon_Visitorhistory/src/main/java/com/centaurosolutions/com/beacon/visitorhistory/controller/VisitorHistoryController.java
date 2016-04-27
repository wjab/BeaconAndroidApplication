package com.centaurosolutions.com.beacon.visitorhistory.controller;

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

import com.centaurosolutions.com.beacon.visitorhistory.model.*;
import com.centaurosolutions.com.beacon.visitorhistory.repository.VisitorHistoryRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/visitorhistory")
public class VisitorHistoryController {
	
	@Autowired
	private VisitorHistoryRepository promoRepository;
	

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createVisitorHistory(@RequestBody Map<String, Object> visitorHistoryMap){
		

		VisitorHistory visitorhistoryModel = new VisitorHistory(visitorHistoryMap.get("user_id").toString(), visitorHistoryMap.get("merchant_id").toString(), visitorHistoryMap.get("shopzone_id").toString() ,DateFormatter(visitorHistoryMap.get("rowDate").toString()));
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Historial de visitas creado correctamente");
	    response.put("promo", visitorhistoryModel); 
		
	    promoRepository.save(visitorhistoryModel);
		return response;
	}
	
	  @RequestMapping(method = RequestMethod.GET, value="/{VisitorHistoryId}")
	  public VisitorHistory getVisitorHistoryDetails(@PathVariable("VisitorHistoryId") String visitorHistoryId){
	    return promoRepository.findOne(visitorHistoryId);
	  }
	  
	  @RequestMapping(method = RequestMethod.GET)
	  public Map<String, Object> getAllVisitorHistoryDetails(){
		  List<VisitorHistory> visitorhistoryModelList = promoRepository.findAll();
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  response.put("Total de Historial de Visitas", visitorhistoryModelList.size());
		  response.put("VisitorHistory", visitorhistoryModelList);
		  return response;
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.PUT, value="/{VisitorHistoryId}")
	  public Map<String, Object> editVisitorHistory(@PathVariable("VisitorHistoryId") String VisitorHistoryId,
	      @RequestBody Map<String, Object> visitorHistoryMap){
		  
		  
	    VisitorHistory visitorhistoryModel = new VisitorHistory(visitorHistoryMap.get("user_id").toString(), visitorHistoryMap.get("merchant_id").toString(), visitorHistoryMap.get("shopzone_id").toString() ,DateFormatter(visitorHistoryMap.get("rowDate").toString()));
			  
        visitorhistoryModel.setId(VisitorHistoryId);
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Historial de visitas actualizado correctamente");
	    response.put("VisitorHistory", promoRepository.save(visitorhistoryModel));
	    return response;
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.DELETE, value="/{VisitorHistoryId}")
	  public Map<String, String> deleteVisitorHistory(@PathVariable("VisitorHistoryId") String visitorHistoryId){
	    promoRepository.delete(visitorHistoryId);
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "Historial de visitas eliminado correctamente");

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
