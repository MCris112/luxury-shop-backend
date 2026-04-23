package com.darkredgm.luxury.Payment.Converters;

import com.darkredgm.luxury.Payment.PaymentMethodData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PaymentMethodDataConverter implements AttributeConverter<PaymentMethodData, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(PaymentMethodData attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize PaymentMethodData to JSON", e);
        }
    }

    @Override
    public PaymentMethodData convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, PaymentMethodData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not deserialize JSON to PaymentMethodData", e);
        }
    }
}
