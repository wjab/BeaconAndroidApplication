package com.centaurosolutions.com.beacon.faq.controller;

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
import com.centaurosolutions.com.beacon.faq.model.Faq;
import com.centaurosolutions.com.beacon.faq.repository.BeaconFaqRepository;

@RestController
@RequestMapping("/faq")
public class BeaconFaqController {
	@Autowired
	private BeaconFaqRepository faqRepository;

	//------------------------------------CREACIOND DE UNA FAQ---------------------------------------------
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createFaq(@RequestBody Map<String, Object> faqMap) {
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{		
	
			Faq faqModel = new Faq(
					faqMap.get("section").toString(), 
					faqMap.get("question").toString(),
					faqMap.get("answer").toString());
			 faqRepository.save(faqModel);
	            response.put("message", "FAQ creada correctamente");
	            response.put("status", 200);;
	            response.put("faq", faqModel);

		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error createFaq");
			response.put("faq", null);
		}
		
		return response;
	}
	//------------------------------------OBTIENE LAS FAQ---------------------------------------------
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object>  getFaq()
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		List<Faq> faq;
			try
			{
				faq  = faqRepository.findAll();
				response.put("status", 200);
				response.put("message", "");
				response.put("faq", faq);
			}
			catch(Exception ex)
			{
				response.put("status", 500);
				response.put("message", ex.getMessage());
				response.put("faq", null);
			}


			return response;
	}
	//------------------------------------OBTIENE UNA FAQ ESPECIFICA---------------------------------------------
	//@param faqID
	@RequestMapping(method = RequestMethod.GET, value = "/id/{faqId}")
	public Map<String, Object>  getFaqById(@PathVariable("faqId") String faqId)
	{
		Faq faq = null;
		Map<String, Object> response = new LinkedHashMap<String, Object>();

			try
			{
				faq  = faqRepository.findOne(faqId);;
				response.put("status", 200);
				response.put("message", "");
				response.put("faq", faq);
			}
			catch(Exception ex)
			{
				response.put("status", 500);
				response.put("message", ex.getMessage());
				response.put("faq", null);
			}


			return response;
	}
	//------------------------------------OBTIENE LOS FAQ POR SECCION---------------------------------------------
		//@param faqID
		@RequestMapping(method = RequestMethod.GET, value = "/section/{section}")
		public Map<String, Object>  getFaqSection(@PathVariable("section") String section)
		{
			Faq faq = null;
			Map<String, Object> response = new LinkedHashMap<String, Object>();

				try
				{
					faq  = faqRepository.findBySection(section);
					response.put("status", 200);
					response.put("message", "");
					response.put("faq", faq);
				}
				catch(Exception ex)
				{
					response.put("status", 500);
					response.put("message", ex.getMessage());
					response.put("faq", null);
				}


				return response;
		}
	//------------------------------------ACTUALIZA LA PREGUNTA DE UNA FQA ESPECIFICA------------------------------------
	//@param faqID
	@RequestMapping(method = RequestMethod.PUT, value = "/updateQuestion/{faqId}")
	public Map<String, Object> updateQuestion(
			@PathVariable("faqId") String faqId,
			@RequestBody Map<String, Object> faqMap) 
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		Faq faq = null;
		
		try
		{
			faq= faqRepository.findById(faqId);
			
			if (faq != null) 
			{
				faq.setQuestion(faqMap.get("question").toString());
				faq.setId(faqId);
				response.put("message", "FAQ question updated");
				response.put("faq", faqRepository.save(faq));
				response.put("status", 200);
			}
			else
			{
				response.put("status", 404);
				response.put("message", "FAQ doesn't exist");
				response.put("faq", null);
			}			
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error updateQuestion");
		}
		
		return response;
	}
	//------------------------------------ACTUALIZA LA RESPUESTA DE UNA FAQ ESPECIFICA---------------------------------
	//@param faqID
	@RequestMapping(method = RequestMethod.PUT, value = "/updateAnswer/{faqId}")
	public Map<String, Object> updateAnswer(
			@PathVariable("faqId") String faqId,
			@RequestBody Map<String, Object> faqMap) 
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		Faq faq = null;
		
		try
		{
			faq= faqRepository.findById(faqId);
			
			if (faq != null) 
			{
				faq.setAnswer(faqMap.get("answer").toString());
				faq.setId(faqId);
				response.put("message", "FAQ answer updated");
				response.put("faq", faqRepository.save(faq));
				response.put("status", 200);
			}
			else
			{
				response.put("status", 404);
				response.put("message", "FAQ doesn't exist");
				response.put("faq", null);
			}			
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error updateAnswer");
		}
		
		return response;
	}
	
	//------------------------------------ELIMINA UNA FAQ ESPECIFICA---------------------------------
		//@param faqID
	@RequestMapping(method = RequestMethod.DELETE, value = "/{faqId}")
	public Map<String, Object> deleteFaq(@PathVariable("faqId") String faqId) 
	{
		Map<String, Object> response = new HashMap<String, Object>();
		
		try
		{
			faqRepository.delete(faqId);
			response.put("status", 200);
			response.put("message", "FAQ deleted");
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error deleteFaq");
		}

		return response;
	}

}
