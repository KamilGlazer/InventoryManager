package org.example.productservice.service;


import lombok.RequiredArgsConstructor;
import org.example.productservice.model.dto.ProductRequest;
import org.example.productservice.model.entity.Product;
import org.example.productservice.repo.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product addProduct(ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(),productRequest.getDescription());
        productRepository.save(product);
        return product;
    }
}
