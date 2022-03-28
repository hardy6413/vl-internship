package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();

        Map<Product,Integer> productQuantityMap = basket.getProducts().stream().collect(Collectors.toMap(Function.identity(),v -> 1,Integer::sum));

        productQuantityMap.forEach((product, quantity) -> receiptEntries.add(new ReceiptEntry(product,quantity)));

        return new Receipt(receiptEntries);
    }
}
