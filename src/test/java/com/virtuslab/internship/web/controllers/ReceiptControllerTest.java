package com.virtuslab.internship.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import com.virtuslab.internship.web.services.BasketService;
import com.virtuslab.internship.web.services.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReceiptControllerTest {

    @Mock
    ReceiptService receiptService;

    @Mock
    BasketService basketService;

    @InjectMocks
    ReceiptController receiptController;

    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(receiptController)
                .setControllerAdvice(new ControllersExceptionHandler())
                .build();
    }

    @Test
    void testCreateRecipe() throws Exception {
        //given
        var basket = new Basket();
        basket.setId(1L);
        var productToAdd = new Product("test", Product.Type.DAIRY, BigDecimal.valueOf(3));
        basket.getProducts().add(productToAdd);
        var receipt = new ReceiptGenerator().generate(basket);

        when(basketService.findById(anyLong())).thenReturn(basket);
        when(receiptService.generateReceipt(any(Basket.class))).thenReturn(receipt);

        //when
        MvcResult result = mockMvc.perform(get("/receipt/1"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        var receiptJson = result.getResponse().getContentAsString();

        assertEquals(receiptJson, mapper.writeValueAsString(receipt));
    }
}