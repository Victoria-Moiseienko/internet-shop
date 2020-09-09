package com.internet.shop.controllers;

import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController extends HttpServlet {
    @Inject
    private static Injector injector = Injector.getInstance("com.internet.shop");
    UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user1 = new User("Vasil", "fruitlover", "qwerty");
        User user2 = new User("Maria", "mariafruit", "asdfgh");
        userService.create(user1);
        userService.create(user2);

        List<User> allUsers = userService.getAll();

        req.setAttribute("users", allUsers);
        req.getRequestDispatcher("/WEB-INF/views/users/all.jsp").forward(req, resp);
    }
}

