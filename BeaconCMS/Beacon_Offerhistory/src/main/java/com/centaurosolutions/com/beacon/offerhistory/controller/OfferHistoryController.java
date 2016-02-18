package com.centaurosolutions.com.beacon.offerhistory.controller;


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
import com.centaurosolutions.com.beacon.offerhistory.model.*;
import com.centaurosolutions.com.beacon.offerhistory.repository.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/offerhistory")
public class OfferHistoryController {
	
    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
	
	@Autowired
	private OfferHistoryRepository offerhistoryRepository;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createDevice(@RequestBody Map<String, Object> offerhistoryMap){
		

		OfferHistory offerhistoryModel = new OfferHistory( offerhistoryMap.get("user_id").toString(),offerhistoryMap.get("promo_id").toString(), offerhistoryMap.get("merchant_id").toString(),offerhistoryMap.get("shopZone_id").toString(),DateFormatter(getDate()), DateFormatter(getDate()),DateFormatter(getDate()));    
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Historial de oferta creado correctamente");
	    response.put("offerhistory", offerhistoryModel); 
		
	    offerhistoryRepository.save(offerhistoryModel);
		return response;
	}
	
	  @RequestMapping(method = RequestMethod.GET, value="/{offerId}")
	  public OfferHistory getDeviceDetails(@PathVariable("offerId") String offerHistoryId){
		  return offerhistoryRepository.findOne(offerHistoryId);
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.GET, value="/getAttempts/user/{userId}/promo/{promoId}")
	  public Map<String, Object> getPromoUserAttempts (@PathVariable("userId") String userId, @PathVariable("promoId") String promoId){
			
		  List<OfferHistory> offerhistoryModelList = offerhistoryRepository.findAll();
		  Date date = new Date();

			int attempts = 0;
	
			for(OfferHistory offerHistory : offerhistoryModelList){
				
				if(offerHistory.getPromo_id().equals(promoId) && offerHistory.getUser_id().equals(userId))
				{
					if(attempts==0){
						date=offerHistory.getScanDate();
					}else{
						if(date.getTime()<offerHistory.getScanDate().getTime()){
							date=offerHistory.getScanDate();
						}
					}
					attempts++;
					
				}				
			}
						
			Map<String, Object> response = new LinkedHashMap<String, Object>();
			response.put("promoId", promoId);
			response.put("userId", userId);
			response.put("lastScan", date);
			response.put("attempts", attempts);
			return response; 

	  }
	  
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getAllDeviceDetails(){
		List<OfferHistory> offerhistoryModelList = offerhistoryRepository.findAll();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("Total de Dispositivos", offerhistoryModelList.size());
		response.put("Historial de oferta", offerhistoryModelList);
		return response;
	}
	  
	  
	@RequestMapping(method = RequestMethod.PUT, value="/{offerId}")
	public Map<String, Object> editDevice(@PathVariable("offerId") String offerHistoryId,
	      @RequestBody Map<String, Object> offerhistoryMap){
		  

		  
		OfferHistory offerhistoryModel = new OfferHistory( offerhistoryMap.get("user_id").toString(),offerhistoryMap.get("promo_id").toString(), offerhistoryMap.get("merchant_id").toString(),offerhistoryMap.get("shopZone_id").toString(),DateFormatter(offerhistoryMap.get("scanDate").toString()), DateFormatter(offerhistoryMap.get("creationDate").toString()),DateFormatter(offerhistoryMap.get("modifiedDate").toString()));  
		offerhistoryModel.setId(offerHistoryId);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Historial de oferta actualizado correctamente");
	    response.put("Historial de oferta", offerhistoryRepository.save(offerhistoryModel));
	    return response;
	}
	  
	  
	@RequestMapping(method = RequestMethod.DELETE, value="/{offerId}")
	public Map<String, String> deleteDevice(@PathVariable("offerId") String offerhistoryId)
	{
	    offerhistoryRepository.delete(offerhistoryId);
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "Historial de oferta eliminado correctamente");

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
	
	private String getDate(){
        DateFormat df = DateFormat.getDateTimeInstance();
        long currentTime = new Date().getTime();
        String currDate = df.format(new Date(currentTime));
        return currDate;
		
	}

}
