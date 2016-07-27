package com.centaurosolutions.com.beacon.points.repository;

import com.centaurosolutions.com.beacon.points.model.Points;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;

/**
 * Created by Eduardo on 27/7/2016.
 */
public interface PointsRepository extends MongoRepository<Points, String>{

    Points findByUserId(String userId);
    Points findByCode(String code);

    @Query(value = "{ 'userId': ?0}")
    ArrayList<Points> findAllByUserId(String userId);

    @Query(value = "{ 'status': ?0}")
    ArrayList<Points> findAllByStatus(String status);
}
