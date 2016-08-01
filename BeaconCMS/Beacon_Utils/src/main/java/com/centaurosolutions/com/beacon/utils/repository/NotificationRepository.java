package com.centaurosolutions.com.beacon.utils.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.centaurosolutions.com.beacon.utils.model.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String>
{
	@Query(value = "{ 'userId' : ?0, 'read' : ?1 }")
	ArrayList<Notification> findAllByUserIdAndRead(String userId, boolean read);
	
	ArrayList<Notification> findAllByUserId(String userId);
}
