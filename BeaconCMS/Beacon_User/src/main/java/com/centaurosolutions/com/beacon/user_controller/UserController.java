package com.centaurosolutions.com.beacon.user_controller;

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

import com.centaurosolutions.com.beacon.user_repository.UserRepository;
import com.centaurosolutions.com.beacon.user_model.*;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	//Esta vara es una picha. Me cago en github
	
	/*@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createUser(@RequestBody Map<String, Object> userMap){
		
		User user = new User(
				userMap.get("user").toString(), 
				userMap.get("password").toString(),
				(Boolean)userMap.get("enable"),
				Integer.parseInt(userMap.get("category_id").toString()),
				Integer.parseInt(userMap.get("total_gift_points").toString()),
				userMap.get("creationDate").toString(),
				userMap.get("modifiedDate").toString());
		
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Usuario creado correctamente");
	    response.put("user", user);
		
		userRepository.save(user);
		return response;
	}*/
	@RequestMapping(method = RequestMethod.POST)
	public String createUser(@RequestBody Map<String, Object> userMap)
	{		    
		return "jojojo";
	}
	
	public User getUserDetails(@PathVariable("userId") String userId){
	    return userRepository.findOne(userId);
	}
	  
	public Map<String, Object> getAllUserDetails(){
		List<User> users = userRepository. findAll();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("Total de Usuarios", users.size());
		response.put("Users", users);
		return response;
	}
	  
	  
	@RequestMapping(method = RequestMethod.PUT, value="/{UserId}")
	public Map<String, Object> editUser(@PathVariable("UserId") String UserId, @RequestBody Map<String, Object> userMap)
	{
		User user = new User(
				userMap.get("user").toString(), 
	    		userMap.get("password").toString(),
	    		(Boolean)userMap.get("enable"),
	    		Integer.parseInt(userMap.get("category_id").toString()),
	    		Integer.parseInt(userMap.get("total_gift_points").toString()),
	    		userMap.get("creationDate").toString(),
	    		userMap.get("modifiedDate").toString());
	    
	    user.setId(UserId);
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Usuario actualizado correctamente");
	    response.put("User", userRepository.save(user));
	    return response;
	}
	  
	  
	@RequestMapping(method = RequestMethod.DELETE, value="/{userId}")
	public Map<String, String> deleteUser(@PathVariable("userId") String userId){
	    userRepository.delete(userId);
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "Usuario eliminado correctamente");

	    return response;
	}
}
