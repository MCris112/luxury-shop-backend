package com.darkredgm.luxury.Order.Models;

import com.darkredgm.luxury.User.Models.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;
}
