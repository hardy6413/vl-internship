package com.virtuslab.internship.web.services;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @Mock
    TenPercentDiscount tenPercentDiscount;

    @Mock
    FifteenPercentDiscount fifteenPercentDiscount;

    @Mock
    ReceiptGenerator receiptGenerator;

    @InjectMocks
    ReceiptService receiptService;

    @Test
    void testGenerateReceipt() {
        //given
        var basket = new Basket();
        basket.setId(1L);
        var productToAdd = new Product("test", Product.Type.DAIRY, BigDecimal.valueOf(3));
        basket.getProducts().add(productToAdd);
        var receipt = new ReceiptGenerator().generate(basket);

        when(receiptGenerator.generate(any(Basket.class))).thenReturn(receipt);
        when(tenPercentDiscount.apply(any(Receipt.class))).thenReturn(receipt);
        when(fifteenPercentDiscount.apply(any(Receipt.class))).thenReturn(receipt);

        //when
        var returnedReceipt = receiptService.generateReceipt(basket);

        //then
        assertEquals(returnedReceipt,receipt);
        verify(receiptGenerator,times(1)).generate(any(Basket.class));
        verify(tenPercentDiscount,times(1)).apply(any(Receipt.class));
        verify(fifteenPercentDiscount,times(1)).apply(any(Receipt.class));
    }
}