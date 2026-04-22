package com.darkredgm.luxury.Order;

public record OrderStoreItem(
        OrderStoreItemProduct product,
        int quantity
){}