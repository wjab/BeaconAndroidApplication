package com.centaurosolutions.com.beacon.merchantprofile.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.centaurosolutions.com.beacon.merchantprofile.model.*;

public  interface MerchantProfileRepository extends MongoRepository<MerchantProfile, String> {

}