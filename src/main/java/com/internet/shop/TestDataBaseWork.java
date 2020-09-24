package com.internet.shop;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.dao.UserDao;
import com.internet.shop.dao.jdbc.ProductDaoJdbcImpl;
import com.internet.shop.dao.jdbc.ShoppingCartDaoJdbcImpl;
import com.internet.shop.dao.jdbc.UserDaoJdbcImpl;
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
    }
}
