package com.darkredgm.luxury.Order.Services;

import com.darkredgm.luxury.Order.Models.Order;
import com.darkredgm.luxury.Order.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order save(Order order) {
        // Ensure each item has a reference to the order for JPA cascade to work correctly
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }
        return orderRepository.save(order);
    }

    public Optional<Order> update(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            if (orderDetails.getPaymentMethod() != null) order.setPaymentMethod(orderDetails.getPaymentMethod());
            if (orderDetails.getTotals() != 0) order.setTotals(orderDetails.getTotals());
            if (orderDetails.getUser() != null) order.setUser(orderDetails.getUser());
            
            // Updating items can be complex; typically you might replace the list or handle specifically
            if (orderDetails.getItems() != null) {
                order.getItems().clear();
                orderDetails.getItems().forEach(item -> {
                    item.setOrder(order);
                    order.getItems().add(item);
                });
            }
            
            return orderRepository.save(order);
        });
    }

    public boolean deleteById(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
