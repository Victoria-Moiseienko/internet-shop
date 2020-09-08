package com.internet.shop.dao;

import com.internet.shop.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Order create(Order order);

    Order update(Order order);

    Optional<Order> getByUserId(Long userId);

    List<Order> getAll();

    boolean delete(Order order);
}
