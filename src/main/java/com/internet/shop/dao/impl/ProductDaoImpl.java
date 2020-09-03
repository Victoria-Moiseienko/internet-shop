package com.internet.shop.dao.impl;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.db.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long productId) {
        return Storage.products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }

    @Override
    public Product update(Product product) {
        getAllProducts().stream()
                .filter(prod -> prod.getId().equals(product.getId()))
                .forEach(prod -> prod = product);

        return product;
    }

    @Override
    public boolean delete(Long productId) {
        return Storage.products.remove(get(productId).orElseThrow());
    }

    @Override
    public List<Product> getAllProducts() {
        return Storage.products;
    }
}
