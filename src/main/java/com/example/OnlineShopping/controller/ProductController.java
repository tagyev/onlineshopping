package com.example.OnlineShopping.controller;

import com.example.OnlineShopping.dto.BuyingRequest;
import com.example.OnlineShopping.dto.PaymentResponse;
import com.example.OnlineShopping.dto.ProductRequest;
import com.example.OnlineShopping.dto.ProductResponse;
import com.example.OnlineShopping.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {
    final ProductService service;

    @PostMapping("/products")
    public List<ProductResponse> saveProductsToStore(@RequestBody List<ProductRequest> marketEnter) {
        return service.saveProductsToStore(marketEnter);
    }

    @PostMapping("/card")
    public boolean addProductsToCart(@RequestBody BuyingRequest buy) {
        return service.addProductsToCart(buy);
    }

    @GetMapping("/payment")
    public PaymentResponse payment() {
        return service.payment();
    }


}
