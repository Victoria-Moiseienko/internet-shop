package com.internet.shop.factory;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.dao.impl.ProductDaoImpl;

public class Factory {
    private static ProductDao productDao;

    public static ProductDao getProductDao() {
        if (productDao == null) {
            productDao = new ProductDaoImpl();
        }
        return productDao;
    }
}
