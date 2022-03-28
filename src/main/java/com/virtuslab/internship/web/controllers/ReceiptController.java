package com.virtuslab.internship.web.controllers;

import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.web.services.BasketService;
import com.virtuslab.internship.web.services.ReceiptService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/receipt")
@RestController
@AllArgsConstructor
public class ReceiptController {

    private final ReceiptService receiptService;
    private final BasketService basketService;

    @GetMapping("/{basketId}")
    @ResponseBody
    public ResponseEntity<Receipt> createRecipe(@PathVariable Long basketId) {
        basketService.findById(basketId);
        Receipt receipt = receiptService.generateReceipt(basketService.findById(basketId));

        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }
}
