package com.internet.shop.controllers.carts;

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

public class AddProductToCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long productId = Long.valueOf(req.getParameter("productId"));
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);

        shoppingCartService.addProduct(shoppingCart, productService.get(productId));
        List<Product> productList = productService.getAll();
        req.setAttribute("products", productList);
        req.setAttribute("message",
                productService.get(productId).getName() + " was added to shopping cart");
        req.getRequestDispatcher("/WEB-INF/views/products/showProducts.jsp").forward(req,resp);
    }
}
