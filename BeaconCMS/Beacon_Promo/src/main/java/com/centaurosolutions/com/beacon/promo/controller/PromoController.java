package com.centaurosolutions.com.beacon.promo.controller;

import com.centaurosolutions.com.beacon.promo.model.Promo;
import com.centaurosolutions.com.beacon.promo.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.*;
import java.util.*;

@RestController
@RequestMapping("/promo")
public class PromoController {
    
    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
    
    @Autowired
    private PromoRepository promoRepository;
    

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> createPromo(@RequestBody Map<String, Object> promoMap){

        Map<String, Object> response = new LinkedHashMap<String, Object>();
        try{


            Promo promoModel = new Promo((Boolean)promoMap.get("enable"),
                                          promoMap.get("merchantId").toString(),
                                          promoMap.get("code").toString() ,
                                          Integer.parseInt(promoMap.get("giftPoints").toString()),
                                          Integer.parseInt(promoMap.get("attempt").toString()),
                                          DateFormatter(promoMap.get("startDate").toString()),
                                          DateFormatter(promoMap.get("endDate").toString()),
                                          promoMap.get("type").toString(),
                                          Integer.parseInt(promoMap.get("availability").toString()),
                                          DateFormatter(promoMap.get("creationDate").toString()),
                                          DateFormatter(promoMap.get("modifiedDate").toString()),
                                          promoMap.get("updatedby").toString(),
                                          promoMap.get("title").toString(),
                                          promoMap.get("description").toString(),
                                         (Boolean)promoMap.get("isAutomatic"),
                                          promoMap.get("images").toString(),
                                          Integer.parseInt(promoMap.get("interval").toString()));


            promoRepository.save(promoModel);
            response.put("message", "Promoción creada correctamente");
            response.put("status", 200);;
            response.put("promo", promoModel);

        }
        catch (Exception ex){
            response.put("message", ex.getMessage());
            response.put("status", 500);
            response.put("promo",null);
        }

        return response;
    }
    
      @RequestMapping(method = RequestMethod.GET, value="/{promoId}")
      public Map<String, Object> getPromoDetails(@PathVariable("promoId") String promoId){

          Map<String, Object> response =  new LinkedHashMap<String, Object>();
          Promo promo = new Promo();

          try{

              promo = promoRepository.findOne(promoId);

              if(promo != null){

                  response.put("message","Promoción encontrada");
                  response.put("status", 200);
                  response.put("promo",promo);
              }
              else{
                  response.put("message","Promoción no encontrada");
                  response.put("status", 404);
                  response.put("promo",null);
              }
          }
          catch (Exception ex){
              response.put("message", ex.getMessage());
              response.put("status", 500);
              response.put("promo",null);
          }

          return response ;
      }
      
      @RequestMapping(method = RequestMethod.GET)
      public Map<String, Object> getAllPromoDetails(){

          Map<String, Object> response = new LinkedHashMap<String, Object>();

          try{

              List<Promo> promoModelList = promoRepository.findAll();
              if(promoModelList!=null && promoModelList.size() > 0){
                  response.put("message", "Promociones encontradas: "+  promoModelList.size());
                  response.put("status", 200);
                  response.put("listPromo", promoModelList);
              }
              else{
                  response.put("message", "No hay promociones registradas");
                  response.put("status", 404);
                  response.put("listPromo", null);
              }

          }
          catch (Exception ex){
              response.put("message",ex.getMessage());
              response.put("status", 500);
              response.put("listPromo", null);
          }

          return response;
      }
      
      @RequestMapping(method = RequestMethod.GET, value="/exp/{promoId}")
      public Map<String, Object>  getPromoExpiration(@PathVariable("promoId") String promoId){

          Map<String, Object> response = new LinkedHashMap<String, Object>();
          Map<String, Object> expirationData = new LinkedHashMap<String, Object>();

          try{
              Promo myPromo = promoRepository.findOne(promoId);

              if(myPromo != null){
                  Date startDate = myPromo.getStartDate();
                  Date endDate = myPromo.getEndDate();
                  Double expiration = getDaysDiff(endDate,startDate);
                  expirationData.put("promoId", promoId );
                  expirationData.put("expiration", expiration);

                  response.put("message","Expiración de la promoción");
                  response.put("status", 200);
                  response.put("expirationData", expirationData);

              }
              else {
                  response.put("message","La promoción no existe");
                  response.put("status", 404);
                  response.put("expirationData", null);
              }
          }
          catch (Exception ex){
              response.put("message",ex.getMessage());
              response.put("status", 500);
              response.put("expirationData", null);
          }

          return response;
      }
      
