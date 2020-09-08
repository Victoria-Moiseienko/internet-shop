package com.internet.shop.service;

import com.internet.shop.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    User get(Long userId);

    User update(User user);

    boolean delete(Long userId);

    List<User> getAllUsers();
}
