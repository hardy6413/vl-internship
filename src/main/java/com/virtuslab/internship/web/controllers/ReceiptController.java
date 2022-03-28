package com.virtuslab.internship.web.controllers;

import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.web.services.BasketService;
import com.virtuslab.internship.web.services.ReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/receipt")
@RestController
public class ReceiptController {

    private final ReceiptService receiptService;
    private final BasketService basketService;

    @Autowired
    public ReceiptController(ReceiptService receiptService, BasketService basketService) {
        this.receiptService = receiptService;
        this.basketService = basketService;
    }

    @GetMapping("/{basketId}")
    @ResponseBody
    public ResponseEntity<Receipt> getReceipt(@PathVariable Long basketId) {
        basketService.findById(basketId);
        var receipt = receiptService.generateReceipt(basketService.findById(basketId));

        log.debug("Getting receipt for Basket with ID:" + basketId);

        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }
}