      @RequestMapping(method = RequestMethod.GET, value="/exp")
      public Map<String, Object> getAllPromoExp(){

          ArrayList promoExpList = new ArrayList();
          Map<String, Object> response = new LinkedHashMap<String, Object>();

          try{
              Double expiration = 0.00;
              List<Promo> promoModelList = promoRepository.findAll();
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

              response.put("message","Listado de expiración de promociones");
              response.put("status", 200);
              response.put("listPromo", promoExpList);

          }
          catch (Exception ex){

              response.put("message", ex.getMessage());
              response.put("status", 500);
              response.put("listPromo", promoExpList);
          }

          return response;
      }
      
      
      @RequestMapping(method = RequestMethod.PUT, value="/{promoId}")
      public Map<String, Object> editPromo(@PathVariable("promoId") String promoId,
          @RequestBody Map<String, Object> promoMap){

          Map<String, Object> response = new HashMap<String, Object>();
          Promo promo;
          try{
        	  promo = promoRepository.findById(promoId);
        	  if (promo != null) 
				{
					promo.setEnable((Boolean)promoMap.get("enable"));
					
                    promo.setMerchantId(promoMap.get("merchantId").toString());
                    promo.setCode(promoMap.get("code").toString()); 
                    promo.setGiftPoints(Integer.parseInt(promoMap.get("giftPoints").toString()));
                    promo.setAttempt(Integer.parseInt(promoMap.get("attempt").toString()));
                    promo.setStartDate(DateFormatter(promoMap.get("startDate").toString()));
                    promo.setEndDate(DateFormatter(promoMap.get("endDate").toString()));
                    promo.setType(promoMap.get("type").toString());
                    promo.setAvailability(Integer.parseInt(promoMap.get("availability").toString()));
                    promo.setCreationDate(DateFormatter(promoMap.get("creationDate").toString()));
                    promo.setModifiedDate(DateFormatter(promoMap.get("modifiedDate").toString()));
                    promo.setUpdatedby(promoMap.get("updatedby").toString());
                    promo.setTitle(promoMap.get("title").toString());
                    promo.setDescription(promoMap.get("description").toString());
                    promo.setIsAutomatic((Boolean)promoMap.get("isAutomatic"));
                    promo.setImages(promoMap.get("images").toString());
                    promo.setInterval(Integer.parseInt(promoMap.get("interval").toString()));
					
					response.put("message", "Promo updated");
					response.put("promo", promoRepository.save(promo));
					response.put("status",200);
				} 
        	  else 
				{
					response.put("status", 404);
					response.put("message", "Promo not found, not changed");
					response.put("promo", null);
				}
              		

          }
          catch (Exception ex){

              response.put("message", ex.getMessage());
              response.put("promo", null);
              response.put("status", 500);
          }

        return response;
      }
    
      @RequestMapping(method = RequestMethod.DELETE, value="/{promoId}")
      public Map<String, Object> deletePromo(@PathVariable("promoId") String promoId){

          Map<String, Object> response = new LinkedHashMap<>();
          try{
              promoRepository.delete(promoId);
              response.put("message", "Promoción eliminada correctamente");
              response.put("status", 200);
              response.put("promo", promoId);
          }
          catch (Exception ex){
              response.put("message", ex.getMessage());
              response.put("status", 500);
              response.put("promo", promoId);
          }

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

        public  double getDaysDiff(Date date1, Date date2){

            NumberFormat daysFormat = new DecimalFormat("#0.00");
            long diff = date1.getTime() - date2.getTime();
            double diffDays = (double) (diff / (24 * 60 * 60 * 1000));
            double result = Double.valueOf((String.valueOf(daysFormat.format(diffDays)).replace(',', '.').toString()));

            return result;
        }

}
