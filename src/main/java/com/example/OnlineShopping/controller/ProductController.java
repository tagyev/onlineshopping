package com.example.OnlineShopping.controller;

import com.example.OnlineShopping.dto.*;
import com.example.OnlineShopping.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {
    final ProductService service;

    @PostMapping("/products")
    public Map<Long, ProductEntity> saveProductsToStore(@RequestBody List<ProductRequest> marketEnter) {
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

    @GetMapping("/show")
    public List<String> showProducts(){
        return service.showProducts();
    }


}
