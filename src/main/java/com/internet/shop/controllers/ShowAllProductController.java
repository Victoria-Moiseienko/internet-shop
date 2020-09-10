package com.internet.shop.controllers;

import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowAllProductController extends HttpServlet {
    private static final Long USER_ID = 1L;

    @Inject
    private static Injector injector = Injector.getInstance("com.internet.shop");
    ProductService productService = (ProductService) injector.getInstance(ProductService.class);
    ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> allProducts = productService.getAll();

        req.setAttribute("products", allProducts);
        req.getRequestDispatcher("/WEB-INF/views/products/showProducts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productId = req.getParameter("productId");
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);

        shoppingCartService.addProduct(shoppingCart, productService.get(Long.parseLong(productId)));
    }
}
