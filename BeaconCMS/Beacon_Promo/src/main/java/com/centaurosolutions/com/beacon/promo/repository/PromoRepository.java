package com.centaurosolutions.com.beacon.promo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.centaurosolutions.com.beacon.promo.model.Promo;
import org.springframework.data.mongodb.repository.Query;


public  interface PromoRepository extends MongoRepository<Promo, String> {

	Promo findById(String id);

	@Query(value = "{ 'merchantId' : ?0, 'departamentId' : ?1, 'idProduct' : ?2 }")
	Promo findMerchantDepartmentProductId(String merchantId, String departamentId, String idProduct);

	Promo findByCode(String code);
}
