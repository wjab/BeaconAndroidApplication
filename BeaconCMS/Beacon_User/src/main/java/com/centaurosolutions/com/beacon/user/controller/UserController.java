package com.centaurosolutions.com.beacon.user.controller;

import com.centaurosolutions.com.beacon.user.model.NotificationResponse;
import com.centaurosolutions.com.beacon.user.model.Preferences;
import com.centaurosolutions.com.beacon.user.model.User;
import com.centaurosolutions.com.beacon.user.model.Wish;
import com.centaurosolutions.com.beacon.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

	private final String urlNotification = "http://butilsdevel.cfapps.io/notification";
    private final String NOTIFICATION_INFO = "INFO";
	private final String DEFAULT_ENCODE = "utf-8";
	
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
		ArrayList<Wish> wishList = new ArrayList<Wish>();
		if(userMap.get("productWishList") != null)
		{
			wishList = (ArrayList<Wish>)userMap.get("productWishList");
		}
			User user = new User(
				userMap.get("user").toString(), 
					setEncryptedPassword(userMap.get("password").toString()),
					Boolean.valueOf(userMap.get("enable").toString()),
					Integer.parseInt(userMap.get("categoryId").toString()),
					Integer.parseInt(userMap.get("totalGiftPoints").toString()),
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
					wishList,
					userMap.get("pathImage").toString(),
					preference);
			
			if(LOCAL_USER.equals(user.getSocialNetworkType()))
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
						user.setTotalGiftPoints(50);
						userRepository.save(user);
						response.put("status", 200);
						response.put("message", "User created");
						response.put("user", user);

						createUserNotification(user.getId(), NOTIFICATION_INFO, "Has ganado "+ user.getTotalGiftPoints() + " por instalar QuickShop");
					}
				}
				else
				{
					user.setTotalGiftPoints(50);
					userRepository.save(user);
					response.put("status", 200);
					response.put("message", "User created");
					response.put("user", user);

					createUserNotification(user.getId(), NOTIFICATION_INFO, "Has ganado "+ user.getTotalGiftPoints() + " por instalar QuickShop");
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
						user.setTotalGiftPoints(100);
						userRepository.save(user);
						response.put("status", 200);
						response.put("message", "User created");
						response.put("user", user);

						createUserNotification(user.getId(), NOTIFICATION_INFO, "Has ganado "+ user.getTotalGiftPoints() + " por instalar QuickShop");
					}
				}
				else
				{
					user.setTotalGiftPoints(100);
					userRepository.save(user);
					response.put("status", 200);
					response.put("message", "User created");
					response.put("user", user);

					createUserNotification(user.getId(), NOTIFICATION_INFO, "Has ganado "+ user.getTotalGiftPoints() + " por instalar QuickShop");
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
				if(user!=null){
					response.put("status", 200);
					response.put("message", "");
					response.put("user", user);
				}
				else{
					response.put("status", 404);
					response.put("message", "");
					response.put("user", null);
				}
			}
			catch(Exception ex)
			{
				response.put("status", 500);
				response.put("message", ex.getMessage());
				response.put("user", null);
			}


			return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/username")
	public Map<String, Object> getUserByNameDetails(@RequestBody  Map<String, Object> userMap)
	{
		User user = null;
		Map<String, Object> response = new LinkedHashMap<String, Object>();

		try
		{
			user = userRepository.findByUser(userMap.get("username").toString());

			if(user != null){

				response.put("status", 200);
				response.put("message", "");
				response.put("user", user);
			}
			else{

				response.put("status", 404);
				response.put("message", "");
				response.put("user", null);
			}
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

			if(users != null && users.size() > 0){
				response.put("status", 200);
				response.put("message", "");
				response.put("listUser", users);
			}
			else{
				response.put("status", 404);
				response.put("message", "");
				response.put("listUser", null);
			}

		}
		catch(Exception ex)
		{
			response.put("status", 500);
			response.put("message", ex.getMessage());
			response.put("listUser", null);
		}
		
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{userId}")
	public Map<String, Object> editUser(@PathVariable("userId") String UserId,
			@RequestBody Map<String, Object> userMap) 
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		try
		{
			User user = userRepository.findById(UserId);
		
			if (user != null) 
			{
				if(userMap.get("productWishList") != null)
				{
					user.setProductWishList((ArrayList<Wish>)userMap.get("productWishList"));
				}
				if(userMap.get("preference") != null)
				{
					user.setPreference((ArrayList<Preferences>)userMap.get("preference"));
				}
				
				user.setUser(userMap.get("user").toString());
				user.setEnable(Boolean.valueOf(userMap.get("enable").toString()));
				user.setCategoryId(Integer.parseInt(userMap.get("categoryId").toString()));
				user.setTotalGiftPoints(Integer.parseInt(userMap.get("totalGiftPoints").toString()));
				user.setModifiedDate(DateFormatter(userMap.get("modifiedDate").toString()));
				user.setName(userMap.get("name").toString());
				user.setLastName(userMap.get("lastName").toString());
				user.setEmail(userMap.get("email").toString());
				user.setPhone(userMap.get("phone").toString());
				user.setGender(userMap.get("gender").toString());
				user.setPathImage(userMap.get("pathImage").toString());
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

	@RequestMapping(method = RequestMethod.PUT, value = "/setPoints/{userId}")
	public Map<String, Object> setPointsUser(
			@PathVariable("userId") String UserId,
			@RequestBody Map<String, Object> userMap) 
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		User user = null;
		
		try
		{
			user = userRepository.findById(UserId);
			
			if (user != null) 
			{
				user.setTotalGiftPoints(Integer.parseInt(userMap.get("totalGiftPoints").toString()));
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

	@RequestMapping(method = RequestMethod.PUT, value = "/changePassword/{userId}")
	public Map<String, Object> editUserPassword(
			@PathVariable("userId") String UserId,
			@RequestBody Map<String, Object> userMap) 
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		User user;
		NotificationResponse notificationResponse;
		
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

                createUserNotification(user.getId(), NOTIFICATION_INFO, "Cambio de contraseña exitoso");


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

	//----------------------------------------------Add Product to wishList-------------------------------------------------------
	
	@RequestMapping(method = RequestMethod.POST, value = "/wishlist/add")
	public Map<String, Object> addProductToWishlist(@RequestBody Map<String, Object> userMap) 
	{

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		User userExist = null;
		String userId=userMap.get("userId").toString();
		Boolean productIdExist = false;

		try
		{
		  if( userMap.get("productId") != null && userId != null){

			  userExist =  userRepository.findOne(userId);

			  if(userExist != null){

				  if(userExist.getProductWishList() != null){
					  for(Wish wish : userExist.getProductWishList()){
						  if( userMap.get("productId").toString().equals(wish.getProductId())){
							  productIdExist = true;
							  break;
						
						  }
					  }
				  }
				  
                  else{
                	  userExist.setProductWishList(new ArrayList<Wish>());
                      }
				  
				  if(!productIdExist){
					  Wish wishList=new Wish();
					  wishList.setProductId(userMap.get("productId").toString());
					  wishList.setProductName(userMap.get("productName").toString());
					  wishList.setPrice(Integer.parseInt(userMap.get("price").toString()));
					  wishList.setImageUrlList(userMap.get("imageUrlList").toString());
					  userExist.getProductWishList().add(wishList);
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
			response.put("message","Error");
			response.put("user", null);
		}

		return response;
	}

//---------------------------------Delete product from wishlist-------------------------------------------------
	@RequestMapping(method = RequestMethod.POST,  value = "/wishlist/delete")
	public Map<String, Object> deleteProductToWishlist(@RequestBody Map<String, Object> userMap) {

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		User userExist = null;
		Boolean productIdExist = false;
		 Wish wishList = null;
		try
		{
			if( userMap.get("productId") != null && userMap.get("userId") != null){

				userExist =  userRepository.findOne( userMap.get("userId").toString());

				if(userExist != null){

                    if(userExist.getProductWishList() != null){
                    	 for(Wish wish : userExist.getProductWishList()){
   						  if( userMap.get("productId").toString().equals(wish.getProductId())){
   							  productIdExist = true;
   							  wishList=wish;
   							  break;
   						  }
                        }
                    }

					if(productIdExist){
						
						 
						userExist.getProductWishList().remove(wishList);
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
	//-----------------------------------------------------Add preference-----------------------------------------------------

	@RequestMapping(method = RequestMethod.POST, value = "/preference/add")
	public Map<String, Object> addPreference(@RequestBody Map<String, Object> userMap) 
	{

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		User userExist = null;
		String userId=userMap.get("userId").toString();
		Boolean preferenceExist = false;

		try
		{
		  if( userMap.get("preference") != null && userId != null && userMap.get("state") != null){

			  userExist =  userRepository.findOne(userId);

			  if(userExist != null){

				  if(userExist.getPreference() != null){
					  for(Preferences preference : userExist.getPreference()){
						  if( 
								userMap.get("preference").toString().equals(preference.getPreference())){
							  preferenceExist = true;
							  break;
						
						  }
					  }
				  }
                  else{
                	  userExist.setPreference(new ArrayList<Preferences>());
                      
                  }


				  if(!preferenceExist){
					  Preferences preferenceList=new Preferences();
					  preferenceList.setPreference(userMap.get("preference").toString());
					  preferenceList.setState(userMap.get("state").toString());
					
					  userExist.getPreference().add(preferenceList);
					  userRepository.save(userExist);

					  response.put("message", "User updated");
					  response.put("user",  userExist);
					  response.put("status", 200);
				  }
				  else{
					  response.put("message", "Preference already added to preferences");
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
			response.put("message","Error");
			response.put("user", null);
		}

		return response;
	}
	
	//---------------------------------Delete preference-------------------------------------------------
	@RequestMapping(method = RequestMethod.POST,  value = "/preference/delete")
	public Map<String, Object> deletePreference(@RequestBody Map<String, Object> userMap) {

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		User userExist = null;
		Boolean preferenceExist = false;
		String userId=userMap.get("userId").toString();
		 Preferences preferenceList = null;
		try
		{
			  if( userMap.get("preference") != null && userId != null && userMap.get("state") != null){

				userExist =  userRepository.findOne( userMap.get("userId").toString());

				if(userExist != null){

                    if(userExist.getProductWishList() != null){
                    	 for(Preferences preference : userExist.getPreference()){
   						  if( userMap.get("preference").toString().equals(preference.getPreference())){
   							preferenceExist = true;
   							preferenceList=preference;
   							  break;
   						  }
                        }
                    }

					if(preferenceExist){
						
						 
						userExist.getPreference().remove(preferenceList);
						userRepository.save(userExist);

						response.put("message","User updated");
						response.put("user",  userExist);
						response.put("status", 200);
					}
					else{
						response.put("message", "Preference not found");
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
			response.put("message", "Error deletePreference");
			response.put("user", null);
		}

		return response;
	}
	
	//-------->PathImage update--------------------------------------------------------------------------------
	
	@RequestMapping(method = RequestMethod.PUT, value = "/editPathImage/{userId}")
	public Map<String, Object> editPathImage(
			@PathVariable("userId") String UserId,
			@RequestBody Map<String, Object> userMap) 
	{
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		User user;
		
		try
		{
			user = userRepository.findById(UserId);
	
			if (user != null) 
			{
				user.setPathImage(userMap.get("pathImage").toString());
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
		
	//-------------------------------Update Preference espefica---------------------------------------------
	@RequestMapping(method = RequestMethod.POST,  value = "/preference/editState")
	public Map<String, Object> editStatePreference(@RequestBody Map<String, Object> userMap) {

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		User userExist = null;
		Boolean preferenceExist = false;
		String userId=userMap.get("userId").toString();
		 Preferences preferenceList = null;
		 Preferences newPreference=null;
		try
		{
			  if( userMap.get("preference") != null && userId != null && userMap.get("state") != null){

				userExist =  userRepository.findOne( userMap.get("userId").toString());

				if(userExist != null){

                    if(userExist.getProductWishList() != null){
                    	 for(Preferences preference : userExist.getPreference()){
   						  if( userMap.get("preference").toString().equals(preference.getPreference())){
   							preferenceExist = true;
   							preferenceList=preference;
   							newPreference=preference;
   							newPreference.setState(userMap.get("state").toString());
   							  break;
   						  }
                        }
                    }

					if(preferenceExist){
						
						 
						userExist.getPreference().remove(preferenceList);
						userExist.getPreference().add(newPreference);
						userRepository.save(userExist);

						response.put("message","User updated");
						response.put("user",  userExist);
						response.put("status", 200);
					}
					else{
						response.put("message", "Preference not found");
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
			response.put("message", "Error editStatePreference");
			response.put("user", null);
		}

		return response;
	}
	
	//------>Preferences Update------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.PUT, value = "/editPreferences/{userId}")
	public Map<String, Object> editPreferences(
			@PathVariable("userId") String UserId,
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


	public NotificationResponse createUserNotification(String userId, String type, String message){

		NotificationResponse notificationResponse =  null;
		Map<String, Object> mainEntity = new LinkedHashMap<String, Object>();

		try
		{

			mainEntity.put("userId", userId);
			mainEntity.put("message", message);
			mainEntity.put("type", type);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LinkedHashMap<String, Object>> entity = new HttpEntity(mainEntity , headers);
			ResponseEntity<NotificationResponse> out = restTemplate.exchange(urlNotification , HttpMethod.POST, entity , NotificationResponse.class);
			notificationResponse =  out.getBody();
		}
		catch(Exception ex)
		{

		}

		return notificationResponse;

	}

		

}
