package com.virtuslab.internship.web.services;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductDb productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void testFindByName() {
        //given
        var product = new Product("test", Product.Type.DAIRY, BigDecimal.valueOf(3));


        when(productRepository.getProduct(anyString())).thenReturn(product);

        //when
        var returnedProduct = productService.findByName(product.name());

        //then
        assertEquals(returnedProduct,product);
        verify(productRepository,times(1)).getProduct(anyString());
    }
}