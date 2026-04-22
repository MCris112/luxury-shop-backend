package com.darkredgm.luxury.Order.Services;

import com.darkredgm.luxury.Order.Exceptions.OrderNoItemException;
import com.darkredgm.luxury.Order.Models.Order;
import com.darkredgm.luxury.Order.Models.OrderItem;
import com.darkredgm.luxury.Order.OrderStore;
import com.darkredgm.luxury.Order.OrderStoreItem;
import com.darkredgm.luxury.Order.Repository.OrderRepository;
import com.darkredgm.luxury.Payment.PaymentMethodData;
import com.darkredgm.luxury.Payment.Services.PaymentManager;
import com.darkredgm.luxury.Product.Models.Product;
import com.darkredgm.luxury.Product.Repository.ProductRepository;
import com.darkredgm.luxury.User.Models.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentManager paymentManager;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, PaymentManager paymentManager) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.paymentManager = paymentManager;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order save(OrderStore data, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(data.paymentMethod());
        order.setAddress(data.address());
        order.setItems(new ArrayList<>());
        float total = 0;

        if (data.items().isEmpty())
            throw new OrderNoItemException();

        for (OrderStoreItem dataItem : data.items()) {
            OrderItem item = new OrderItem();
            // Fetch product details to ensure price and name are correct
            Product product = productRepository.findById(dataItem.product().id())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            item.setProduct(product);
            item.setOrder(order);
            item.setUnitPrice(product.getPrice());
            item.setQuantity(dataItem.quantity());

            total += product.getPrice() * item.getQuantity();
            order.getItems().add(item);
        }

        order.setTotals(total);

        // Dynamically process payment using the Strategy Pattern via PaymentManager!
        PaymentMethodData paymentData = paymentManager.process(order);
        if (paymentData != null) {
            order.setPayment(paymentData);
        }

        return orderRepository.save(order);
    }

    public Optional<Order> update(Long id, Order orderDetails) {
        return orderRepository.findById(id).map(order -> {
            if (orderDetails.getPaymentMethod() != null) order.setPaymentMethod(orderDetails.getPaymentMethod());
            if (orderDetails.getTotals() != 0) order.setTotals(orderDetails.getTotals());
            if (orderDetails.getUser() != null) order.setUser(orderDetails.getUser());

            if (orderDetails.getPayment() != null) {
                order.setPayment(orderDetails.getPayment());
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
