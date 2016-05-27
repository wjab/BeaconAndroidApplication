package com.centaurosolutions.com.beacon.offerhistory.controller;


import com.centaurosolutions.com.beacon.offerhistory.model.OfferHistory;
import com.centaurosolutions.com.beacon.offerhistory.repository.OfferHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                    offerhistoryMap.get("user_id").toString(),
                    offerhistoryMap.get("promo_id").toString(),
                    offerhistoryMap.get("merchant_id").toString(),
                    offerhistoryMap.get("shopZone_id").toString(),
                    DateFormatter(new Date().toString()),
                    DateFormatter(new Date().toString()),
                    DateFormatter(new Date().toString()));

            offerhistoryRepository.save(offerhistoryModel);

            response.put("message", "Historial de oferta creado correctamente");
            response.put("offerhistory", offerhistoryModel);
            response.put("status", 200);

        }
        catch (Exception ex)
        {
            response.put("message", ex.getMessage());
            response.put("offerhistory", null);
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
                  response.put("offerhistory", offerhistoryModel);
                  response.put("status", 200);
              }
              else
              {
                  response.put("message", "Historial de oferta no encontrado");
                  response.put("offerhistory", null);
                  response.put("status", 404);
              }
          }
          catch (Exception ex)
          {
              response.put("message", ex.getMessage());
              response.put("offerhistory", null);
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
                response.put("listOfferhistory", offerhistoryModelList);
            }
            else 
            {
                response.put("message", "No hay promociones registradas");
                response.put("status", 404);
                response.put("listOfferhistory", null);
            }
        }
        catch (Exception ex)
        {
            response.put("message", ex.getMessage());
            response.put("listOfferhistory", null);
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
                    offerhistoryMap.get("user_id").toString(),
                    offerhistoryMap.get("promo_id").toString(),
                    offerhistoryMap.get("merchant_id").toString(),
                    offerhistoryMap.get("shopZone_id").toString(),
                    DateFormatter(offerhistoryMap.get("scanDate").toString()),
                    DateFormatter(offerhistoryMap.get("creationDate").toString()),
                    DateFormatter(offerhistoryMap.get("modifiedDate").toString()));

            response.put("message", "Historial de oferta actualizado correctamente");
            response.put("offerhistory", offerhistoryModel);
            response.put("status", 200);
        }
        catch (Exception ex)
        {
            response.put("message", ex.getMessage());
            response.put("offerhistory", null);
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
            response.put("offerhistory", offerhistoryId);
        }
        catch (Exception ex)
        {
            response.put("message", ex.getMessage());
            response.put("status", 500);
            response.put("offerhistory", offerhistoryId);
        }

	    return response;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> GetOfferHistoryByUserId(@RequestBody Map<String, Object> offerhistoryMap)
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		String userId = "";
		int page = 0;
		Pageable pageable; 
		
		try
		{
			userId = offerhistoryMap.get("userId").toString();
			page = (offerhistoryMap.get("pageable") != null ? Integer.parseInt( offerhistoryMap.get("pageable").toString() ) : 0 );
			pageable = new PageRequest(0, page);
			
			List<OfferHistory> offerHistoryList = offerhistoryRepository.findAllByUserId(userId, pageable);
			
			response.put("message", "");
            response.put("status", 200);
			response.put("offerhistory", offerHistoryList);
		}
		catch(Exception ex)
		{
			response.put("message", ex.getMessage());
            response.put("status", 500);
            response.put("offerhistory", null);	
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
