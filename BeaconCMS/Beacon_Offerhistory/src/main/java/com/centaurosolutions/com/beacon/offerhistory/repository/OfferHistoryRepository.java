package com.centaurosolutions.com.beacon.offerhistory.repository;
import com.centaurosolutions.com.beacon.offerhistory.model.OfferHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface OfferHistoryRepository extends MongoRepository<OfferHistory, String> 
{	
	OfferHistory findByUserId(String UserId);
	ArrayList<OfferHistory> findAllByUserId(String userId);
}
