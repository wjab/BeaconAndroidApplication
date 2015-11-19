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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/promo")
public class PromoController {
	
    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
	
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
	  
	  @RequestMapping(method = RequestMethod.GET, value="/exp/{PromoId}")
	  public Map<String, Object>  getPromoExpiration(@PathVariable("PromoId") String promoId){
		  Promo myPromo = promoRepository.findOne(promoId);
		  Date startDate = myPromo.getStartDate();
		  Date endDate = myPromo.getEndDate();
		  Double expiration = getDaysDiff(endDate,startDate);
	
		  

		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  response.put("promoId", promoId );
		  response.put("expiration", expiration);
		  
		  return response;
	  }
	  
	  @RequestMapping(method = RequestMethod.GET, value="/exp")
	  public Map<String, Object> getAllPromoExp(){
		  List<Promo> promoModelList = promoRepository.findAll();
		  ArrayList promoExpList = 	new ArrayList();


		  Double expiration = 0.00;
		  for(Promo myPromo:promoModelList){
			  if(myPromo.getStartDate() != null && myPromo.getEndDate()!=null){
				  Date startDate = myPromo.getStartDate();
				  Date endDate = myPromo.getEndDate();
				   expiration = getDaysDiff(endDate,startDate);
					  Map<String, Object> dataExp = new LinkedHashMap<String, Object>();
					  dataExp.put("promoId", myPromo.getId());
					  dataExp.put("expiration",expiration);
					  
					  promoExpList.add(dataExp);


			  }

			  
		  }
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  response.put("PromosExp", promoExpList);

		  

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

			try {
				finalDate = format.parse(pDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return finalDate;		
		}
		

	    public  double getDaysDiff(Date date1, Date date2){

	        NumberFormat daysFormat = new DecimalFormat("#0.00");

	        long diff = date1.getTime() - date2.getTime();


	        double diffDays = (double) (diff / (24 * 60 * 60 * 1000));
	        double result = Double.valueOf((String.valueOf(daysFormat.format(diffDays)).replace(',', '.').toString()));

	        return result;
	    }

}
