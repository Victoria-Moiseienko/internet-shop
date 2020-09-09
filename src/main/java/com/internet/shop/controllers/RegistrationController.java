package com.internet.shop.controllers;

import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController extends HttpServlet {
    @Inject
    private static Injector injector = Injector.getInstance("com.internet.shop");
    UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pwd");
        String passwordRepeat = req.getParameter("pwd-repeat");

        if (password.equals(passwordRepeat)) {
            userService.create(new User("", login, password));
            //redirect to main
            resp.sendRedirect(req.getContextPath() + "/");
            //TODO: add login to page
        } else {
            //show correct data message
            req.setAttribute("message", "password and repeat password are not equal");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req,resp);
        }
    }
}
