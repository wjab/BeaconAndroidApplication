package com.centaurosolutions.com.beacon.offerhistory.controller;


import com.centaurosolutions.com.beacon.offerhistory.model.OfferHistory;
import com.centaurosolutions.com.beacon.offerhistory.repository.OfferHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/offerhistory")
public class OfferHistoryController {
	
    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
	
	@Autowired
	private OfferHistoryRepository offerhistoryRepository;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createDevice(@RequestBody Map<String, Object> offerhistoryMap)
	{
        OfferHistory offerhistoryModel = new OfferHistory();
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        try
        {
             offerhistoryModel = new OfferHistory(
                    offerhistoryMap.get("userId").toString(),
                    offerhistoryMap.get("promoId").toString(),
                    offerhistoryMap.get("merchantId").toString(),
                    offerhistoryMap.get("shopZoneId").toString(),
                    new Date(),
                    new Date(),
                    new Date());

            offerhistoryRepository.save(offerhistoryModel);

            response.put("message", "Historial de oferta creado correctamente");
            response.put("offerHistory", offerhistoryModel);
            response.put("status", 200);

        }
        catch (Exception ex)
        {
            response.put("message", ex.getMessage());
            response.put("offerHistory", null);
            response.put("status", 400);
        }

		return response;
	}
	
	  @RequestMapping(method = RequestMethod.GET, value = "/{offerId}")
	  public Map<String, Object> getDeviceDetails(@PathVariable("offerId") String offerHistoryId)
	  {
          Map<String, Object> response = new LinkedHashMap<String, Object>();
          OfferHistory offerhistoryModel = new OfferHistory();

          try
          {
              offerhistoryModel = offerhistoryRepository.findOne(offerHistoryId);

              if(offerhistoryModel !=null)
              {
                  response.put("message", "Historial de oferta encontrado");
                  response.put("offerHistory", offerhistoryModel);
                  response.put("status", 200);
              }
              else
              {
                  response.put("message", "Historial de oferta no encontrado");
                  response.put("offerHistory", null);
                  response.put("status", 404);
              }
          }
          catch (Exception ex)
          {
              response.put("message", ex.getMessage());
              response.put("offerHistory", null);
              response.put("status", 500);
          }

		  return response;
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.GET, value = "/getAttempts/user/{userId}/promo/{promoId}")
	  public Map<String, Object> getPromoUserAttempts (@PathVariable("userId") String userId, @PathVariable("promoId") String promoId)
	  {			
		  List<OfferHistory> offerhistoryModelList;
          Map<String, Object> response = new LinkedHashMap<String, Object>();
          Map<String, Object> attemptData = new LinkedHashMap<String, Object>();
		  Date date = new Date();
          int attempts = 0;

          try
          {
              offerhistoryModelList = offerhistoryRepository.findAll();
              for(OfferHistory offerHistory : offerhistoryModelList){

                  if(offerHistory.getPromoId().equals(promoId) && offerHistory.getUserId().equals(userId))
                  {
                      if(attempts==0)
                      {
                          date=offerHistory.getScanDate();
                      }
                      else
                      {
                          if(date.getTime()<offerHistory.getScanDate().getTime())
                          {
                              date = offerHistory.getScanDate();
                          }
                      }
                      attempts++;
                  }
              }
              attemptData.put("promoId", promoId);
              attemptData.put("userId", userId);
              attemptData.put("lastScan", date);
              attemptData.put("attempts", attempts);

              response.put("message", "Número de intentos del usuario");
              response.put("status", 200);
              response.put("attemptData", attemptData);
          }
          catch (Exception ex)
          {
              response.put("message", ex.getMessage());
              response.put("attemptData", null);
              response.put("status", 500);
          }

          return response;

	  }
	  
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getAllDeviceDetails()
	{
        Map<String, Object> response = new LinkedHashMap<String, Object>();

        try
        {
            List<OfferHistory> offerhistoryModelList = offerhistoryRepository.findAll();

            if(offerhistoryModelList!= null && offerhistoryModelList.size() > 0)
            {
                response.put("message", "Historial de ofertas");
                response.put("status", 200);
                response.put("listOfferHistory", offerhistoryModelList);
            }
            else 
            {
                response.put("message", "No hay promociones registradas");
                response.put("status", 404);
                response.put("listOfferHistory", null);
            }
        }
        catch (Exception ex)
        {
            response.put("message", ex.getMessage());
            response.put("listOfferHistory", null);
            response.put("status", 500);
        }

		return response;
	}
	  
	@RequestMapping(method = RequestMethod.PUT, value = "/{offerId}")
	public Map<String, Object> editDevice(@PathVariable("offerId") String offerHistoryId,
	      @RequestBody Map<String, Object> offerhistoryMap)
	{
        Map<String, Object> response = new LinkedHashMap<String, Object>();

        try
        {
            OfferHistory offerhistoryModel = new OfferHistory(
                    offerhistoryMap.get("userId").toString(),
                    offerhistoryMap.get("promoId").toString(),
                    offerhistoryMap.get("merchantId").toString(),
                    offerhistoryMap.get("shopZoneId").toString(),
                    DateFormatter(offerhistoryMap.get("scanDate").toString()),
                    DateFormatter(offerhistoryMap.get("creationDate").toString()),
                    DateFormatter(offerhistoryMap.get("modifiedDate").toString()));

            response.put("message", "Historial de oferta actualizado correctamente");
            response.put("offerHistory", offerhistoryModel);
            response.put("status", 200);
        }
        catch (Exception ex)
        {
            response.put("message", ex.getMessage());
            response.put("offerHistory", null);
            response.put("status", 500);
        }

	    return response;
	}
	  
	  
	@RequestMapping(method = RequestMethod.DELETE, value = "/{offerId}")
	public Map<String, Object> deleteDevice(@PathVariable("offerId") String offerhistoryId)
	{
        Map<String, Object> response = new HashMap<String, Object>();

        try
        {
            offerhistoryRepository.delete(offerhistoryId);
            response.put("message", "Historial de oferta eliminado correctamente");
            response.put("status", 200);
            response.put("offerHistory", offerhistoryId);
        }
        catch (Exception ex)
        {
            response.put("message", ex.getMessage());
            response.put("status", 500);
            response.put("offerHistory", offerhistoryId);
        }

	    return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/{userId}" )
	public Map<String, Object> GetOfferHistoryByUserId(@PathVariable("userId") String userId)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
        List<OfferHistory> offerHistoryList = null;

        try{

            if(userId !=null && !userId.isEmpty()){
                offerHistoryList = offerhistoryRepository.findAllByUserId(userId);

                if(offerHistoryList != null && offerHistoryList.size() > 0){
                    response.put("message", "");
                    response.put("status", 200);
                    response.put("offerHistory", offerHistoryList);
                }
                else{
                    response.put("message", "User does not have points registered");
                    response.put("status", 404);
                    response.put("offerHistory", null);
                }
            }
            else{
                response.put("message", "Missing parameters");
                response.put("status", 400);
                response.put("offerHistory", null);
            }
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
            response.put("status", 500);
            response.put("offerHistory", null);	
		}
		
		return response;
	}
	
	
	private void findAllByOfferHistory() {
		// TODO Auto-generated method stub
		
	}

	private Date DateFormatter(String pDate)
	{		
		Date finalDate = new Date();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		try 
		{
			finalDate = format.parse(pDate);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return finalDate;		
	}	
	
	private String getDate()
	{
        DateFormat df = DateFormat.getDateTimeInstance();
        long currentTime = new Date().getTime();
        String currDate = df.format(new Date(currentTime));
        return currDate;		
	}

}
