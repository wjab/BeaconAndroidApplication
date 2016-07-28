package com.centaurosolutions.com.beacon.utils.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.centaurosolutions.com.beacon.utils.model.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String>
{

}
