package com.internet.shop.security.impl;

import com.internet.shop.exeptions.AuthenticationException;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.User;
import com.internet.shop.security.AuthenticationService;
import com.internet.shop.service.UserService;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByLogin(login);

        if (userFromDB.isPresent() && userFromDB.get().getPassword().equals(password)) {
            return userFromDB.get();
        }

        throw new AuthenticationException("Incorrect username or user password");
    }
}
