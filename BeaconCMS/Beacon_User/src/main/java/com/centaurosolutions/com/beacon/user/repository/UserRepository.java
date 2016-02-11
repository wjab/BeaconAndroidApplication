package com.centaurosolutions.com.beacon.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.centaurosolutions.com.beacon.user.model.*;

public interface UserRepository extends MongoRepository<User, String> {
	User findByUser(String user);
	User findById(String id);

}
