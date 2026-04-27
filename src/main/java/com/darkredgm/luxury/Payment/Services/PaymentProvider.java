package com.darkredgm.luxury.Payment.Services;

import com.darkredgm.luxury.Order.Models.Order;
import com.darkredgm.luxury.Payment.PaymentMethodData;


/**
 * This helps in case I change the "paymentMethod" to paypal/etc. Wont be need
 * to replace the code, only by implementing this
 */
public interface PaymentProvider {
    /**
     * Checks if this provider handles the specified payment method string (e.g., "mercadopago")
     */
    boolean supports(String paymentMethod);

    /**
     * Processes the order and generates the required PaymentMethodData
     */
    PaymentMethodData processPayment(Order order);
}
