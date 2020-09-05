package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import java.util.List;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    private static Product apple = new Product("apple", 25.5);
    private static Product pear = new Product("pear", 35.5);
    private static Product plum = new Product("plum", 20);

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        productService.create(apple);
        productService.create(pear);
        productService.create(plum);

        productService.delete(apple.getId());

        Product product = productService.get(pear.getId());
        product.setPrice(32);
        productService.update(product);

        List<Product> productList = productService.getAllProducts();
        for (Product prod : productList) {
            System.out.println(prod);
        }
    }
}
