package com.centaurosolutions.com.beacon.user.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import com.centaurosolutions.com.beacon.user.model.*;
import com.centaurosolutions.com.beacon.user.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createUser(@RequestBody Map<String, Object> userMap){
		
		User user = new User(
				userMap.get("user").toString(), 
	    		setEncryptedPassword(userMap.get("password").toString()),
	    		(Boolean)userMap.get("enable"),
	    		Integer.parseInt(userMap.get("category_id").toString()),
	    		Integer.parseInt(userMap.get("total_gift_points").toString()),
	    		DateFormatter(userMap.get("creationDate").toString()),
	    		DateFormatter(userMap.get("modifiedDate").toString()), 
	    		userMap.get("name").toString(), 
	    		userMap.get("lastName").toString(),
	    		userMap.get("email").toString(),
	    		userMap.get("phone").toString());
		
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Usuario creado correctamente");
	    response.put("user", user);
		
		userRepository.save(user);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/id/{userId}")
	public User getUserById(@PathVariable("userId") String userId){

	    return userRepository.findOne(userId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{username}")
	public User getUserByNameDetails(@PathVariable("username") String username){

	    return userRepository.findByUser(username);
	}
	  
	  
	@RequestMapping(method = RequestMethod.GET)
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
		User user = userRepository.findById(UserId);
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		if(user != null){
			user.setUser(userMap.get("user").toString());
			user.setEnable((Boolean)userMap.get("enable"));
			user.setCategory_id(Integer.parseInt(userMap.get("category_id").toString()));
			user.setTotal_gift_points(Integer.parseInt(userMap.get("total_gift_points").toString()));
			user.setModifiedDate(DateFormatter(userMap.get("modifiedDate").toString()));
			user.setName(userMap.get("name").toString()); 
			user.setLastName(userMap.get("lastName").toString());
			user.setEmail(userMap.get("email").toString());
			user.setPhone(userMap.get("phone").toString());
		    user.setId(UserId);

		    response.put("message", "Usuario actualizado correctamente");
		    response.put("User", userRepository.save(user));
		}	    
		else			
		{
			response.put("message", "El usuario no existe");
		}
	    return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/setPoints/{UserId}")
	public Map<String, Object> setPointsUser(@PathVariable("UserId") String UserId, @RequestBody Map<String, Object> userMap)
	{
		User user = userRepository.findById(UserId);
		
		if(user != null){
			user.setTotal_gift_points(Integer.parseInt(userMap.get("total_gift_points").toString()));
		    user.setId(UserId);
		}

	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("message", "Usuario actualizado correctamente");
	    response.put("User", userRepository.save(user));
	    return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/changePassword/{UserId}")
	public Map<String, Object> editUserPassword(@PathVariable("UserId") String UserId, @RequestBody Map<String, Object> userMap)
	{
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
		User user = userRepository.findById(UserId);
		
		if(user != null){
			
			user.setPassword(setEncryptedPassword(userMap.get("password").toString()));
			user.setUser(UserId);
		    response.put("message", "Password de usuario actualizado correctamente");
		    response.put("User", userRepository.save(user));
		    
		}
		else{
			response.put("message", "Usuario no encontrado");
		}

	    return response;
	}
	  
	  
	@RequestMapping(method = RequestMethod.DELETE, value="/{userId}")
	public Map<String, String> deleteUser(@PathVariable("userId") String userId){
	    userRepository.delete(userId);
	    Map<String, String> response = new HashMap<String, String>();
	    response.put("message", "Usuario eliminado correctamente");

	    return response;
	}
	
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

	private String setEncryptedPassword(String password){

		MessageDigest md;
		StringBuffer sb = new StringBuffer();
		
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();

			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();

	}
}
