package com.centaurosolutions.com.beacon.promo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.centaurosolutions.com.beacon.promo.model.Promo;

public  interface PromoRepository extends MongoRepository<Promo, String> {

}
