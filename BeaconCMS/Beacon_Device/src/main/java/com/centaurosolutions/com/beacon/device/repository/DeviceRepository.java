package com.centaurosolutions.com.beacon.device.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.centaurosolutions.com.beacon.device.model.Device;

public  interface DeviceRepository extends MongoRepository<Device, String> {

}