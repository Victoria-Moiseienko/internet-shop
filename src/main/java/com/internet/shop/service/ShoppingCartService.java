package com.internet.shop.service;

import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import java.util.List;

public interface ShoppingCartService {

    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart update(ShoppingCart shoppingCart);

    ShoppingCart getByUserId(Long userId);

    List<ShoppingCart> getAll();

    boolean delete(ShoppingCart shoppingCart);

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);
}
