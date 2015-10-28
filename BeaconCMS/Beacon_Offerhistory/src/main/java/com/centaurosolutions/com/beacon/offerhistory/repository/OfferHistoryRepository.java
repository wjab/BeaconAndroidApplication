package com.centaurosolutions.com.beacon.offerhistory.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.centaurosolutions.com.beacon.offerhistory.model.*;

public interface OfferHistoryRepository extends MongoRepository<OfferHistory,String> {

}
