package com.internet.shop;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.dao.jdbc.ProductDaoJdbcImpl;
import com.internet.shop.model.Product;
import java.util.List;

public class TestDataBaseWork {
    public static void main(String[] args) {
        ProductDao dao = new ProductDaoJdbcImpl();
        //insert
        Product testItem = dao.create(new Product("watermelon", 79));
        //select
        dao.get(testItem.getId()).ifPresent(product -> System.out.println(product.toString()));
        //update
        testItem.setPrice(78);
        dao.update(testItem);
        dao.get(testItem.getId()).ifPresent(product -> System.out.println(product.toString()));
        //delete
        dao.delete(testItem.getId());
        //select all
        List<Product> all = dao.getAll();
        for (Product product : all) {
            System.out.println(product);
        }
    }
}
