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
import java.util.Map;
import java.util.TimeZone;

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

	private String LOCAL_USER = "localuser";
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createUser(@RequestBody Map<String, Object> userMap) {
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		User userExist;
		
		try
		{		
			User user = new User(
					userMap.get("user").toString(), 
					setEncryptedPassword(userMap.get("password").toString()),
					Boolean.valueOf(userMap.get("enable").toString()),
					Integer.parseInt(userMap.get("category_id").toString()),
					Integer.parseInt(userMap.get("total_gift_points").toString()),
					DateFormatter(userMap.get("creationDate").toString()),
					DateFormatter(userMap.get("modifiedDate").toString()), 
					userMap.get("name").toString(),
					userMap.get("lastName").toString(), 
					userMap.get("email").toString(), 
					userMap.get("phone").toString(),
					userMap.get("socialNetworkId").toString(), 
					userMap.get("socialNetworkType").toString(),
					userMap.get("socialNetworkJson").toString());
			
			if(LOCAL_USER != user.getSocialNetworkType())
			{
				/* Se aplican las validaciones para ver que el usuario no
				 * exista caso: Usuario con mismo telefono o Correo */
				
				userExist = userRepository.findByPhone(user.getPhone());
				if(userExist != null)
				{
					if(userExist.getEmail().equals(user.getEmail()))
					{
						response.put("status", 400);
						response.put("message", "User exist");
						response.put("user", null);
					}
					else
					{
						userRepository.save(user);
						response.put("status", 200);
						response.put("message", "User created");
						response.put("user", user);							
					}
				}
			}
			else
			{
				/* Verifica que el usuario de red social este registrado:
				 * haciendo una verificacion por idRedSocial y el type */
				
				userExist = userRepository.findBySocialNetworkType(user.getSocialNetworkType());
				if(userExist != null)
				{
					if(userExist.getSocialNetworkId().equals(user.getSocialNetworkId()))
					{
						response.put("status", 200);
						response.put("message", "User registered");
						response.put("user", user);
					}
					else
					{
						userRepository.save(user);
						response.put("status", 200);
						response.put("message", "User created");
						response.put("user", user);							
					}
				}
			}			
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error createUser");
			response.put("user", null);
		}
		
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/id/{userId}")
	public User getUserById(@PathVariable("userId") String userId) 
	{
		User user = null;
		
		try
		{
			user = userRepository.findOne(userId);
		}
		catch(Exception ex)
		{ }
		
		return user;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username}")
	public User getUserByNameDetails(@PathVariable("username") String username) 
	{
		User user = null;
		
		try
		{
			user = userRepository.findByUser(username);
		}
		catch(Exception ex)
		{ }
		
		return user;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getAllUserDetails() 
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		List<User> users;
		
		try
		{
			users = userRepository.findAll();	
			response.put("status", 200);
			response.put("message", "AllUserDetails got");
			response.put("userCount", users.size());
			response.put("users", users);
		}
		catch(Exception ex)
		{
			response.put("status", 200);
			response.put("message", "Error getAllUserDetails");
			response.put("userCount", 0);
			response.put("Users", null);
		}
		
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{UserId}")
	public Map<String, Object> editUser(@PathVariable("UserId") String UserId,
			@RequestBody Map<String, Object> userMap) 
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			User user = userRepository.findById(UserId);
	
			if (user != null) 
			{
				user.setUser(userMap.get("user").toString());
				user.setEnable(Boolean.valueOf(userMap.get("enable").toString()));
				user.setCategory_id(Integer.parseInt(userMap.get("category_id").toString()));
				user.setTotal_gift_points(Integer.parseInt(userMap.get("total_gift_points").toString()));
				user.setModifiedDate(DateFormatter(userMap.get("modifiedDate").toString()));
				user.setName(userMap.get("name").toString());
				user.setLastName(userMap.get("lastName").toString());
				user.setEmail(userMap.get("email").toString());
				user.setPhone(userMap.get("phone").toString());
				user.setId(UserId);
	
				response.put("status", 200);
				response.put("message", "User updated");
				response.put("User", userRepository.save(user));
			} 
			else 
			{
				response.put("status", 404);
				response.put("message", "User not found");
			}
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error editUser");
			response.put("User", null);
		}
		
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/setPoints/{UserId}")
	public Map<String, Object> setPointsUser(
			@PathVariable("UserId") String UserId,
			@RequestBody Map<String, Object> userMap) 
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		User user = null;
		
		try
		{
			user = userRepository.findById(UserId);
			
			if (user != null) 
			{
				user.setTotal_gift_points(Integer.parseInt(userMap.get("total_gift_points").toString()));
				user.setId(UserId);
				response.put("message", "User updated");
				response.put("User", userRepository.save(user));
			}
			else
			{
				response.put("status", 404);
				response.put("message", "User doesn't exist, points not given");
				response.put("User", null);
			}			
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error setPointsUser");
		}
		
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/changePassword/{UserId}")
	public Map<String, Object> editUserPassword(
			@PathVariable("UserId") String UserId,
			@RequestBody Map<String, Object> userMap) 
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		User user;
		
		try
		{
			user = userRepository.findById(UserId);
	
			if (user != null) 
			{
				user.setPassword(setEncryptedPassword(userMap.get("password").toString()));
				user.setId(UserId);
				response.put("message", "Password updated");
				response.put("User", userRepository.save(user));	
			} 
			else 
			{
				response.put("status", 404);
				response.put("message", "User not found, password not changed");
				response.put("User", null);
			}
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error editUserPassword");
			response.put("User", null);
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
	public Map<String, Object> deleteUser(@PathVariable("userId") String userId) 
	{
		Map<String, Object> response = new HashMap<String, Object>();
		
		try
		{
			userRepository.delete(userId);
			response.put("status", 200);
			response.put("message", "User deleted");
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error deleteUser");
		}

		return response;
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

	private String setEncryptedPassword(String password) 
	{
		MessageDigest md;
		StringBuffer sb = new StringBuffer();

		try 
		{
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();

			for (byte b : digest) 
			{
				sb.append(String.format("%02x", b & 0xff));
			}
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}

		return sb.toString();

	}

}
