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
	
	
	
	private Date DateFormatter(String pDate){
		   
		   Date finalDate = new Date();
		   DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS", Locale.ENGLISH);
		   try {
		    finalDate = format.parse(pDate);
		   } catch (ParseException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   }
		   
		   return finalDate;  
		  }
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createCategory(@RequestBody Map<String, Object> categoryMap){
		
		 Category category=new Category();
		 try{
		 category = new Category(Integer.parseInt(categoryMap.get("startRange").toString()),Integer.parseInt(categoryMap.get("endRange").toString()), Integer.parseInt(categoryMap.get("giftPoints").toString()) ,DateFormatter(categoryMap.get("creationDate").toString()),DateFormatter(categoryMap.get("modifieldDate").toString()),categoryMap.get("categoryName").toString(),(Boolean)categoryMap.get("enable"));
		}catch(Exception ex){}
		Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Categoría creada correctamente");
	    response.put("category", category);		
	    categoryRepository.save(category);
		return response;
	}
	
	 @RequestMapping(method = RequestMethod.GET, value="/{categoryId}")
	  public Category getCategoryDetails(@PathVariable("categoryId") String categoryId){
	    return categoryRepository.findOne(categoryId);
	  }
	 
	  @RequestMapping(method = RequestMethod.GET)
	  public Map<String, Object> getAllCategoriesDetails(){
		  List<Category> categories = categoryRepository.findAll();
		  Map<String, Object> response = new LinkedHashMap<String, Object>();
		  response.put("Total de categorías", categories.size());
		  response.put("Categories", categories);
		  return response;
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.PUT, value="/{CategoryId}")
	  public Map<String, Object> editCategory(@PathVariable("CategoryId") String CategoryId,
	      @RequestBody Map<String, Object> categoryMap){
		  Category category=new Category();
			 try{
			 category = new Category(Integer.parseInt(categoryMap.get("startRange").toString()),Integer.parseInt(categoryMap.get("endRange").toString()), Integer.parseInt(categoryMap.get("giftPoints").toString()) ,DateFormatter(categoryMap.get("creationDate").toString()),DateFormatter(categoryMap.get("modifieldDate").toString()),categoryMap.get("categoryName").toString(),(Boolean)categoryMap.get("enable"));
			}catch(Exception ex){} 
			 category.setId(CategoryId);
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Categoría actualizada correctamente");
	    response.put("Category", categoryRepository.save(category));
	    return response;
	  }
	  
	  
	  @RequestMapping(method = RequestMethod.DELETE, value="/{CategoryId}")
	  public Map<String, String> deleteCategory(@PathVariable("CategoryId") String CategoryId){
	    categoryRepository.delete(CategoryId);
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "Categoría eliminada correctamente");
	    return response;
	  }
}
