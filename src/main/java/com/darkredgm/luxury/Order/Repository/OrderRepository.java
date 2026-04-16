package com.darkredgm.luxury.Order.Repository;

import com.darkredgm.luxury.Order.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
