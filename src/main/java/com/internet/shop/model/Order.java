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

    public Order(Long id, Long userId, List<Product> productList) {
        this.id = id;
        this.products = productList;
        this.userId = userId;
    }

    public Order(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Order{"
                + "id=" + id
                + ", products=" + products
                + ", userId=" + userId
                + '}';
    }
}
