package com.centaurosolutions.com.beacon.merchantprofile.repository;

import com.centaurosolutions.com.beacon.merchantprofile.model.Department;
import com.centaurosolutions.com.beacon.merchantprofile.model.MerchantProfile;
import com.centaurosolutions.com.beacon.merchantprofile.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public  interface MerchantProfileRepository extends MongoRepository<MerchantProfile, String> {

    @Query(value = "{ 'id' : ?0, 'departments.name' : ?1 }")
    MerchantProfile  findDepartmentsByName(String merchantId, String department);

    @Query(value = "{ 'id' : ?0, 'deparments.products.productId' : ?1 }")
    Product findProductById(String merchantId, String productId);

    @Query(value = "{ 'id' : ?0, 'deparments.id' : ?1 }")
    Department  findDepartmentById(String merchantId, String departmentId);

}