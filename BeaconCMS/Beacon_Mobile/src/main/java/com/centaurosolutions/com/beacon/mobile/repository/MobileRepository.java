/**
 * 
 */
package com.centaurosolutions.com.beacon.mobile.repository;

import java.util.List;

/**
 * @author Eduardo
 *
 */
import org.springframework.data.mongodb.repository.MongoRepository;

import com.centaurosolutions.com.beacon.mobile.model.*;

public  interface MobileRepository extends MongoRepository<Mobile, String> {
	

	List<Mobile> findByUserId(String userId);
	
}
