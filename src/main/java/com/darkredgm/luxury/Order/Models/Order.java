package com.darkredgm.luxury.Order.Models;

import com.darkredgm.luxury.User.Models.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders") // "order" is often a reserved word in SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String paymentMethod;

    private float totals;

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
}
