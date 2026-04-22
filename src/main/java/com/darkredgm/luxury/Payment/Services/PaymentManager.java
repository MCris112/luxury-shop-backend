package com.darkredgm.luxury.Payment.Services;

import com.darkredgm.luxury.Order.Models.Order;
import com.darkredgm.luxury.Payment.PaymentMethodData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentManager {

    private final List<PaymentProvider> paymentProviders;

    @Autowired
    public PaymentManager(List<PaymentProvider> paymentProviders) {
        // Spring automatically injects all beans implementing PaymentProvider!
        this.paymentProviders = paymentProviders;
    }

    public PaymentMethodData process(Order order) {
        if (order.getPaymentMethod() == null) {
            throw new RuntimeException("Payment method is required");
        }

        for (PaymentProvider provider : paymentProviders) {
            if (provider.supports(order.getPaymentMethod())) {
                return provider.processPayment(order);
            }
        }

        throw new RuntimeException("Payment method not supported: " + order.getPaymentMethod());
    }
}
