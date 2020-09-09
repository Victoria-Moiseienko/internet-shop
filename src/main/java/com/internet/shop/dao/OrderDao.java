package com.internet.shop.dao;

import com.internet.shop.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderDao extends GenericDao<Order, Long> {

    Optional<Order> getByUserId(Long userId);

    List<Order> getUserOrders(Long userId);
}
