package com.centaurosolutions.com.beacon.faq.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.centaurosolutions.com.beacon.faq.model.Faq;

public interface BeaconFaqRepository extends MongoRepository<Faq, String> 
{
	Faq findById(String id);
	List<Faq> findBySection(String section);
}
