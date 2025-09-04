package com.example.OnlineShopping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyingRequest {
    Long productId;
    Integer quantity;
}
