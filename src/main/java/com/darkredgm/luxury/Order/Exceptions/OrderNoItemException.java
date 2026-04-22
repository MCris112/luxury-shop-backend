package com.darkredgm.luxury.Order.Exceptions;

public class OrderNoItemException extends RuntimeException {
    public OrderNoItemException() {
        super("Debe tener como minimo un producto para poder comprar");
    }
}
