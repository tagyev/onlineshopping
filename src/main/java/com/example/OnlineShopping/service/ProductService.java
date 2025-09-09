package com.example.OnlineShopping.service;

import com.example.OnlineShopping.dto.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductService {
    final Map<Long, ProductEntity> store = new HashMap<>();
    final Map<Long, Integer> card = new HashMap<>();
    final AtomicLong productIdGenerator = new AtomicLong(1);
    static BigDecimal balance = new BigDecimal("500.00");


    public Map<Long, ProductEntity> saveProductsToStore(List<ProductRequest> marketEnter) {
        for (ProductRequest request : marketEnter) {
            Long id = productIdGenerator.getAndIncrement();
            ProductEntity entity = new ProductEntity(id, request.getName(), request.getPrice(), request.getQuantity());
            store.put(id, entity);
        }
        return store;
    }

    public boolean addProductsToCart(BuyingRequest buy) {
        ProductEntity product = store.get(buy.getProductId());

        if (product == null) {
            throw new RuntimeException("Məhsul mağazamızda yoxdur!");
        }

        if (product.getQuantity() < buy.getQuantity()) {
            throw new RuntimeException("Bu məhsuldan istədiyiniz qədər yoxdur!");
        }
        card.put(buy.getProductId(), buy.getQuantity());
        return true;
    }

    public PaymentResponse payment() {
        BigDecimal totalPrice = new BigDecimal("0");
        List<String> productName = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : card.entrySet()) {
            Long id = entry.getKey();
            Integer quality = entry.getValue();
            ProductEntity product = store.get(id);
            if (product == null) {
                throw new RuntimeException("Heç bir məhsul seçilməyib !");
            } else {
                BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(quality));
                productName.add(product.getName() +" - "+ quality +" x " + product.getPrice());
                totalPrice = totalPrice.add(price);
            }
        }

        // balansdan çıx
        if (balance.compareTo(totalPrice) < 0) {
            throw new RuntimeException("Balansınızda kifayət qədər məbləğ yoxdur! ");
        } else {
            balance = balance.subtract(totalPrice);

            // store mapdan çıx məhsulu
            for (Map.Entry<Long, Integer> entry : card.entrySet()) {
                Long id = entry.getKey();
                Integer quality = entry.getValue();
                ProductEntity product = store.get(id);
                store.get(id).setQuantity(store.get(id).getQuantity() - quality);
            }
        }
        card.clear();
        return new PaymentResponse(totalPrice, balance, productName);
    }

    public List<String> showProducts(){
        List<String> show = new ArrayList<>();
        for (ProductEntity entity : store.values()) {
            show.add(entity.getName() +" - "+ entity.getQuantity() + " + "+ entity.getPrice());
        }
        return show;
    }


}
