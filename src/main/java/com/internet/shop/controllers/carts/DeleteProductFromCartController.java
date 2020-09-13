package com.internet.shop.controllers.carts;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductFromCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("productId"));
        Product product = productService.get(id);
        shoppingCartService.deleteProduct(shoppingCartService.getByUserId(USER_ID),
                product);

        List<Product> productsInCart =
                shoppingCartService.getByUserId(USER_ID).getProducts();

        req.setAttribute("products", productsInCart);
        req.getRequestDispatcher("/WEB-INF/views/carts/shoppingCart.jsp").forward(req, resp);
    }
}
