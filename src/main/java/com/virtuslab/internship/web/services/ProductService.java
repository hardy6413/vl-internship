package com.virtuslab.internship.web.services;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDb productRepository;

    @Autowired
    public ProductService(ProductDb productRepository) {
        this.productRepository = productRepository;
    }

    public Product findByName(String name){
        return productRepository.getProduct(name);
    }
}
