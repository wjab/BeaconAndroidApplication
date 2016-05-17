package com.centaurosolutions.com.beacon.category.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centaurosolutions.com.beacon.category.model.Category;
import com.centaurosolutions.com.beacon.category.repository.CategoryRepository;
@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
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
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createCategory(@RequestBody Map<String, Object> categoryMap){
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		try
		{
			Category category=new Category();
			Category category_Find=categoryRepository.findByCategoryName(categoryMap.get("categoryName").toString());
			if (category_Find == null)
			{
		       category = new Category(Integer.parseInt(categoryMap.get("startRange").toString()),
		    		   Integer.parseInt(categoryMap.get("endRange").toString()),
					   Integer.parseInt(categoryMap.get("giftPoints").toString()),
					   DateFormatter(categoryMap.get("creationDate").toString()),
					   DateFormatter(categoryMap.get("modifieldDate").toString()),
					   categoryMap.get("categoryName").toString(),
					   Boolean.valueOf(categoryMap.get("enable").toString()));
		       
		       categoryRepository.save(category);
		       response.put("message", "Categoría creada correctamente");
		       response.put("category", category);
		       response.put("status", "200");
		      
			}
			else
			{
		       response.put("message", "Categoría ya existe");
		       response.put("category", null);
		       response.put("status", "400"); 
			}
		}
		catch(Exception ex)
		{
		    response.put("message", ex.getMessage());
		    response.put("category", null);
		    response.put("status", "400"); 
		}		
		return response;  
	}
	
	 @RequestMapping(method = RequestMethod.GET, value="/{categoryId}")
	 public Map<String, Object> getCategoryDetails(@PathVariable("categoryId") String categoryId)
	 {
		 Map<String, Object> response = new LinkedHashMap<String, Object>();
		 
		 try
		 {
			  Category category_Find;
			  category_Find= categoryRepository.findOne(categoryId);
			  if (category_Find!=null) 
			  {
				  response.put("message", "Categoria encontrada");
				  response.put("category", category_Find);
				  response.put("status", "200"); 
			  }
			  else
			  {
				  response.put("message", "Categoria no encontrada");
				  response.put("category", null);
				  response.put("status", "404"); 
			  }			  
		 } 
		 catch(Exception ex)
		 {
		     response.put("message", ex.getMessage());
			 response.put("category", null);
			 response.put("status", "400"); 
		 }
		 return response;
	  }
	 
	  @RequestMapping(method = RequestMethod.GET)
	  public Map<String, Object> getAllCategoriesDetails()
	  {
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  
		  try
		  {
		      List<Category> categories = categoryRepository.findAll();		 
		      response.put("Total de categorías", categories.size());
		      response.put("Categories", categories);
		      response.put("status", "200"); 
		  }
		  catch(Exception ex)
		  {
			  response.put("message", ex.getMessage());
			  response.put("category", null);
			  response.put("status", "400"); 
		  }
		  return response;
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.PUT, value="/{CategoryId}")
	  public Map<String, Object> editCategory(@PathVariable("CategoryId") String CategoryId,
	      @RequestBody Map<String, Object> categoryMap)
	  {
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  
		  try 
		  {
	         Category category=new Category();
			 category = new Category(Integer.parseInt(categoryMap.get("startRange").toString()),Integer.parseInt(categoryMap.get("endRange").toString()), Integer.parseInt(categoryMap.get("giftPoints").toString()) ,DateFormatter(categoryMap.get("creationDate").toString()),DateFormatter(categoryMap.get("modifieldDate").toString()),categoryMap.get("categoryName").toString(),(Boolean)categoryMap.get("enable"));
			 category.setId(CategoryId);	  
			 response.put("message", "Categoría actualizada correctamente");
			 response.put("Category", categoryRepository.save(category));
			 response.put("status", "200"); 
		  }
		  catch(Exception ex)
		  {
			  response.put("message", ex.getMessage());
			  response.put("category", null);
			  response.put("status", "400"); 
		  }
	      return response;
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.DELETE, value="/{CategoryId}")
	  public Map<String, String> deleteCategory(@PathVariable("CategoryId") String CategoryId)
	  {
	      Map<String, String> response = new HashMap<String, String>();
		  try 
		  {
		      categoryRepository.delete(CategoryId);
		      response.put("message", "Categoría eliminada correctamente");
	      }
		  catch(Exception ex)
		  {
			  response.put("message", ex.getMessage());
			  response.put("category", null);
			  response.put("status", "400"); 
		  }
		  return response;
	 }
}
