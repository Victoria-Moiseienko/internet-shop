package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private List<Product> products;
    private Long userId;

    public Order(Long userId) {
        this.products = new ArrayList<>();
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getId() {
        return id;
    }

    public Object getUserId() {
        return userId;
    }
}
