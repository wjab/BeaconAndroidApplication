package com.centaurosolutions.com.beacon.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.centaurosolutions.com.beacon.user.model.*;

public interface UserRepository extends MongoRepository<User, String> {
	User findByUser(String user);
	User findById(String id);
	User findByPhone(String phone);	
	User findBySocialNetworkType(String SocialNetworkType);
	User findBySocialNetworkId(String SocialNetworkId);
}
