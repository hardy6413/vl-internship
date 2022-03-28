package com.virtuslab.internship.web.services;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    private final ReceiptGenerator receiptGenerator;
    private final TenPercentDiscount tenPercentDiscount;
    private final FifteenPercentDiscount fifteenPercentDiscount;

    @Autowired
    public ReceiptService(ReceiptGenerator receiptGenerator, TenPercentDiscount tenPercentDiscount, FifteenPercentDiscount fifteenPercentDiscount) {
        this.receiptGenerator = receiptGenerator;
        this.tenPercentDiscount = tenPercentDiscount;
        this.fifteenPercentDiscount = fifteenPercentDiscount;
    }

    public Receipt generateReceipt(Basket basket) {
        var generatedReceipt = receiptGenerator.generate(basket);
        generatedReceipt = tenPercentDiscount.apply(generatedReceipt);
        generatedReceipt = fifteenPercentDiscount.apply(generatedReceipt);

        return generatedReceipt;
    }
}
