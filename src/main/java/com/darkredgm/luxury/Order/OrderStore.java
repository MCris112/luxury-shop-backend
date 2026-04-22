package com.darkredgm.luxury.Order;


import java.util.List;

public record OrderStore(
        OrderStoreUser user,
        String address,

        String paymentMethod,

        List<OrderStoreItem> items
) {
}
