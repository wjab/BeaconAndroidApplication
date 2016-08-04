package com.centaurosolutions.com.beacon.merchantprofile.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.centaurosolutions.com.beacon.merchantprofile.model.MerchantBusinessType;

public interface MerchantBusinessTypeRepository extends MongoRepository<MerchantBusinessType, String>
{	
	MerchantBusinessType findByType(String type);
}