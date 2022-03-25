package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class FifteenPercentDiscount {

    public static String NAME = "FifteenPercentDiscount";

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        AtomicReference<Integer> grainProductsAmount = new AtomicReference<>(0);
        receipt.entries().forEach(receiptEntry -> {
            if (receiptEntry.product().type().equals(Product.Type.GRAINS)) {
                grainProductsAmount.updateAndGet(v -> v + receiptEntry.quantity());
            }
        });
        return grainProductsAmount.get() >= 3;
    }
}
