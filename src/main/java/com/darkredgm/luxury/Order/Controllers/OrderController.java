package com.darkredgm.luxury.Order.Controllers;

import com.darkredgm.luxury.Order.Models.Order;
import com.darkredgm.luxury.Order.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> index() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> show(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> store(@RequestBody Order order) {
        Order savedOrder = orderService.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order order) {
        return orderService.update(id, order)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (orderService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
