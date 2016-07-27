package com.centaurosolutions.com.beacon.points.controller;

import com.centaurosolutions.com.beacon.points.model.Points;
import com.centaurosolutions.com.beacon.points.repository.PointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Eduardo on 26/7/2016.
 */

@RestController
@RequestMapping("/points")
public class PointsController {

    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

    private final String STATUS_ACTIVE = "ACTIVE";
    private final String STATUS_EXPIRED = "EXPIRED";
    private final String STATUS_REDEEMED = "REDEEMED";


    @Autowired
    private PointsRepository pointsRepository;


    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> createPointsRegistry(@RequestBody Map<String, Object> pointsMap) {

        Points pointsModel = new Points();
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        String code = "";
        Date creationDate = new Date();

        try {
            code = generateSecurityCode();

            pointsModel = new Points(
                    pointsMap.get("userId").toString(),
                    creationDate,
                    setExpirationDate(creationDate),
                    null,
                    null,
                    code,
                    pointsMap.get("type").toString(),
                    Integer.parseInt(pointsMap.get("points").toString()),
                    STATUS_ACTIVE
            );

            pointsRepository.save(pointsModel);

            response.put("points", pointsModel);
            response.put("status", 200);
            response.put("message", null);

        } catch (Exception ex) {

            response.put("points", null);
            response.put("status", 500);
            response.put("message", null);
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Map<String, Object> updatePointsRegistry(@RequestBody Map<String, Object> pointsMap) {

        Points pointsModel = new Points();
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        String code = "";
        Date creationDate = new Date();
        Points userData = null;

        try {
            if (pointsMap.get("code") != null && !pointsMap.get("code").toString().isEmpty()) {
                userData = pointsRepository.findByCode(pointsMap.get("code").toString());

                if (userData != null) {

                    if (userData.getStatus().equals(STATUS_ACTIVE)) {

                        userData.setStatus(pointsMap.get("status").toString());

                        if (pointsMap.get("status").toString().toUpperCase().equals(STATUS_REDEEMED)) {
                            userData.setRedeemedDate(new Date());
                            userData.setRedeemedByUserId(pointsMap.get("redeemedByUserId").toString());
                        } else {
                            userData.setExpirationDate(new Date());
                        }

                        pointsRepository.save(userData);

                        response.put("points", userData);
                        response.put("status", 200);
                        response.put("message", null);
                    } else {
                        response.put("points", null);
                        response.put("status", 400);
                        response.put("message", "Código expirado o redimido");
                    }
                } else {
                    response.put("points", null);
                    response.put("status", 404);
                    response.put("message", "Código no encontrado");
                }
            }
        } catch (Exception ex) {
            response.put("points", null);
            response.put("status", 500);
            response.put("message", null);
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> getPointDetails() {
        Map<String, Object> response = new LinkedHashMap<String, Object>();

        try {
            List<Points> pointsList = pointsRepository.findAll();

            if (pointsList != null && pointsList.size() > 0) {
                response.put("message", "");
                response.put("status", 200);
                response.put("points", pointsList);
            } else {
                response.put("message", "No hay puntos registrados");
                response.put("status", 404);
                response.put("points", null);
            }
        } catch (Exception ex) {
            response.put("message", ex.getMessage());
            response.put("points", null);
            response.put("status", 500);
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    public Map<String, Object> GetPointsByUserId(@PathVariable("userId") String userId) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        List<Points> pointsList = null;

        try {

            if (userId != null && !userId.isEmpty()) {
                pointsList = pointsRepository.findAllByUserId(userId);

                if (pointsList != null && pointsList.size() > 0) {
                    response.put("message", "");
                    response.put("status", 200);
                    response.put("points", pointsList);
                } else {
                    response.put("message", "No hay puntos flotantes para este usuario");
                    response.put("status", 404);
                    response.put("points", null);
                }
            } else {
                response.put("message", "Missing parameters");
                response.put("status", 400);
                response.put("points", null);
            }
        } catch (Exception ex) {
            response.put("message", ex.getMessage());
            response.put("status", 500);
            response.put("points", null);
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/status/{status}")
    public Map<String, Object> GetPointsByStatus(@PathVariable("status") String status) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        List<Points> pointsList = null;

        try {

            if (status != null) {
                pointsList = pointsRepository.findAllByStatus(status.toUpperCase());

                if (pointsList != null && pointsList.size() > 0) {
                    response.put("message", "");
                    response.put("status", 200);
                    response.put("points", pointsList);
                } else {
                    response.put("message", "No hay puntos registrados");
                    response.put("status", 404);
                    response.put("points", null);
                }
            } else {
                response.put("message", "Missing parameters");
                response.put("status", 400);
                response.put("points", null);
            }
        } catch (Exception ex) {
            response.put("message", ex.getMessage());
            response.put("status", 500);
            response.put("points", null);
        }

        return response;
    }


    private String getDate() {
        DateFormat df = DateFormat.getDateTimeInstance();
        long currentTime = new Date().getTime();
        String currDate = df.format(new Date(currentTime));
        return currDate;
    }


    private Date DateFormatter(String pDate) {

        Date finalDate = new Date();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            finalDate = format.parse(pDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return finalDate;
    }


    public String generateSecurityCode() {

        String securityCode = "";

        try {
            securityCode = UUID.randomUUID().toString().toUpperCase().replace("-", "").substring(0, 8);
        } catch (Exception ex) {

        }

        return securityCode;
    }

    private Date setExpirationDate(Date date) {

        Calendar calendar = Calendar.getInstance();

        try {

            calendar.setTime(date);
            calendar.add(calendar.HOUR, 8);
        } catch (Exception ex) {

        }

        return calendar.getTime();
    }
}