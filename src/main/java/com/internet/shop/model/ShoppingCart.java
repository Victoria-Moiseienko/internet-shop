package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private Long userId;

    public ShoppingCart(Long userId) {
        this.products = new ArrayList<>();
        this.userId = userId;
    }

    public ShoppingCart(Long id, Long userId, List<Product> productList) {
        this.id = id;
        this.userId = userId;
        this.products = productList;
    }

    public ShoppingCart(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
        this.products = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ShoppingCart{"
                + "id=" + id
                + ", products=" + products.toString()
                + ", userId=" + userId
                + '}';
    }
}
