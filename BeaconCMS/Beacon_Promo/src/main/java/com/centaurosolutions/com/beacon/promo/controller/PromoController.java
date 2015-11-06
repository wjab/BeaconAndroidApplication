package com.centaurosolutions.com.beacon.promo.controller;

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

import com.centaurosolutions.com.beacon.promo.model.*;
import com.centaurosolutions.com.beacon.promo.repository.PromoRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/promo")
public class PromoController {
	
	@Autowired
	private PromoRepository promoRepository;
	

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createPromo(@RequestBody Map<String, Object> promoMap){
		
		ArrayList<PromoImage> images = null;
		
		if(promoMap.get("images") != null){
			images =  (ArrayList<PromoImage>) promoMap.get("images");
		}
		

		Promo promoModel = new Promo((Boolean)promoMap.get("enable"), promoMap.get("profile_id").toString(), promoMap.get("code").toString() ,Integer.parseInt(promoMap.get("gift_points").toString()),Integer.parseInt(promoMap.get("attempt").toString()),DateFormatter(promoMap.get("startDate").toString()), DateFormatter(promoMap.get("endDate").toString()),promoMap.get("type").toString(),Integer.parseInt(promoMap.get("availability").toString()), DateFormatter(promoMap.get("creationDate").toString()), DateFormatter(promoMap.get("modifiedDate").toString()),promoMap.get("updatedBy").toString(),promoMap.get("promoTitle").toString(),promoMap.get("promoDescription").toString(),(Boolean)promoMap.get("isAutomatic"),images);
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Promoción creada correctamente");
	    response.put("promo", promoModel); 
		
	    promoRepository.save(promoModel);
		return response;
	}
	
	  @RequestMapping(method = RequestMethod.GET, value="/{PromoId}")
	  public Promo getPromoDetails(@PathVariable("PromoId") String promoId){
	    return promoRepository.findOne(promoId);
	  }
	  
	  @RequestMapping(method = RequestMethod.GET)
	  public Map<String, Object> getAllPromoDetails(){
		  List<Promo> promoModelList = promoRepository.findAll();
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  response.put("Total de promociones", promoModelList.size());
		  response.put("Promo", promoModelList);
		  return response;
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.PUT, value="/{PromoId}")
	  public Map<String, Object> editPromo(@PathVariable("PromoId") String PromoId,
	      @RequestBody Map<String, Object> promoMap){
		  
		  

			ArrayList<PromoImage> images = null;
			
			if(promoMap.get("images") != null){
				images =  (ArrayList<PromoImage>) promoMap.get("images");
			}
			


	    Promo promoModel = new Promo((Boolean)promoMap.get("enable"), promoMap.get("profile_id").toString(), promoMap.get("code").toString() ,Integer.parseInt(promoMap.get("gift_points").toString()),Integer.parseInt(promoMap.get("attempt").toString()),DateFormatter(promoMap.get("startDate").toString()), DateFormatter(promoMap.get("endDate").toString()),promoMap.get("type").toString(),Integer.parseInt(promoMap.get("availability").toString()), DateFormatter(promoMap.get("creationDate").toString()), DateFormatter(promoMap.get("modifiedDate").toString()),promoMap.get("updatedBy").toString(),promoMap.get("promoTitle").toString(),promoMap.get("promoDescription").toString(),(Boolean)promoMap.get("isAutomatic"),images);
		promoModel.setId(PromoId);
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Promoción actualizada correctamente");
	    response.put("Promo", promoRepository.save(promoModel));
	    return response;
	  }
	
	  @RequestMapping(method = RequestMethod.DELETE, value="/{PromoId}")
	  public Map<String, String> deletePromo(@PathVariable("PromoId") String promoId){
	    promoRepository.delete(promoId);
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "Promoción eliminada correctamente");

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
