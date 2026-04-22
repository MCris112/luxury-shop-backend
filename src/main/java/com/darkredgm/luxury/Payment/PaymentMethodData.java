package com.darkredgm.luxury.Payment;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class PaymentMethodData {

    private String paymentId;
    
    // Flexible fields for different payment providers
    private Map<String, Object> extraData = new HashMap<>();

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @JsonAnyGetter
    public Map<String, Object> getExtraData() {
        return extraData;
    }

    @JsonAnySetter
    public void setExtraData(String key, Object value) {
        this.extraData.put(key, value);
    }
}
