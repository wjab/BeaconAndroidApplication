package com.centaurosolutions.com.beacon.offerhistory.repository;
import java.util.ArrayList;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.centaurosolutions.com.beacon.offerhistory.model.*;

public interface OfferHistoryRepository extends MongoRepository<OfferHistory, String> 
{	
	OfferHistory findByUserId(String UserId);
	ArrayList<OfferHistory> findAllByUserId(String userId, Pageable pageable);
}
