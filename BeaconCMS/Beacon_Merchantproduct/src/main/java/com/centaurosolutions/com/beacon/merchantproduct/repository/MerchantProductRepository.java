package com.centaurosolutions.com.beacon.merchantproduct.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.centaurosolutions.com.beacon.merchantproduct.model.MerchantProduct;

public interface MerchantProductRepository extends MongoRepository<MerchantProduct, String>
{

}
