package com.centaurosolutions.com.beacon.utils.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.centaurosolutions.com.beacon.utils.model.OfferHistoryAttempt;
import org.springframework.http.ResponseEntity;

import com.centaurosolutions.com.beacon.utils.model.*;



@RestController
@RequestMapping("/utils")
public class UtilsController {
	
    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
	
	@RequestMapping(method = RequestMethod.POST, value="/savePoints")
	public Map<String, Object> createUser(@RequestBody Map<String, Object> customMap){
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
				
		try{

			RestTemplate restTemplate = new RestTemplate();
			OfferHistoryAttempt offerHistoryAttempt = restTemplate.getForObject("http://bofferhistorydevel.cfapps.io/offerhistory/getAttempts/user/"+customMap.get("userId").toString()+"/promo/"+customMap.get("promoId").toString(),OfferHistoryAttempt.class);		
			Promo promoObject = restTemplate.getForObject("http://bpromodevel.cfapps.io/promo/"+customMap.get("promoId").toString(),Promo.class);
			
			if(promoObject != null && offerHistoryAttempt != null){
				if(offerHistoryAttempt.getAttempts() < promoObject.getAttempt() ){		
					String url = "http://buserdevel.cfapps.io/user/"+customMap.get("userId").toString();
					User userObject = restTemplate.getForObject("http://buserdevel.cfapps.io/user/id/"+customMap.get("userId").toString(), User.class);
					if(userObject != null){
						
						int points = userObject.getTotal_gift_points() + promoObject.getGift_points();
						userObject.setTotal_gift_points(points);
						
						if(setUserPoints(userObject) && setUserPromoOffer(userObject.getId(), promoObject.getId())){
						    response.put("user", userObject);	
						}
					} 
					else{
						response.put("user", null);
					}
				}			
				else{
					response.put("user", null);
					response.put("El usuario ha superado el limite de promociones escaneadas", null);	
				}
			}
			else{
				response.put("user", null);
				response.put("cause", "Objetos vacíos");
			}
		}
		catch(Exception ex){
			response.put("user", null);
			response.put("error", ex.getMessage());
		}
		

		return response;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value="/getDateDiff")
	public Map<String, Object> getAccurateDateDifference(@RequestBody Map<String, Object> customMap){
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		Date d1 = null;
		Date d2 = null;
		long diff = 0;
		long diffSeconds = 0;
		long diffMinutes = 0;
		long diffHours = 0;
		long diffDays = 0;

		try {
			
			if(customMap.get("initialDate") != null && customMap.get("finalDate") != null ){
				
				d1 = format.parse(customMap.get("initialDate").toString());
				d2 = format.parse(customMap.get("finalDate").toString());
	
				//in milliseconds
				diff = d2.getTime() - d1.getTime();
				
				if( diff > 0 ){
					
					diffSeconds = diff / 1000 % 60;
					diffMinutes = diff / (60 * 1000) % 60;
					diffHours = diff / (60 * 60 * 1000) % 24;
					diffDays = diff / (24 * 60 * 60 * 1000);
					
					response.put("days", diffDays);
					response.put("hours", diffHours);
					response.put("minutes", diffMinutes);
					response.put("seconds", diffSeconds);
				}
				
				else{
					response.put("error", "La fecha inicial es mayor a la fecha final");
				}			
			}		
			else{
				response.put("error", "Parámetros nulos");
			}

		} catch (Exception e) {
			response.put("error", e.getStackTrace());
		}
				
		return response;
	}
	
	public boolean setUserPoints(User userObject){
		
		try{
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity entity = new HttpEntity(userObject , headers);
			ResponseEntity<User> out = restTemplate.exchange("http://buserdevel.cfapps.io/user/"+userObject.getId(), HttpMethod.PUT, entity , User.class);
			return true;
		}
		catch(Exception ex){
			return false;
		}			
	}
	
	public boolean setUserPromoOffer(String userId, String promoId){
		
		try{
			OfferHistory historyOffer = new OfferHistory();
			historyOffer.setPromo_id(promoId);
			historyOffer.setUser_id(userId);
			historyOffer.setMerchant_id("");
			historyOffer.setShopZone_id("");
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity entity = new HttpEntity(historyOffer , headers);
			ResponseEntity<OfferHistory> out = restTemplate.exchange("http://bofferhistorydevel.cfapps.io/offerhistory/", HttpMethod.POST, entity , OfferHistory.class);
			
			return true;
		}
		catch(Exception ex){
			return false;
		}	
		
	}	
}


	

	


