package com.orderservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSendEvent {

    private String productName;
    private String barCode;
    private int quantity;
    private BigDecimal price;

}