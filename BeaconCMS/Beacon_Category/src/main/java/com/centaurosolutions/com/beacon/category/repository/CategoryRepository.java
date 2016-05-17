package com.centaurosolutions.com.beacon.category.repository;




import org.springframework.data.mongodb.repository.MongoRepository;
import com.centaurosolutions.com.beacon.category.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
	Category findByCategoryName(String user);
}
