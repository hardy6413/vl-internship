package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManyDiscountsAtTheSameTimeTest {

    @Test
    void shouldApply15And10PercentDiscountWhenCartHasAtLeast3GrainProductsAndPriceIsAbove50() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));
        receiptEntries.add(new ReceiptEntry(steak,1));

        var receipt = new Receipt(receiptEntries);
        var fifteenPercentDiscount = new FifteenPercentDiscount();
        var tenPercentDiscount = new TenPercentDiscount();
        var expectedTotalPrice = cereals.price().multiply(BigDecimal.valueOf(2)).add(bread.price())
                .add(steak.price()).multiply(BigDecimal.valueOf(0.85).multiply(BigDecimal.valueOf(0.9)));

        // When
        var receiptAfterDiscount = fifteenPercentDiscount.apply(receipt);
        receiptAfterDiscount = tenPercentDiscount.apply(receiptAfterDiscount);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(2, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldOnlyApply15PercentDiscountWhenCartHasAtLeast3GrainProductsButPriceIsUnder50() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var pork = productDb.getProduct("Pork");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));
        receiptEntries.add(new ReceiptEntry(pork,2));

        var receipt = new Receipt(receiptEntries);
        var fifteenPercentDiscount = new FifteenPercentDiscount();
        var tenPercentDiscount = new TenPercentDiscount();
        var expectedTotalPrice = cereals.price().multiply(BigDecimal.valueOf(2)).add(bread.price())
                .add(pork.price().multiply(BigDecimal.valueOf(2))).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = fifteenPercentDiscount.apply(receipt);
        receiptAfterDiscount = tenPercentDiscount.apply(receiptAfterDiscount);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }
}
