package com.centaurosolutions.com.beacon.utils.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import com.centaurosolutions.com.beacon.utils.model.*;



@RestController
@RequestMapping("/utils")
public class UtilsController {
	
    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
	private String urlUser = "http://buserdevel.cfapps.io/user/";
	private String urlPromo = "http://bpromodevel.cfapps.io/promo/";
	private String urlOfferHistory = "http://bofferhistorydevel.cfapps.io/offerhistory/";
    private String offerHistoryAttemptResource = "/getAttempts/user/%s/promo/%s";

	
	@RequestMapping(method = RequestMethod.POST, value="/savePoints")
	public Map<String, Object> createUser(@RequestBody Map<String, Object> customMap){

		// Attributes
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Map<String, Object> response = new LinkedHashMap<String, Object>();	
		DateDiffValues dateDiffInfo = null;
		Date dateNow = new Date();
		int points = 0;
		User userObject = null;
		OfferHistoryAttempt offerHistoryAttempt = null;
		Promo promoObject  = null;
		RestTemplate restTemplate = new RestTemplate();
		

		try{

			offerHistoryAttempt = restTemplate.getForObject(urlOfferHistory + String.format(offerHistoryAttemptResource,customMap.get("userId").toString(),customMap.get("promoId")),OfferHistoryAttempt.class);
			promoObject = restTemplate.getForObject(urlPromo + "" +customMap.get("promoId").toString(),Promo.class);
			
			if(promoObject != null && offerHistoryAttempt != null) {

                dateDiffInfo = getDateDiffCall(format.format(offerHistoryAttempt.getLastScan()).toString(), format.format(dateNow).toString());

                /***Verificar si es la primera vez en registrar una promoción en el historial de ofertas***/
                if (offerHistoryAttempt.getAttempts() == 0 || (offerHistoryAttempt.getAttempts() < promoObject.getAttempt() && (dateNow.after(promoObject.getStartDate()) && promoObject.getEndDate().after(dateNow)) && dateDiffInfo.getHours() > promoObject.getInterval())) {
                    userObject = restTemplate.getForObject(urlUser + "id/" + customMap.get("userId").toString(), User.class);
                    if (userObject != null) {
                        points = userObject.getTotal_gift_points() + promoObject.getGift_points();
                        userObject.setTotal_gift_points(points);
                        if (setUserPoints(userObject) && setUserPromoOffer(userObject.getId(), promoObject.getId())) {
                            response.put("user", userObject);
                        }
                    } else {
                        response.put("user", null);
                    }
                } else {
                    response.put("user", null);
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
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		try {
			
			if(customMap.get("initialDate") != null && customMap.get("finalDate") != null ){
				
				d1 = format.parse(customMap.get("initialDate").toString());
				d2 = format.parse(customMap.get("finalDate").toString());
	
				//in seconds
				diff = (d2.getTime() / 1000) - (d1.getTime() / 1000) ;
				
				if( diff > 0 ){
					
					diffSeconds = diff / 1000 % 60;
					diffMinutes = diff / (60 * 1000) % 60;
					diffHours = (diff / (60 * 60) % 24) + (diff / (24 * 60 * 60)) * 24;;
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
			HttpEntity<User> entity = new HttpEntity<User>(userObject , headers);
			ResponseEntity<User> out = restTemplate.exchange("http://buserdevel.cfapps.io/user/"+userObject.getId(), HttpMethod.PUT, entity , User.class);
			return true;
		}
		catch(Exception ex){
			return false;
		}			
	}
	
	public boolean setUserPromoOffer(String userId, String promoId){
		
		try{
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
			OfferHistory historyOffer = new OfferHistory();
			String strDate =  format.format(new Date().getTime());
            Date scanDate =  format.parse(strDate);
			historyOffer.setPromo_id(promoId);
			historyOffer.setUser_id(userId);
			historyOffer.setMerchant_id("");
			historyOffer.setShopZone_id("");
			historyOffer.setScanDate(scanDate);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<OfferHistory> entity = new HttpEntity<OfferHistory>(historyOffer , headers);
			ResponseEntity<OfferHistory> out = restTemplate.exchange("http://bofferhistorydevel.cfapps.io/offerhistory/", HttpMethod.POST, entity , OfferHistory.class);
			
			return true;
		}
		catch(Exception ex){
			return false;
		}	
		
	}	
	

	public DateDiffValues getDateDiffCall (String initialDate, String finalDate){
		   
		   Map<String, Object> parameters = new LinkedHashMap<String, Object>();
		   Map<String, Object> responseObject = new LinkedHashMap<String, Object>();
		   DateDiffValues diffValues = new DateDiffValues();
		   
		   try{
			   if((!initialDate.isEmpty() || initialDate != null)  && (!initialDate.isEmpty() || initialDate != null)){
				   
				   parameters.put("initialDate", initialDate);
				   parameters.put("finalDate", finalDate);
				   RestTemplate restTemplate = new RestTemplate();
				   HttpHeaders headers = new HttpHeaders();
				   headers.setContentType(MediaType.APPLICATION_JSON);
				   HttpEntity<DateDiffValues> entity = new HttpEntity(parameters , headers);
				   ResponseEntity<DateDiffValues> out =  restTemplate.exchange("http://butilsdevel.cfapps.io/utils/getDateDiff", HttpMethod.POST, entity , DateDiffValues.class);
				   diffValues = out.getBody();
			   }
		   }
		   catch(Exception ex){
			   diffValues = null;
		   }
	
		   
		   	
		return diffValues;
		   
	   }
}


	

	


