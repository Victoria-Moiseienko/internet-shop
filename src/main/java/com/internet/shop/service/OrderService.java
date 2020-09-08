package com.internet.shop.service;

import com.internet.shop.model.Order;
import java.util.List;

public interface OrderService {
    Order create(Order order);

    Order update(Order order);

    Order getByUserId(Long userId);

    List<Order> getAll();

    boolean delete(Order order);
}
