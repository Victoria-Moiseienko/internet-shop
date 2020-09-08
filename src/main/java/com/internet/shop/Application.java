package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    private static Product apple = new Product("apple", 25.5);
    private static Product pear = new Product("pear", 35.5);
    private static Product plum = new Product("plum", 20);

    private static User user1 = new User("Vasil", "fruitlover", "qwerty");
    private static User user2 = new User("Maria", "mariafruit", "asdfgh");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        productService.create(apple);
        productService.create(pear);
        productService.create(plum);

        UserService userService = (UserService) injector.getInstance(UserService.class);
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

        userService.create(user1);
        ShoppingCart shoppingCart1 = new ShoppingCart(user1.getId());
        shoppingCartService.create(shoppingCart1);
        shoppingCartService.addProduct(shoppingCart1, plum);
        shoppingCartService.addProduct(shoppingCart1, pear);

        userService.create(user2);
        ShoppingCart shoppingCart2 = new ShoppingCart(user2.getId());
        shoppingCartService.create(shoppingCart2);

        System.out.println("User and empty shopping cart:");
        System.out.println(user2);
        System.out.println(shoppingCartService.getByUserId(user2.getId()));

        System.out.println("\nUser and shopping cart:");
        System.out.println(user1);
        System.out.println(shoppingCartService.getByUserId(user1.getId()));

        shoppingCartService.deleteProduct(shoppingCart1, pear);
        System.out.println("\nUser and updated shopping cart:");
        System.out.println(user1);
        System.out.println(shoppingCartService.getByUserId(user1.getId()));
    }
}
