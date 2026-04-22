package com.darkredgm.luxury.Payment.Config;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfiguration {

    @Value("${mercadopago.access-token}")
    private String accessToken;

    @PostConstruct
    public void init() {
        if (accessToken == null || accessToken.isEmpty() || accessToken.equals("YOUR_ACCESS_TOKEN")) {
            throw new IllegalStateException("Mercado Pago Access Token is not configured in application.properties");
        }
        MercadoPagoConfig.setAccessToken(accessToken);
    }
}
