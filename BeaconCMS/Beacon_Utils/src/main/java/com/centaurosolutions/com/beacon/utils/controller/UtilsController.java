package com.centaurosolutions.com.beacon.utils.controller;

import com.centaurosolutions.com.beacon.utils.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/utils")
public class UtilsController
{

	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
	private String urlUser = "http://buserdevel.cfapps.io/user/";
	private String urlPromo = "http://bpromodevel.cfapps.io/promo/";
	private String urlProduct = "http://bmerchantproductdevel.cfapps.io/merchantproduct/";
	private String urlMerchant = "http://bmerchantprofiledevel.cfapps.io/merchantprofile/";
	private String urlOfferHistory = "http://bofferhistorydevel.cfapps.io/offerhistory/";
	private String urlPoints = "http://bpointsdevel.cfapps.io/points/";
	private String offerHistoryAttemptResource = "/getAttempts/user/%s/promo/%s";
	private final String STATUS_ACTIVE = "ACTIVE";
	private final String STATUS_EXPIRED = "EXPIRED";
	private final String STATUS_REDEEMED = "REDEEMED";


	@RequestMapping(method = RequestMethod.POST, value="/savePoints")
	public Map<String, Object> createUser(@RequestBody Map<String, Object> customMap){

		// Attributes
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		DateDiffValues dateDiffInfo = null;
		Date dateNow = new Date();
		int points = 0;
		User userObject = null;
		UserResponse userResponse = null;
		OfferHistoryAttemptResponse offerHistoryAttemptResponse = null;
		OfferHistoryAttempt offerHistoryAttempt = null;
		Promo promoObject  = null;
		PromoResponse promoResponse = null;
		RestTemplate restTemplate = new RestTemplate();

		try
		{
			offerHistoryAttemptResponse = restTemplate.getForObject(
					urlOfferHistory + String.format(
							offerHistoryAttemptResource,
							customMap.get("userId").toString(),
							customMap.get("promoId")),
					OfferHistoryAttemptResponse.class);

			promoResponse = restTemplate.getForObject( urlPromo + "" + customMap.get("promoId").toString(), PromoResponse.class);

			if(promoResponse.getStatus() == 200 && offerHistoryAttemptResponse.getStatus() == 200 )
			{
				offerHistoryAttempt = offerHistoryAttemptResponse.getAttemptData();
				promoObject = promoResponse.getPromo();

				dateDiffInfo = getDateDiffCall(
						format.format(offerHistoryAttempt.getLastScan()).toString(),
						format.format(dateNow));

				/***Verificar si es la primera vez en registrar una promoción en el historial de ofertas***/
				if (offerHistoryAttempt.getAttempts() == 0 ||
						(offerHistoryAttempt.getAttempts() < promoObject.getAttempt() &&
								(dateNow.after(promoObject.getStartDate()) && promoObject.getEndDate().after(dateNow)) &&
								dateDiffInfo.getHours() > promoObject.getInterval()))
				{
					userResponse = restTemplate.getForObject(urlUser + "id/" + customMap.get("userId").toString(), UserResponse.class);

					if (userResponse.getStatus() == 200)
					{
						userObject = userResponse.getUser();
						points = userObject.getTotalGiftPoints() + promoObject.getGiftPoints();
						userObject.setTotalGiftPoints(points);

						if (setUserPoints(userObject) && setUserPromoOffer(userObject.getId(), promoObject.getId()))
						{
							response.put("user", userObject);
							response.put("status", 200);
							response.put("message", "Puntos asignados correctamente");
						}
					}
					else
					{
						response.put("user", null);
						response.put("status", 404);
						response.put("message", "Usuario no encontrado");
					}
				}
				else
				{
					response.put("user", null);
					response.put("status", 400);
					response.put("message", "El usuario ha superado el límite de escaneos y/o el intervalo de escaneo no se ha cumplido");
				}
			}
			else
			{
				response.put("user", null);
				response.put("status", 400);
				response.put("message", "Error inesperado obteniendo datos de usuario y promociones");
			}
		}
		catch(Exception ex)
		{
			response.put("user", null);
			response.put("status", 500);
			response.put("message", ex.getMessage());
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value="/exchangePoints")
	public Map<String, Object> exchangePoints(@RequestBody Map<String, Object> customMap){

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		Points pointsModel = new Points();
		PointsResponse pointsResponse = new PointsResponse();
		User userObject = null;
		UserResponse userResponse = null;
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		int points = 0;


		try{
			if((customMap.get("userId") != null && !customMap.get("userId").toString().isEmpty()) &&
					(customMap.get("points") != null && !customMap.get("points").toString().isEmpty())){

				String x = urlUser + "id/" + customMap.get("userId").toString();
				userResponse = restTemplate.getForObject(urlUser + "id/" + customMap.get("userId").toString(), UserResponse.class);

				points = Integer.parseInt(customMap.get("points").toString());

				if(userResponse.getStatus() == 200){

					if(points <= userResponse.getUser().getTotalGiftPoints()){

						pointsResponse = generateCodeCall(customMap.get("userId").toString(), Integer.parseInt(customMap.get("points").toString()));
						pointsModel = mapper.convertValue(pointsResponse.getPoints(), new TypeReference<Points>() { });

						if(pointsResponse.getStatus() == 200) {

							userObject = userResponse.getUser();
							userObject.setTotalGiftPoints(userObject.getTotalGiftPoints() - points);

							if (setUserPoints(userObject)){
								response.put("points", pointsModel);
								response.put("status", 200);
								response.put("message", "Saldo flotante creado");
							}
							else{
								response.put("points", pointsModel);
								response.put("status", 400);
								response.put("message", "El usuario no tiene saldo para regalar puntos");
							}
						}
						else{
							response.put("points", null);
							response.put("status", 400);
							response.put("message", "Error durante la generación de código");
						}
					}
					else{
						response.put("points", null);
						response.put("status", 404);
						response.put("message", "El usuario no tiene saldo para regalar puntos");
					}
				}
				else{
					response.put("points", null);
					response.put("status", 400);
					response.put("message", "El usuario no existe");
				}
			}
			else{
				response.put("points", null);
				response.put("status", 404);
				response.put("message", "Faltan parámetros");
			}
		}
		catch (Exception ex){
			response.put("points", null);
			response.put("status", 500);
			response.put("message", ex.getMessage());
		}


		return response;
	}


	@RequestMapping(method = RequestMethod.POST, value="/redeemPoints")
	public Map<String, Object> redeemPoints(@RequestBody Map<String, Object> customMap){

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		Points pointsModel = new Points();
		PointsResponse pointsResponse = new PointsResponse();
		User userObject = null;
		UserResponse userResponse = null;
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		int points = 0;


		try{
			if((customMap.get("userId") != null && !customMap.get("userId").toString().isEmpty()) &&
					(customMap.get("code") != null && !customMap.get("code").toString().isEmpty())){

				String x = urlUser + "id/" + customMap.get("userId").toString();
				userResponse = restTemplate.getForObject(urlUser + "id/" + customMap.get("userId").toString(), UserResponse.class);

				if(userResponse.getStatus() == 200){

					pointsResponse = redeemPointsCall(customMap.get("userId").toString(), customMap.get("code").toString());
					pointsModel = mapper.convertValue(pointsResponse.getPoints(), new TypeReference<Points>() { });

					if(pointsResponse.getStatus() == 200) {

						points = pointsModel.getPoints();

						userObject = userResponse.getUser();
						userObject.setTotalGiftPoints(userObject.getTotalGiftPoints() + points);

						if (setUserPoints(userObject)){
							response.put("user", userObject);
							response.put("status", 200);
							response.put("message", null);
						}
						else{
							response.put("user", pointsModel);
							response.put("status", 400);
							response.put("message", "Error durante la actualización del usuario");
						}
					}
					else{
						response.put("user", null);
						response.put("status", 400);
						response.put("message", "Código invalido");
					}
				}
				else{
					response.put("user", null);
					response.put("status", 400);
					response.put("message", "El usuario no existe");
				}
			}
			else{
				response.put("user", null);
				response.put("status", 404);
				response.put("message", "Faltan parámetros");
			}
		}
		catch (Exception ex){
			response.put("user", null);
			response.put("status", 500);
			response.put("message", ex.getMessage());
		}


		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value="/getDateDiff")
	public Map<String, Object> getAccurateDateDifference(@RequestBody Map<String, Object> customMap){
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		Map<String, Object> dateDiffValues = new LinkedHashMap<>();

		Date d1 = null;
		Date d2 = null;
		long diff = 0;
		long diffSeconds = 0;
		long diffMinutes = 0;
		long diffHours = 0;
		long diffDays = 0;
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		try
		{
			if(customMap.get("initialDate") != null && customMap.get("finalDate") != null )
			{
				d1 = format.parse(customMap.get("initialDate").toString());
				d2 = format.parse(customMap.get("finalDate").toString());

				//in seconds
				diff = (d2.getTime() / 1000) - (d1.getTime() / 1000) ;

				if( diff > 0 )
				{
					diffSeconds = diff / 1000 % 60;
					diffMinutes = diff / (60 * 1000) % 60;
					diffHours = (diff / (60 * 60) % 24) + (diff / (24 * 60 * 60)) * 24;;
					diffDays = diff / (24 * 60 * 60 * 1000);

					dateDiffValues.put("days", diffDays);
					dateDiffValues.put("hours", diffHours);
					dateDiffValues.put("minutes", diffMinutes);
					dateDiffValues.put("seconds", diffSeconds);

					response.put("message", "Resultados de diferencia de fechas");
					response.put("status", 200);
					response.put("dateDiffValues", dateDiffValues);

				}
				else
				{
					response.put("message", "La fecha de inicio es mayor a la fecha final");
					response.put("status", 400);
					response.put("dateDiffValues", null);
				}
			}
			else
			{
				response.put("message", "Parámetros inválidos");
				response.put("status", 400);
				response.put("dateDiffValues", null);
			}

		}
		catch (Exception ex)
		{
			response.put("message", ex.getMessage());
			response.put("status", 500);
			response.put("dateDiffValues", null);
		}

		return response;
	}

	public boolean setUserPoints(User userObject)
	{
		boolean setUserPoint = false;

		try
		{
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<User> entity = new HttpEntity<User>(userObject , headers);
			ResponseEntity<UserResponse> out = restTemplate.exchange(urlUser +"setPoints/" + userObject.getId(), HttpMethod.PUT, entity , UserResponse.class);
			setUserPoint = true;
		}
		catch(Exception ex)
		{
			setUserPoint = false;
		}

		return setUserPoint;
	}

	public PointsResponse generateCodeCall(String userId, int points)
	{
		PointsResponse pointsModel =  null;
		Map<String, Object> mainEntity = new LinkedHashMap<String, Object>();

		try
		{
			mainEntity.put("userId", userId);
			mainEntity.put("points", points);
			mainEntity.put("type", "");

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LinkedHashMap<String, Object>> entity = new HttpEntity(mainEntity , headers);
			ResponseEntity<PointsResponse> out = restTemplate.exchange(urlPoints, HttpMethod.POST, entity , PointsResponse.class);
			pointsModel =  out.getBody();
		}
		catch(Exception ex)
		{

		}

		return pointsModel;
	}
	public PointsResponse redeemPointsCall(String userId, String code)
	{
		PointsResponse pointsModel =  null;
		Map<String, Object> mainEntity = new LinkedHashMap<String, Object>();

		try
		{
			mainEntity.put("code", code);
			mainEntity.put("redeemedByUserId", userId);
			mainEntity.put("status", STATUS_REDEEMED);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LinkedHashMap<String, Object>> entity = new HttpEntity(mainEntity , headers);
			ResponseEntity<PointsResponse> out = restTemplate.exchange(urlPoints, HttpMethod.PUT, entity , PointsResponse.class);
			pointsModel =  out.getBody();
		}
		catch(Exception ex)
		{

		}

		return pointsModel;
	}

	public boolean setUserPromoOffer(String userId, String promoId)
	{
		boolean setPromoOffer = false;

		try
		{
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
			OfferHistory historyOffer = new OfferHistory();
			String strDate =  format.format(new Date().getTime());
			Date scanDate =  format.parse(strDate);
			historyOffer.setPromoId(promoId);
			historyOffer.setUserId(userId);
			historyOffer.setMerchantId("");
			historyOffer.setShopZoneId("");
			historyOffer.setScanDate(scanDate);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<OfferHistory> entity = new HttpEntity<OfferHistory>(historyOffer , headers);
			ResponseEntity<OfferHistory> out = restTemplate.exchange("http://bofferhistorydevel.cfapps.io/offerhistory/", HttpMethod.POST, entity , OfferHistory.class);

			setPromoOffer = true;
		}
		catch(Exception ex)
		{
			setPromoOffer = false;
		}

		return setPromoOffer;
	}


	public DateDiffValues getDateDiffCall (String initialDate, String finalDate)
	{
		Map<String, Object> parameters = new LinkedHashMap<String, Object>();
		Map<String, Object> responseObject = new LinkedHashMap<String, Object>();
		DateDiffValues diffValues = new DateDiffValues();
		DateDiffResponse dateDiffResponse = null;

		try
		{
			if((!initialDate.isEmpty() || initialDate != null)  && (!initialDate.isEmpty() || initialDate != null))
			{
				parameters.put("initialDate",initialDate);
				parameters.put("finalDate", finalDate);
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<DateDiffValues> entity = new HttpEntity(parameters , headers);
				ResponseEntity<DateDiffResponse> out =  restTemplate.exchange("http://butilsdevel.cfapps.io/utils/getDateDiff", HttpMethod.POST, entity , DateDiffResponse.class);
				dateDiffResponse = out.getBody();
				if(dateDiffResponse.getStatus() == "200"){
					diffValues = dateDiffResponse.getDateDiffValues();
				}
			}
		}
		catch(Exception ex)
		{
			diffValues = null;
		}

		return diffValues;
	}

	@RequestMapping(method = RequestMethod.POST, value="/product/getdata")
	public Map<String, Object> getMerchantProductData(@RequestBody Map<String, Object> customMap){

		// Attributes
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		Map<String, Object> productData = new LinkedHashMap<String, Object>();
		MerchantProductResponse merchantProductResponse =  null;
		MerchantProfileResponse merchantProfileResponse = null;
		Product product = null;

		RestTemplate restTemplate = new RestTemplate();

		try
		{
			if( customMap.get("merchantProductId") != null && customMap.get("merchantId") != null &&  customMap.get("productId") != null  ){

				merchantProductResponse = restTemplate.getForObject( urlProduct + "" + customMap.get("merchantProductId").toString(), MerchantProductResponse.class);

				merchantProfileResponse = restTemplate.getForObject( urlMerchant  + "" + customMap.get("merchantId").toString(), MerchantProfileResponse.class);

				if(merchantProductResponse.getStatus() == 200 && merchantProfileResponse.getStatus() == 200){

					if(merchantProductResponse.getMerchantProduct().getMerchantId().equals(merchantProfileResponse.getMerchantProfile().getId())){

						if(merchantProductResponse.getMerchantProduct().getProductList() != null && merchantProductResponse.getMerchantProduct().getProductList().size() > 0){

							for(Product listProduct : merchantProductResponse.getMerchantProduct().getProductList()) {
								if (customMap.get("productId").toString().equals(listProduct.getProductId())) {
									product = listProduct;
								}
							}

							if(product != null) {
								response.put("status", 200);
								productData.put("product", product);
								productData.put("storeName", merchantProfileResponse.getMerchantProfile().getMerchantName());
								productData.put("shopZoneId", merchantProductResponse.getMerchantProduct().getShopZoneId());
								response.put("productData", productData);
								response.put("message", null);
							}
							else{
								response.put("status", 404);
								response.put("message", "Producto inexistente");
								response.put("productData", null);
							}
						}
						else{
							response.put("status", 404);
							response.put("message", "No hay un listado de productos");
							response.put("productData", null);
						}
					}
					else {
						response.put("status", 404);
						response.put("message", "El producto no pertenece a la tienda ingresada");
						response.put("productData", null);
					}
				}
				else{
					response.put("status", 400);
					response.put("message", "Error during request product and merchant");
					response.put("productData", null);
				}
			}
			else{
				response.put("status", 400);
				response.put("message", "Missing parameters");
				response.put("productData", null);
			}
		}
		catch(Exception ex)
		{
			response.put("productData", null);
			response.put("status", 500);
			response.put("message", ex.getMessage());
		}

		return response;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/user/getPointsData/{userId}")
	public Map<String, Object> getPointsHistory(@PathVariable("userId") String userId){

		// Attributes
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		Map<String, Object> customData = new LinkedHashMap<String, Object>();
		Map<String, Object> promoData = new LinkedHashMap<String, Object>();
		PromoResponse promoResponse =  null;
		MerchantProfileResponse merchantProfileResponse = null;
		OfferHistoryResponse offerHistoryResponse = null;
		OfferHistoryAttemptResponse offerHistoryAttemptResponse = null;
		UserResponse userResponse = null;
		RestTemplate restTemplate = new RestTemplate();
		int totalPoints = 0;
		List<OfferHistory> offerHistoryList = new ArrayList<OfferHistory>();
		List<OfferHistory> filteredList =  new ArrayList<OfferHistory>();
		LinkedHashMap<String,Object> data = new LinkedHashMap<String, Object>();
		String promoId = "";
		ObjectMapper mapper = new ObjectMapper();
		Object custom =  new Object();
		ArrayList<Object> listData = new ArrayList<Object>();

		try{

			if(!userId.isEmpty() || userId != null)
			{
				offerHistoryResponse =  restTemplate.getForObject(urlOfferHistory + "user/" + userId, OfferHistoryResponse.class);
				if(offerHistoryResponse.getStatus() == 200){


					offerHistoryList = mapper.convertValue(offerHistoryResponse.getOfferhistory(), new TypeReference<List<OfferHistory>>() { });


					//Get unique promos
					for(OfferHistory offerHistory : offerHistoryList){
						if(!promoId.equals(offerHistory.getPromoId())){
							promoId = offerHistory.getPromoId();
							filteredList.add(offerHistory);
						}
					}

					//Iterate unique promos
					for(OfferHistory offerHistory : filteredList){
						offerHistoryAttemptResponse = restTemplate.getForObject(
								urlOfferHistory + String.format(
										offerHistoryAttemptResource,
										offerHistory.getUserId(),
										offerHistory.getPromoId()),
								OfferHistoryAttemptResponse.class);

						if(offerHistoryAttemptResponse.getStatus() == 200){

							promoResponse = restTemplate.getForObject( urlPromo  + "" + offerHistoryAttemptResponse.getAttemptData().getPromoId(), PromoResponse.class);

							if(promoResponse.getStatus() == 200){

								merchantProfileResponse = restTemplate.getForObject( urlMerchant  + "" + promoResponse.getPromo().getMerchantId(), MerchantProfileResponse.class);

							}
						}
						if(promoResponse.getPromo() != null && merchantProfileResponse.getMerchantProfile() != null)
						{
							totalPoints = offerHistoryAttemptResponse.getAttemptData().getAttempts() * promoResponse.getPromo().getGiftPoints();
							merchantProfileResponse.getMerchantProfile().setUsers(null);

							customData.put("merchantProfile", merchantProfileResponse.getMerchantProfile());
							customData.put("promo", promoResponse.getPromo());
							customData.put("points", totalPoints);
							customData.put("lastScanDate", offerHistoryAttemptResponse.getAttemptData().getLastScan());

							custom = mapper.convertValue(customData, new TypeReference<Object>() {
							});

						}
						else {
							customData.put("merchantProfile", null);
							customData.put("promo", null);
							customData.put("points", 0);
							customData.put("lastScanDate",null);
							custom = mapper.convertValue(customData, new TypeReference<Object>() {
							});
						}

						listData.add(custom);
					}
					response.put("status", 200);
					response.put("message", "");
					response.put("pointsData", listData);

				}
				else{
					response.put("status", 404);
					response.put("message", "The user does not have points ");
					response.put("customData", null);
				}
			}
			else
			{
				response.put("status", 400);
				response.put("message", "Missing parameters");
				response.put("customData", null);
			}
		}
		catch (Exception ex){
			response.put("customData", null);
			response.put("status", 500);
			response.put("message", ex.getMessage());
		}

		return response;
	}



}


	

	


