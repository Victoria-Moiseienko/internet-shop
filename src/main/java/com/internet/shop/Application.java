package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import java.util.List;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");
    private static final Long PEAR_ID = 2L;
    private static final Long APPLE_ID = 1L;

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        productService.create(new Product("apple", 25.5));
        productService.create(new Product("pear", 35.5));
        productService.create(new Product("plum", 20));

        productService.delete(APPLE_ID);

        Product product = productService.get(PEAR_ID);
        product.setPrice(32);
        productService.update(product);

        List<Product> productList = productService.getAllProducts();
        for (Product prod : productList) {
            System.out.println(prod);
        }
    }
}
