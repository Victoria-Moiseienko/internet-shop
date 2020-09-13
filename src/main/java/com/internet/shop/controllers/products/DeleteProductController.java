package com.internet.shop.controllers.products;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("productId"));
        String deletedProductName = productService.get(id).getName();
        productService.delete(id);

        List<Product> allProducts = productService.getAll();
        req.setAttribute("message", deletedProductName + " was deleted");
        req.setAttribute("products", allProducts);
        req.getRequestDispatcher(
                "/WEB-INF/views/products/allProductsAdmin.jsp").forward(req, resp);
    }
}
