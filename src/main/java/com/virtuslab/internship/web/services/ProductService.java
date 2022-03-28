package com.virtuslab.internship.web.services;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.web.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductService {

    ProductDb productRepository;

    public Product findByName(String name){
        Product productOptional = productRepository.getProduct(name);
        if (productOptional == null){
            throw new NotFoundException("Product not found for NAME: " +name);
        }
        return  productOptional;
    }
}
