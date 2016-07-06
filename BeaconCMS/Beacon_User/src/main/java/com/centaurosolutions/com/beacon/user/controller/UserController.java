package com.centaurosolutions.com.beacon.user.controller;

import com.centaurosolutions.com.beacon.user.model.User;
import com.centaurosolutions.com.beacon.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.centaurosolutions.com.beacon.user.model.Preferences;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
			ArrayList<Preferences> preference = new ArrayList<Preferences>();
		if(userMap.get("preference") != null)
		{
			preference = (ArrayList<Preferences>)userMap.get("preference");
		}
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
					userMap.get("socialNetworkJson").toString(),
					userMap.get("gender").toString(),
					null,
					userMap.get("path_image").toString(),
					preference);
			
			if(LOCAL_USER == user.getSocialNetworkType())
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
				else
				{
					userRepository.save(user);
					response.put("status", 200);
					response.put("message", "User created");
					response.put("user", user);	
				}
			}
			else
			{
				/* Verifica que el usuario de red social este registrado:
				 * haciendo una verificacion por idRedSocial y el type */
				
				userExist = userRepository.findBySocialNetworkId(user.getSocialNetworkId());
				if(userExist != null)
				{
					if(userExist.getSocialNetworkType().equals(user.getSocialNetworkType()))
					{
						response.put("status", 200);
						response.put("message", "User registered");
						response.put("user", userExist);
					}
					else
					{
						userRepository.save(user);
						response.put("status", 200);
						response.put("message", "User created");
						response.put("user", user);							
					}
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
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error createUser");
			response.put("user", null);
		}
		
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/id/{userId}")
	public Map<String, Object>  getUserById(@PathVariable("userId") String userId)
	{
		User user = null;
		Map<String, Object> response = new LinkedHashMap<String, Object>();

			try
			{
				user  = userRepository.findOne(userId);;
				response.put("status", 200);
				response.put("message", "");
				response.put("user", user);
			}
			catch(Exception ex)
			{
				response.put("status", 500);
				response.put("message", ex.getMessage());
				response.put("user", null);
			}


			return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username}")
	public Map<String, Object> getUserByNameDetails(@PathVariable("username") String username)
	{
		User user = null;

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		List<User> users;

		try
		{
			user = userRepository.findByUser(username);
			response.put("status", 200);
			response.put("message", "");
			response.put("user", user);
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", ex.getMessage());
			response.put("user", null);
		}

		return response;
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
			response.put("message", "");
			response.put("listUser", users);
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", ex.getMessage());
			response.put("listUser", null);
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
			ArrayList<Preferences> preference = new ArrayList<Preferences>();
			if(userMap.get("preference") != null)
			{
				preference = (ArrayList<Preferences>)userMap.get("preference");
			}
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
				user.setGender(userMap.get("gender").toString());
				user.setPath_image(userMap.get("path_image").toString());
				user.setPreference(preference);
				user.setId(UserId);
				response.put("status", 200);
				response.put("message", "User updated");
				response.put("user", userRepository.save(user));
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
			response.put("user", null);
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
				response.put("user", userRepository.save(user));
				response.put("status", 200);
			}
			else
			{
				response.put("status", 404);
				response.put("message", "User doesn't exist, points not given");
				response.put("user", null);
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
				response.put("user", userRepository.save(user));
				response.put("status",200);
			} 
			else 
			{
				response.put("status", 404);
				response.put("message", "User not found, password not changed");
				response.put("user", null);
			}
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error editUserPassword");
			response.put("user", null);
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
	@RequestMapping(method = RequestMethod.POST, value = "/wishlist/add")
	public Map<String, Object> addProductToWishlist(@RequestBody Map<String, Object> userMap) {

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		User userExist = null;
		Boolean productIdExist = false;

		try
		{
		  if( userMap.get("productId") != null && userMap.get("userId") != null){

			  userExist =  userRepository.findOne( userMap.get("userId").toString());

			  if(userExist != null){

				  if(userExist.getProductWishList() != null){
					  for(String id : userExist.getProductWishList()){
						  if( userMap.get("productId").toString().equals(id)){
							  productIdExist = true;
							  break;
						  }
					  }
				  }
                  else{
                      userExist.setProductWishList(new ArrayList<String>());
                  }


				  if(!productIdExist){

					  userExist.getProductWishList().add(userMap.get("productId").toString());
					  userRepository.save(userExist);

					  response.put("message", "User updated");
					  response.put("user",  userExist);
					  response.put("status", 200);
				  }
				  else{
					  response.put("message", "Product already added to wishlist");
					  response.put("user", userExist);
					  response.put("status", 200);
				  }
			  }
			  else{
				  response.put("status", 404);
				  response.put("message", "User not found");
				  response.put("user", null);
			  }

		  }else{
			  response.put("status", 400);
			  response.put("message", "Missing parameters");
			  response.put("user", null);
		  }
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error addWishlist");
			response.put("user", null);
		}

		return response;
	}


	@RequestMapping(method = RequestMethod.POST,  value = "/wishlist/delete")
	public Map<String, Object> deleteProductToWishlist(@RequestBody Map<String, Object> userMap) {

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		User userExist = null;
		Boolean productIdExist = false;

		try
		{
			if( userMap.get("productId") != null && userMap.get("userId") != null){

				userExist =  userRepository.findOne( userMap.get("userId").toString());

				if(userExist != null){

                    if(userExist.getProductWishList() != null){
                        for(String id : userExist.getProductWishList()){
                            if( userMap.get("productId").toString().equals(id)){
                                productIdExist = true;
                                break;
                            }
                        }
                    }

					if(productIdExist){

						userExist.getProductWishList().remove(userMap.get("productId").toString());
						userRepository.save(userExist);

						response.put("message", "User updated");
						response.put("user",  userExist);
						response.put("status", 200);
					}
					else{
						response.put("message", "Product not found");
						response.put("user", userExist);
						response.put("status", 404);
					}
				}
				else{
					response.put("status", 404);
					response.put("message", "User not found");
					response.put("user", null);
				}

			}else{
				response.put("status", 400);
				response.put("message", "Missing parameters");
				response.put("user", null);
			}
		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", "Error deleteWishlist");
			response.put("user", null);
		}

		return response;
	}
	//-------->PathImage update
		@RequestMapping(method = RequestMethod.PUT, value = "/editPathImage/{UserId}")
		public Map<String, Object> editPathImage(
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
					user.setPath_image(userMap.get("path_image").toString());
					user.setId(UserId);
					response.put("message", "PathImage updated");
					response.put("user", userRepository.save(user));
					response.put("status",200);
				} 
				else 
				{
					response.put("status", 404);
					response.put("message", "User not found, PathImage not changed");
					response.put("user", null);
				}
			}
			catch(Exception ex)
			{
				response.put("status", 500);
				response.put("message", "Error editPathImage");
				response.put("user", null);
			}

			return response;
		}
		//------>Preferences Update
		@SuppressWarnings("unchecked")
		@RequestMapping(method = RequestMethod.PUT, value = "/editPreferences/{UserId}")
		public Map<String, Object> editPreferences(
				@PathVariable("UserId") String UserId,
				@RequestBody Map<String, Object> userMap) 
		{
			Map<String, Object> response = new LinkedHashMap<String, Object>();
			User user;
			ArrayList<Preferences> preference = new ArrayList<Preferences>();
			if(userMap.get("preference") != null)
			{
				preference = (ArrayList<Preferences>)userMap.get("preference");
			}
			
			try
			{
				user = userRepository.findById(UserId);
		
				if (user != null) 
				{
					user.setPreference(preference);
					user.setId(UserId);
					response.put("message", "Preferences updated");
					response.put("user", userRepository.save(user));
					response.put("status",200);
				} 
				else 
				{
					response.put("status", 404);
					response.put("message", "User not found, Preferences not changed");
					response.put("user", null);
				}
			}
			catch(Exception ex)
			{
				response.put("status", 500);
				response.put("message", "Error editPreferences");
				response.put("user", null);
			}

			return response;
		}
		

}
