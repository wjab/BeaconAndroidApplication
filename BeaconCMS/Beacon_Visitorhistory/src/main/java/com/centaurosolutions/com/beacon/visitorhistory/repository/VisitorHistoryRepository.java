package com.centaurosolutions.com.beacon.visitorhistory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.centaurosolutions.com.beacon.visitorhistory.model.*;


public  interface VisitorHistoryRepository extends MongoRepository<VisitorHistory, String> {

	
}
