package com.centaurosolutions.com.beacon.utils.controller;

import com.centaurosolutions.com.beacon.utils.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
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
	private String urlMerchantInvoice = "http://bmerchantprofiledevel.cfapps.io/merchantinvoicehistory/";
	private String urlOfferHistory = "http://bofferhistorydevel.cfapps.io/offerhistory/";
	private String urlPoints = "http://bpointsdevel.cfapps.io/points/";
	private String offerHistoryAttemptResource = "/getAttempts/user/%s/promo/%s";
	private final String STATUS_ACTIVE = "ACTIVE";
	private final String STATUS_EXPIRED = "EXPIRED";
	private final String STATUS_REDEEMED = "REDEEMED";
	private final String AUTOMATIC_EXPIRATION = "AUTOMATIC PROCESS";
    private final String PROMO_SCAN = "SCAN";
    private final String PROMO_WALKIN = "WALKIN";
    private final String PROMO_PURCHASE = "PURCHASE";


	@RequestMapping(method = RequestMethod.POST, value="/savePoints")
	public Map<String, Object> createUser(@RequestBody Map<String, Object> customMap){

		// Attributes
		Map<String, Object> response = new LinkedHashMap<String, Object>();

		try
		{

            if((customMap.get("userId") != null && !customMap.get("userId").toString().isEmpty()) && (customMap.get("promoId") != null && !customMap.get("promoId").toString().isEmpty())){

                setPointsToUser(customMap.get("userId").toString(), customMap.get("promoId").toString(), response );

            }
            else {

                response.put("user", null);
                response.put("status", 400);
                response.put("message", "Missing parameters");

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
								response.put("points", null);
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
		Map<String, Object> data = new LinkedHashMap<String, Object>();
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

					pointsResponse = redeemPointsCall(customMap.get("userId").toString(), customMap.get("code").toString(), STATUS_REDEEMED);
					pointsModel = mapper.convertValue(pointsResponse.getPoints(), new TypeReference<Points>() { });

					if(pointsResponse.getStatus() == 200) {

						points = pointsModel.getPoints();

						userObject = userResponse.getUser();
						userObject.setTotalGiftPoints(userObject.getTotalGiftPoints() + points);

						if (setUserPoints(userObject)){

							data.put("user", userObject);
							data.put("points", points);
							response.put("pointsData", data);
							response.put("status", 200);
							response.put("message", null);
						}
						else{
							response.put("pointsData", null);
							response.put("status", 400);
							response.put("message", "Error durante la actualización del usuario");
						}
					}
					else{
						response.put("pointsData", null);
						response.put("status", 400);
						response.put("message", "Código invalido");
					}
				}
				else{
					response.put("pointsData", null);
					response.put("status", 400);
					response.put("message", "El usuario no existe");
				}
			}
			else{
				response.put("pointsData", null);
				response.put("status", 404);
				response.put("message", "Faltan parámetros");
			}
		}
		catch (Exception ex){
			response.put("pointsData", null);
			response.put("status", 500);
			response.put("message", ex.getMessage());
		}


		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value="/redeemPointsMerchant")
	public Map<String, Object> redeemPointsMerchant(@RequestBody Map<String, Object> customMap){

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		Points pointsModel = new Points();
		PointsResponse pointsResponse = new PointsResponse();
		User userObject = null;
		UserResponse userResponse = null;
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		MerchantProfileResponse merchantProfileResponse =  new MerchantProfileResponse();
		MerchantInvoiceHistory invoiceHistory = null;
		MerchantProfileInvoiceResponse invoiceResponse= null;
		int points = 0;

		try{
			if((customMap.get("merchantId") != null && !customMap.get("merchantId").toString().isEmpty()) &&
					(customMap.get("code") != null && !customMap.get("code").toString().isEmpty())){

					pointsResponse = redeemPointsCall(customMap.get("merchantId").toString(), customMap.get("code").toString(), STATUS_REDEEMED);
					pointsModel = mapper.convertValue(pointsResponse.getPoints(), new TypeReference<Points>() { });

					if(pointsResponse.getStatus() == 200) {

						points = pointsModel.getPoints();
						invoiceHistory = new MerchantInvoiceHistory();
						invoiceHistory.setMerchantId(customMap.get("merchantId").toString());
						invoiceHistory.setInvoiceId(customMap.get("invoiceId").toString());
						invoiceHistory.setInvoiceAmount(Float.parseFloat(customMap.get("invoiceAmount").toString()));
						invoiceHistory.setSecurityCode(pointsModel.getCode());
						invoiceHistory.setPaymentType(customMap.get("paymentType").toString().toUpperCase());
						invoiceHistory.setUsedPoints(points);
						invoiceHistory.setUserId(pointsModel.getUserId());


						invoiceResponse = setMerchantPoints(invoiceHistory);


						if (invoiceResponse.getStatus() == 200){

							invoiceHistory = mapper.convertValue(invoiceResponse.getInvoiceHistory(), new TypeReference<MerchantInvoiceHistory>() { });

							response.put("invoiceHistory", invoiceHistory);
							response.put("status", 200);
							response.put("message", null);
						}
						else{
							response.put("invoiceHistory", null);
							response.put("status", 400);
							response.put("message", "Error durante la actualización de la tienda");
						}
					}
					else{
						response.put("invoiceHistory", null);
						response.put("status", 400);
						response.put("message", "Código invalido");
					}
			}
			else{
				response.put("invoiceHistory", null);
				response.put("status", 404);
				response.put("message", "Faltan parámetros");
			}
		}
		catch (Exception ex){
			response.put("invoiceHistory", null);
			response.put("status", 500);
			response.put("message", ex.getMessage());
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value="/getDateDiff")
	public Map<String, Object> getAccurateDateDifference(@RequestBody Map<String, Object> customMap){

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		DateDiffValues dateDiffValues = null;
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		try
		{
			if(customMap.get("initialDate") != null && customMap.get("finalDate") != null )
			{
                 dateDiffValues =  getDateDiff(customMap.get("initialDate").toString(),customMap.get("finalDate").toString() );

				if( dateDiffValues != null )
				{
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

	@RequestMapping(method = RequestMethod.POST, value="/product/getdata")
	public Map<String, Object> getMerchantProductData(@RequestBody Map<String, Object> customMap){

		// Attributes
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Map<String, Object> response = new LinkedHashMap<>();

		try
		{
			if((customMap.get("merchantId") != null && !customMap.get("merchantId").toString().isEmpty()) &&  (customMap.get("productId") != null && !customMap.get("productId").toString().isEmpty()) ){
				getMerchantInfo(customMap.get("merchantId").toString(), customMap.get("productId").toString(), "", false, response);
			}

			else if(customMap.get("promoId") != null && !customMap.get("promoId").toString().isEmpty())
			{
				getMerchantInfo("", "", customMap.get("promoId").toString(), true, response);
			}

			else {
				response.put("status", 400);
				response.put("message", "Missing paramenters");
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
					response.put("pointsData", null);
				}
			}
			else
			{
				response.put("status", 400);
				response.put("message", "Missing parameters");
				response.put("pointsData", null);
			}
		}
		catch (Exception ex){
			response.put("pointsData", null);
			response.put("status", 500);
			response.put("message", ex.getMessage());
		}

		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/code/checkExpiration")
	public Map<String, Object> expireCodes(){

		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		PointsResponse pointsResponse;
		Points pointsModel;
		RestTemplate restTemplate =  new RestTemplate();
		List<Points> listPoints = new ArrayList<Points>();;
		List<Points> listExpiredPoints = new ArrayList<Points>();
		ObjectMapper mapper = new ObjectMapper();
		Date currDate = new Date();

		try {

			pointsResponse = restTemplate.getForObject(urlPoints + "/status/"+ STATUS_ACTIVE, PointsResponse.class);

			if(pointsResponse.getStatus() == 200){

				listPoints = mapper.convertValue(pointsResponse.getPoints(), new TypeReference<List<Points>>() { });

				for(Points points: listPoints){

					if(currDate.compareTo(points.getExpirationDate()) > 0){
						pointsResponse = redeemPointsCall(AUTOMATIC_EXPIRATION, points.getCode(), STATUS_EXPIRED);
						if(pointsResponse.getStatus() == 200){
							points.setStatus(STATUS_EXPIRED);
							points.setRedeemedByUserId(AUTOMATIC_EXPIRATION);
							listExpiredPoints.add(points);
						}
					}
				}
				if(listExpiredPoints.size() > 0){
					response.put("totalExpired", listExpiredPoints.size());
					response.put("status", 200);
					response.put("message","Registros expirados");

				}
				else{
					response.put("totalExpired", 0);
					response.put("status", 404);
					response.put("message", "No se expiraron códigos");
				}
			}
		}
		catch (Exception ex){

			response.put("totalExpired", null);
			response.put("status", 500);
			response.put("message", ex.getMessage());
		}

		return response;

	}

	@RequestMapping(method = RequestMethod.POST, value="/savePointsByCode")
	public Map<String, Object> savePointsByPromoCode(@RequestBody Map<String, Object> customMap){

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		Points pointsModel = new Points();
		PointsResponse pointsResponse = new PointsResponse();
		User userObject = null;
		UserResponse userResponse = null;
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		int points = 0;


		try{
			if((customMap.get("userId") != null && !customMap.get("userId").toString().isEmpty()) &&
					(customMap.get("code") != null && !customMap.get("code").toString().isEmpty()) &&
                    (customMap.get("merchantId") != null && !customMap.get("merchantId").toString().isEmpty())){

                setPointsToUserByProductCode(customMap.get("userId").toString(), customMap.get("merchantId").toString(),  customMap.get("code").toString(), response);

			}
			else{
				response.put("pointsData", null);
				response.put("status", 404);
				response.put("message", "Faltan parámetros");
			}
		}
		catch (Exception ex){
			response.put("pointsData", null);
			response.put("status", 500);
			response.put("message", ex.getMessage());
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

	public void setPointsToUser(String userId, String promoId, Map<String, Object> response){

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


		try{

            offerHistoryAttemptResponse = restTemplate.getForObject(
                    urlOfferHistory + String.format(
                            offerHistoryAttemptResource,
                            userId,
                            promoId),
                    OfferHistoryAttemptResponse.class);

			promoResponse = restTemplate.getForObject( urlPromo + "" + promoId, PromoResponse.class);

			if(promoResponse.getStatus() == 200 && offerHistoryAttemptResponse.getStatus() == 200 ) {
				offerHistoryAttempt = offerHistoryAttemptResponse.getAttemptData();
				promoObject = promoResponse.getPromo();

				dateDiffInfo = getDateDiff(format.format(offerHistoryAttempt.getLastScan()).toString(), format.format(dateNow));
				/***Verificar si es la primera vez en registrar una promoción en el historial de ofertas***/
				if (offerHistoryAttempt.getAttempts() == 0 ||
						(offerHistoryAttempt.getAttempts() < promoObject.getAttempt() &&
								(dateNow.after(promoObject.getStartDate()) && promoObject.getEndDate().after(dateNow)) &&
								dateDiffInfo.getHours() > promoObject.getInterval())) {
					userResponse = restTemplate.getForObject(urlUser + "id/" + userId, UserResponse.class);

					if (userResponse.getStatus() == 200) {
						userObject = userResponse.getUser();
						points = userObject.getTotalGiftPoints() + promoObject.getGiftPoints();
						userObject.setTotalGiftPoints(points);

						if (setUserPoints(userObject) && setUserPromoOffer(userObject.getId(), promoObject.getId())) {
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
                else{
                    response.put("user", null);
                    response.put("status", 400);
                    response.put("message", "El usuario ha superado el límite de escaneos y/o el intervalo de escaneo no se ha cumplido");
                }
			}
            else{
                response.put("user", null);
                response.put("status", 400);
                response.put("message", "Error inesperado obteniendo datos de usuario y promociones");
            }
		}
		catch (Exception ex){
            response.put("user", null);
            response.put("status", 500);
            response.put("message", ex.getMessage());
		}
	}

    public void setPointsToUserByProductCode(String userId, String merchantId, String code, Map<String, Object> response ){

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        Points pointsModel = new Points();
        PointsResponse pointsResponse = new PointsResponse();
        MerchantProfileResponse merchantProfileResponse = new MerchantProfileResponse();
        PromoResponse promoResponse = new PromoResponse();
        User userObject = null;
        UserResponse userResponse = null;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        Promo promo = null;
        int points = 0;


        try{

            merchantProfileResponse =  restTemplate.getForObject( urlMerchant  + "" + merchantId, MerchantProfileResponse.class);

            if(merchantProfileResponse.getStatus() == 200){

                if(merchantProfileResponse.getMerchantProfile().getDepartments() != null && merchantProfileResponse.getMerchantProfile().getDepartments().size() > 0)
                {
                    for(Department department : merchantProfileResponse.getMerchantProfile().getDepartments()){

                        if(department.getProducts() != null && department.getProducts() .size() > 0 ){

                            for (Product product : department.getProducts()){

                                if(product.getCode().equals(code)){
                                    promoResponse = getPromoByProductData(product.getProductId(),merchantProfileResponse.getMerchantProfile().getId(), department.getId());

                                    if(promoResponse.getStatus() == 200){
                                        if(promoResponse.getPromo().getType().toUpperCase().equals(PROMO_SCAN)){
                                            promo =  promoResponse.getPromo();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if(promo != null){
                            break;
                        }
                    }
                }

                if(promo != null){
                    setPointsToUser(userId, promo.getId(), response);
                }
                else{
                    response.put("user", null);
                    response.put("status", 404);
                    response.put("message", "Promoción no encontrada");
                }
            }
            else{
                response.put("user", null);
                response.put("status", 404);
                response.put("message", "Perfil de tiendas no encontrado");
            }
        }

        catch (Exception ex){
            response.put("user", null);
            response.put("status", 500);
            response.put("message", ex.getMessage());
        }
    }

	public MerchantProfileInvoiceResponse setMerchantPoints(MerchantInvoiceHistory merchantInvoice)
	{

		MerchantProfileInvoiceResponse response  = null;

		try{

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<MerchantInvoiceHistory> entity = new HttpEntity<MerchantInvoiceHistory>(merchantInvoice , headers);
			ResponseEntity<MerchantProfileInvoiceResponse> out = restTemplate.exchange(urlMerchantInvoice, HttpMethod.POST, entity , MerchantProfileInvoiceResponse.class);

			response = out.getBody();

		}
		catch (Exception ex){

		}

		return response;
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

	public PointsResponse redeemPointsCall(String userId, String code, String status)
	{
		PointsResponse pointsModel =  null;
		Map<String, Object> mainEntity = new LinkedHashMap<String, Object>();

		try
		{
			mainEntity.put("code", code);
			mainEntity.put("redeemedByUserId", userId);
			mainEntity.put("status", status);

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

   public void getMerchantInfo(String merchantId, String productId, String promoId, boolean isPromo, Map<String, Object> response ){

	   // Attributes
	   format.setTimeZone(TimeZone.getTimeZone("UTC"));
	   MerchantProductResponse merchantProductResponse =  null;
	   MerchantProfileResponse merchantProfileResponse = null;
	   PromoResponse promoResponse = null;
	   Product product = null;
	   String pId = null;
	   Map<String, Object> productData = new LinkedHashMap<String, Object>();

	   RestTemplate restTemplate = new RestTemplate();

	   try{

		   promoResponse = isPromo ? restTemplate.getForObject(urlPromo + "" + promoId, PromoResponse.class) : null;

		   if(promoResponse != null && promoResponse.getStatus() == 200)
		   {
			   merchantProfileResponse = restTemplate.getForObject( urlMerchant  + "" + promoResponse.getPromo().getMerchantId(), MerchantProfileResponse.class);
		   }
		   else{
			   merchantProfileResponse =  restTemplate.getForObject( urlMerchant  + "" + merchantId, MerchantProfileResponse.class);
		   }

		   if(merchantProfileResponse.getStatus() == 200){

			   pId = isPromo ? promoResponse.getPromo().getIdProduct() : productId;

			   if(merchantProfileResponse.getMerchantProfile().getDepartments() != null && merchantProfileResponse.getMerchantProfile().getDepartments().size() > 0){

				   for(Department dep: merchantProfileResponse.getMerchantProfile().getDepartments())
				   {
					   if(pId != null && !pId.isEmpty())
					   {
						   for(Product listProduct : dep.getProducts()) {
							   if (pId.equals(listProduct.getProductId())) {
								   product = listProduct;
								   break;
							   }
						   }
					   }
				   }
			   }

			   if(product != null){
				   response.put("status", 200);
				   productData.put("product", product);
				   productData.put("storeName", merchantProfileResponse.getMerchantProfile().getMerchantName());
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
			   response.put("status", 400);
			   response.put("message", "Error durante la obtención de datos");
			   response.put("productData", null);
		   }
	   }
	   catch (Exception ex)
	   {
		   response.put("productData", null);
		   response.put("status", 500);
		   response.put("message", ex.getMessage());
	   }

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

    public PromoResponse getPromoByProductData(String productId, String merchantId, String departmentId){

        PromoResponse promoResponse =  null;
        Map<String, Object> mainEntity = new LinkedHashMap<String, Object>();

        try
        {
            mainEntity.put("merchantId", merchantId);
            mainEntity.put("idProduct", productId);
            mainEntity.put("departamentId", departmentId);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<LinkedHashMap<String, Object>> entity = new HttpEntity(mainEntity , headers);
            ResponseEntity<PromoResponse> out = restTemplate.exchange(urlPromo+"/promo/product", HttpMethod.POST, entity , PromoResponse.class);
            promoResponse =  out.getBody();
        }
        catch(Exception ex)
        {

        }

        return promoResponse;
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

	public DateDiffValues getDateDiff(String dateInit, String dateFinal)
	{
		DateDiffValues values = null;
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
	    format.setTimeZone(TimeZone.getTimeZone("UTC"));
		long diff = 0;
		long diffSeconds = 0;
		long diffMinutes = 0;
		long diffHours = 0;
		long diffDays = 0;

		try
		{
			d1.setTime(format.parse(dateInit));
			d2.setTime(format.parse(dateFinal));

			//in seconds
			diff = (d1.getTimeInMillis() / 1000) - (d2.getTimeInMillis() / 1000) ;

			if( diff > 0 )
			{
				diffSeconds = diff / 1000 % 60;
				diffMinutes = diff / (60 * 1000) % 60;
				diffHours = (diff / (60 * 60) % 24) + (diff / (24 * 60 * 60)) * 24;;
				diffDays = diff / (24 * 60 * 60 * 1000);

				values = new DateDiffValues();
				values.setSeconds((int) diffSeconds);
				values.setHours((int) diffHours);
				values.setMinutes((int) diffMinutes);
				values.setDays((int) diffDays);
			}
		}
		catch (ParseException ex)
		{

		}
		return values;
	}

}




	


