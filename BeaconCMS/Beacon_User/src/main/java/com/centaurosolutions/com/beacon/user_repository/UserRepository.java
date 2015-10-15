package com.centaurosolutions.com.beacon.user_repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.centaurosolutions.com.beacon.user_model.*;

public interface UserRepository extends MongoRepository<User, String> {

}
