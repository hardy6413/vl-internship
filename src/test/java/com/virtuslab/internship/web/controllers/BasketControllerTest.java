package com.virtuslab.internship.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.web.services.BasketService;
import com.virtuslab.internship.web.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BasketControllerTest {

    @Mock
    BasketService basketService;

    @Mock
    ProductService productService;

    @InjectMocks
    BasketController basketController;

    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(basketController)
                .setControllerAdvice(new ControllersExceptionHandler())
                .build();
    }

    @Test
    void testGetBasket() throws Exception {
        var basket = new Basket();
        basket.setId(1L);

        when(basketService.findById(anyLong())).thenReturn(basket);

        MvcResult result = mockMvc.perform(get("/basket/1"))
                .andExpect(status().isOk())
                .andReturn();

        var basketJson = result.getResponse().getContentAsString();

        assertEquals(basketJson, mapper.writeValueAsString(basket));
    }

    @Test
    void testAddProductToBasket() throws Exception {
        var basket = new Basket();
        basket.setId(1L);
        var productToAdd = new Product("test", Product.Type.DAIRY, BigDecimal.valueOf(3));
        basket.getProducts().add(productToAdd);

        when(basketService.addItemToBasket(anyLong(),any(Product.class))).thenReturn(basket);
        when(productService.findByName(anyString())).thenReturn(productToAdd);

        MvcResult result = mockMvc.perform(post("/basket/1/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productToAdd)))
                .andExpect(status().isOk())
                .andReturn();

        var basketJson = result.getResponse().getContentAsString();

        assertEquals(basketJson, mapper.writeValueAsString(basket));
        verify(basketService, times(1)).addItemToBasket(anyLong(),any(Product.class));
        verify(productService, times(1)).findByName(anyString());
    }

    @Test
    void testCreateBasket() throws Exception {
        var basket = new Basket();
        basket.setId(1L);

        when(basketService.saveBasket(any(Basket.class))).thenReturn(basket);

        MvcResult result = mockMvc.perform(post("/basket/create"))
                .andExpect(status().isCreated())
                .andReturn();

        var basketJson = result.getResponse().getContentAsString();

        assertEquals(basketJson, mapper.writeValueAsString(basket));
    }

    @Test
    void deleteItemFromBasket() throws Exception {
        var basket = new Basket();
        basket.setId(1L);
        var productToBeDeleted = new Product("test", Product.Type.DAIRY, BigDecimal.valueOf(3));

        when(basketService.removeItemFromBasket(anyLong(),any(Product.class))).thenReturn(basket);
        when(productService.findByName(anyString())).thenReturn(productToBeDeleted);

        mockMvc.perform(delete("/basket/1/"+productToBeDeleted.name()))
                .andExpect(status().isOk())
                .andReturn();

        verify(basketService, times(1)).removeItemFromBasket(anyLong(),any(Product.class));
        verify(productService, times(1)).findByName(anyString());
    }

    @Test
    void deleteBasket() throws Exception {
        mockMvc.perform(delete("/basket/1"))
                .andExpect(status().isOk())
                .andReturn();

        verify(basketService, times(1)).removeBasket(anyLong());
    }
}