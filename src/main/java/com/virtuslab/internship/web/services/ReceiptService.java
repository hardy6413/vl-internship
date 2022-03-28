package com.virtuslab.internship.web.services;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReceiptService {

    ReceiptGenerator receiptGenerator;

    public Receipt generateReceipt(Basket basket) {
        return receiptGenerator.generate(basket);
    }
}
