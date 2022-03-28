package com.virtuslab.internship.web.services;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductService {

    ProductDb productRepository;

    public Product findByName(String name){
        Product productOptional = productRepository.getProduct(name);
        if (productOptional == null){
            //todo error
        }
        return  productOptional;
    }
}
