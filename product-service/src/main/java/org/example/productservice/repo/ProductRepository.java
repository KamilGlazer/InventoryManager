package org.example.productservice.repo;

import org.example.productservice.model.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface ProductRepository extends MongoRepository<Product,Long> {
}
