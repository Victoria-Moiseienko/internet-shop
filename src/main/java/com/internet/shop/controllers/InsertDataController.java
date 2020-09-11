package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private static final Product product1 = new Product("apple", 25.5);
    private static final Product product2 = new Product("pear", 35.5);
    private static final Product product3 = new Product("plum", 20);
    private static final User user1 = new User("Vasil", "fruitlover", "qwerty");
    private static final User user2 = new User("Maria", "mariafruit", "asdfgh");

    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/insertdata.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        userService.create(user1);
        ShoppingCart shoppingCart1 = new ShoppingCart(user1.getId());
        shoppingCartService.create(shoppingCart1);

        userService.create(user2);
        ShoppingCart shoppingCart2 = new ShoppingCart(user2.getId());
        shoppingCartService.create(shoppingCart2);

        productService.create(product1);
        productService.create(product2);
        productService.create(product3);

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
