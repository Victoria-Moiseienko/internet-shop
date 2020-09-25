package com.internet.shop;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.dao.ProductDao;
import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.dao.UserDao;
import com.internet.shop.dao.jdbc.OrderDaoJdbcImpl;
import com.internet.shop.dao.jdbc.ProductDaoJdbcImpl;
import com.internet.shop.dao.jdbc.ShoppingCartDaoJdbcImpl;
import com.internet.shop.dao.jdbc.UserDaoJdbcImpl;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.Role;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import java.util.List;
import java.util.Set;

public class TestDataBaseWork {
    public static void main(String[] args) {
        //PRODUCTDAO
        ProductDao dao = new ProductDaoJdbcImpl();
        //insert
        Product testItem = dao.create(new Product("watermelon", 79));
        //select

        dao.get(testItem.getId()).ifPresent(product -> System.out.println(product.toString()));
        //update
        testItem.setPrice(78);
        //dao.update(testItem);
        dao.get(testItem.getId()).ifPresent(product -> System.out.println(product.toString()));
        //delete
        dao.delete(testItem.getId());
        //select all
        List<Product> all = dao.getAll();
        for (Product product : all) {
            System.out.println(product);
        }

        //USERDAO
        UserDao userDao = new UserDaoJdbcImpl();
        //findByLogin
        userDao.findByLogin("Nikusya12").ifPresent(user -> System.out.println(user.toString()));
        //insert
        User testUser = userDao.create(new User("Nika", "Nikusya16",
                "123", Set.of(Role.of("USER"))));

        //select
        userDao.get(2L).ifPresent(user -> System.out.println(user.toString()));

        //update
        testUser.setLogin("login");
        userDao.update(testUser);

        //delete
        userDao.delete(testUser.getId());
        //select all
        System.out.println("select All:");
        List<User> usersAll = userDao.getAll();
        usersAll.forEach(System.out::println);

        //shopping cart
        //create
        ShoppingCart shoppingCart = new ShoppingCart(2L);
        ShoppingCartDao shoppingCartDao = new ShoppingCartDaoJdbcImpl();
        shoppingCartDao.create(shoppingCart);
        //update
        ProductDao productDao = new ProductDaoJdbcImpl();
        Product testProduct = productDao.get(4L).get();
        shoppingCart.getProducts().add(testProduct);
        shoppingCartDao.update(shoppingCart);

        //select
        shoppingCartDao.get(shoppingCart.getId())
                .ifPresent(cart -> System.out.println("By Id:\n" + cart));
        shoppingCartDao.getByUserId(2L)
                .ifPresent(cart -> System.out.println("By UserId:\n" + cart));

        //delete
        shoppingCartDao.delete(1L);
        //select all
        System.out.println("All:");
        List<ShoppingCart> cartListAll = shoppingCartDao.getAll();
        cartListAll.forEach(System.out::println);

        //ORDER
        //create
        testProduct = productDao.get(2L).get();

        Order testOrder = new Order(23L);
        testOrder.setProducts(List.of(testProduct));

        OrderDao orderDao = new OrderDaoJdbcImpl();
        orderDao.create(testOrder);
        //select
        orderDao.get(testOrder.getId())
                .ifPresent(order -> System.out.println("Order by ID:\n" + order.toString()));
        //update
        Product testProduct2 = productDao.get(1L).get();
        testOrder.setProducts(List.of(testProduct, testProduct2));
        orderDao.update(testOrder);
        orderDao.get(testOrder.getId())
                .ifPresent(order -> System.out.println(
                        "Updated order by ID:\n" + order.toString()));
        //delete
        orderDao.delete(testOrder.getId());
        //select all
        System.out.println("All:");
        List<Order> orderList = orderDao.getAll();
        orderList.forEach(System.out::println);

        System.out.println("All for user:");
        List<Order> orderUserList = orderDao.getUserOrders(23L);
        orderUserList.forEach(System.out::println);
    }
}
