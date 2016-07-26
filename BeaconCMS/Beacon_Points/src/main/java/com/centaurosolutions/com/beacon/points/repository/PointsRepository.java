package com.centaurosolutions.com.beacon.points.repository;

import com.centaurosolutions.com.beacon.points.model.Points;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Eduardo on 26/7/2016.
 */
public interface PointsRepository  extends MongoRepository<Points, String>{

    Points findByUserId(String userId);

}
