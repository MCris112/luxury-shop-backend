package com.darkredgm.luxury.Order.Models;

import com.darkredgm.luxury.Payment.PaymentMethodData;
import com.darkredgm.luxury.User.Models.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "orders") // "order" is often a reserved word in SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String address;

    private String paymentMethod;

    private float totals;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private PaymentMethodData payment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public float getTotals() {
        return totals;
    }

    public void setTotals(float totals) {
        this.totals = totals;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PaymentMethodData getPayment() {
        return payment;
    }

    public void setPayment(PaymentMethodData payment) {
        this.payment = payment;
    }
}
