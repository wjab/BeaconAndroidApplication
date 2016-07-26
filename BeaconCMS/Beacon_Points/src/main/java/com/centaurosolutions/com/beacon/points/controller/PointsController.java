package com.centaurosolutions.com.beacon.points.controller;

import com.centaurosolutions.com.beacon.points.repository.PointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Eduardo on 26/7/2016.
 */

@RestController
@RequestMapping("/points")
public class PointsController {

    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

    @Autowired
    private PointsRepository pointsRepository;



    public Map<String, Object> createPointsRegistry(@RequestBody Map<String, Object> pointsMap){

        Map<String, Object> response = new LinkedHashMap<String, Object>();

        try{

        }
        catch (Exception ex){

        }

        return response;
    }


    private String getDate()
    {
        DateFormat df = DateFormat.getDateTimeInstance();
        long currentTime = new Date().getTime();
        String currDate = df.format(new Date(currentTime));
        return currDate;
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



}
