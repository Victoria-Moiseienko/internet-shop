package com.internet.shop.controllers;

import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductController extends HttpServlet {
    @Inject
    private static Injector injector = Injector.getInstance("com.internet.shop");
    ProductService productService = (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/products/addProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getParameter("productname");
        String price = req.getParameter("price");
        System.out.println(productName + " " + price);
        productService.create(new Product(productName, Double.parseDouble(price)));

        req.setAttribute("message", productName + " was added");
        req.getRequestDispatcher("/WEB-INF/views/products/addProduct.jsp").forward(req,resp);
    }
}
