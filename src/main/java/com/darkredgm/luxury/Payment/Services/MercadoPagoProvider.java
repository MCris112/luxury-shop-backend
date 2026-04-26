package com.darkredgm.luxury.Payment.Services;

import com.darkredgm.luxury.Order.Models.Order;
import com.darkredgm.luxury.Order.Models.OrderItem;
import com.darkredgm.luxury.Payment.PaymentMethodData;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoProvider implements PaymentProvider {

    @Value("${mercadopago.public-key}")
    private String publicKey;

    @Override
    public boolean supports(String paymentMethod) {
        return "mercadopago".equalsIgnoreCase(paymentMethod);
    }

    @Override
    public PaymentMethodData processPayment(Order order) {
        List<PreferenceItemRequest> preferenceItems = new ArrayList<>();

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new RuntimeException("Cannot process payment for an order with no items.");
        }

        for (OrderItem item : order.getItems()) {
            if (item.getProduct() != null) {
                // Mercado Pago requires quantity > 0 and unitPrice >= 0
                int qty = item.getQuantity();
                BigDecimal price = new BigDecimal(String.valueOf(item.getProduct().getPrice()));
                
                if (qty <= 0) continue; 

                PreferenceItemRequest mpItem = PreferenceItemRequest.builder()
                        .id(String.valueOf(item.getProduct().getId()))
                        .title(item.getProduct().getName() != null ? item.getProduct().getName() : "Luxury Product")
                        .quantity(qty)
                        .unitPrice(price)
                        .currencyId("PEN")
                        .build();

                preferenceItems.add(mpItem);
            }
        }

        if (preferenceItems.isEmpty()) {
            throw new RuntimeException("No valid items found to process with Mercado Pago.");
        }

        try {
            PreferenceClient client = new PreferenceClient();

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://localhost:3000/checkout/success")
                    .pending("https://localhost:3000/checkout/error")
                    .failure("https://localhost:3000/checkout/error")
                    .build();

            PreferenceRequest request = PreferenceRequest.builder()
                    .backUrls( backUrls )
                    .autoReturn("approved")
                    .items(preferenceItems)
                    .binaryMode(true)
                    // Adding Payer info helps prevent some MP rejection errors
                    .payer(com.mercadopago.client.preference.PreferencePayerRequest.builder()
                            .email(order.getUser() != null && order.getUser().getEmail() != null && !order.getUser().getEmail().isEmpty() 
                                   ? order.getUser().getEmail() : "test_user_v1@testuser.com") 
                            .build())
                    .build();

            Preference preference = client.create(request);

            PaymentMethodData paymentMethodData = new PaymentMethodData();
            paymentMethodData.setPaymentId(preference.getId());
            
            paymentMethodData.setExtraData("publicKey", publicKey);
            paymentMethodData.setExtraData("initPoint", preference.getInitPoint());
            
            return paymentMethodData;

        } catch (MPApiException e) {
            String errorMsg = "MP API Error: " + e.getApiResponse().getContent();
            System.err.println(errorMsg);
            throw new RuntimeException("MercadoPago API Error: " + errorMsg, e);
        } catch (MPException e) {
            System.err.println("MP SDK Error: " + e.getMessage());
            throw new RuntimeException("MercadoPago SDK Error: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("General Error in Payment Process: " + e.getMessage());
            throw new RuntimeException("Unexpected error processing payment: " + e.getMessage(), e);
        }
    }
}
