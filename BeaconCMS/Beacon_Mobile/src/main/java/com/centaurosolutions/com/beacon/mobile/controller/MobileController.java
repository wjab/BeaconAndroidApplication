/**
 * 
 */
package com.centaurosolutions.com.beacon.mobile.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centaurosolutions.com.beacon.mobile.model.Mobile;
import com.centaurosolutions.com.beacon.mobile.repository.MobileRepository;

/**
 * @author Eduardo
 *
 */

@RestController
@RequestMapping("/mobile")
public class MobileController {
	
    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
	
	@Autowired
	private MobileRepository mobileRepository;
	

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createMobile(@RequestBody Map<String, Object> mobileMap){
		
		Mobile mobileModel = new Mobile(mobileMap.get("deviceName").toString(),mobileMap.get("deviceModel").toString(),mobileMap.get("osName").toString(), mobileMap.get("osVersion").toString(),mobileMap.get("userId").toString(), DateFormatter(mobileMap.get("registered").toString()));
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Dispositivo Móvil creado correctamente");
	    response.put("mobile", mobileModel); 
		
	    mobileRepository.save(mobileModel);
		return response;
	}
	
	  @RequestMapping(method = RequestMethod.GET, value="/{MobileId}")
	  public Mobile getMobileDetails(@PathVariable("MobileId") String MobileId){
	    return mobileRepository.findOne(MobileId);
	  }
	  
	  @RequestMapping(method = RequestMethod.GET)
	  public Map<String, Object> getAllPromoDetails(){
		  List<Mobile> mobileModelList = mobileRepository.findAll();
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  response.put("Total de dispositivos móviles", mobileModelList.size());
		  response.put("MobileList", mobileModelList);
		  return response;
	  }
	  
	  @RequestMapping(method = RequestMethod.PUT, value="/{MobileId}")
	  public Map<String, Object> editMobile(@PathVariable("MobileId") String MobileId,
	      @RequestBody Map<String, Object> mobileMap){

		Mobile mobileModel = new Mobile(mobileMap.get("deviceName").toString(),mobileMap.get("deviceModel").toString(),mobileMap.get("osName").toString(), mobileMap.get("osVersion").toString(),mobileMap.get("userId").toString(), DateFormatter(mobileMap.get("registered").toString()));
		mobileModel.setId(MobileId);
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Dispositivo Móvil actualizado correctamente");
	    response.put("mobile", mobileRepository.save(mobileModel));
	    return response;
	  }
	
	  @RequestMapping(method = RequestMethod.DELETE, value="/{MobileId}")
	  public Map<String, String> deleteMobile(@PathVariable("MobileId") String MobileId){
	    mobileRepository.delete(MobileId);
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "Dispositivo Móvil eliminado correctamente");

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
		
}

		
		
		
		