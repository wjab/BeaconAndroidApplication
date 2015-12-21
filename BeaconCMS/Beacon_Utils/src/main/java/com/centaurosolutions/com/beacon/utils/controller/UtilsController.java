package com.centaurosolutions.com.beacon.utils.controller;

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
	
	@RequestMapping(method = RequestMethod.POST, value="/savePoints")
	public Map<String, Object> createUser(@RequestBody Map<String, Object> customMap){
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
				
		try{

			RestTemplate restTemplate = new RestTemplate();
			OfferHistoryAttempt offerHistoryAttempt = restTemplate.getForObject("http://bofferhistory.cfapps.io/offerhistory/getAttempts/user/"+customMap.get("userId").toString()+"/promo/"+customMap.get("promoId").toString(),OfferHistoryAttempt.class);		
			Promo promoObject = restTemplate.getForObject("http://bpromodev.cfapps.io/promo/"+customMap.get("promoId").toString(),Promo.class);
			
			if(promoObject != null && offerHistoryAttempt != null){
				if(offerHistoryAttempt.getAttempts() < promoObject.getAttempt() ){		
					String url = "http://beuserdev.cfapps.io/user/"+customMap.get("userId").toString();
					User userObject = restTemplate.getForObject("http://beuserdev.cfapps.io/user/id/"+customMap.get("userId").toString(), User.class);
					if(userObject != null){
						userObject.setTotal_gift_points(userObject.getTotal_gift_points() + promoObject.getGift_points());

						if(setUserPoints(userObject) && setUserPromoOffer(userObject.getId(), promoObject.getId())){
						    response.put("user", userObject);	
						}
					}
					else{
						response.put("userId", null);
					}
				}			
				else{
					response.put("userId", null);
					response.put("El usuario ha superado el limite de promociones escaneadas", null);	
				}
			}		
		}
		catch(Exception ex){
			response.put("userId", null);
			response.put("Error", ex.getMessage());
		}
		

		return response;
	}
	
	public boolean setUserPoints(User userObject){
		
		try{
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity entity = new HttpEntity(userObject , headers);
			ResponseEntity<User> out = restTemplate.exchange("http://beuserdev.cfapps.io/user/"+userObject.getId(), HttpMethod.PUT, entity , User.class);
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
			ResponseEntity<OfferHistory> out = restTemplate.exchange("http://bofferhistory.cfapps.io/offerhistory/", HttpMethod.POST, entity , OfferHistory.class);
			
			return true;
		}
		catch(Exception ex){
			return false;
		}	
		
	}	
}


	

	


