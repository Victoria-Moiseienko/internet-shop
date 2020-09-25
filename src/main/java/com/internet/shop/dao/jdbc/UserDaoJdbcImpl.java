package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.UserDao;
import com.internet.shop.exeptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.util.DbConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT *, group_concat(roles.role_name SEPARATOR ',') AS role_titles"
                        + " FROM users"
                        + " JOIN users_roles ON users.user_id = users_roles.user_id"
                        + " JOIN roles ON users_roles.role_id = roles.role_id"
                        + " where users.login = ?";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet sqlResult = preparedStatement.executeQuery();
            if (sqlResult.next()) {
                User user = parseRow(sqlResult);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by login = " + login, e);
        }
        return Optional.empty();
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (user_name, password, login)"
                + " VALUES (?, ?, ?)";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.executeUpdate();
            ResultSet resultKey = preparedStatement.getGeneratedKeys();
            if (resultKey.next()) {
                user.setId(resultKey.getLong(1));
            }
            addUserRoles(user, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("User with login " + user.getLogin()
                    + " has not been added", e);
        }
        return user;
    }

    private void addUserRoles(User user, Connection connection) throws SQLException {
        for (Role role : user.getRoles()) {
            String query = "SELECT role_id FROM roles WHERE role_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, role.getStringRoleName());
            ResultSet sqlResult = preparedStatement.executeQuery();
            if (sqlResult.next()) {
                Long roleId = sqlResult.getLong("role_id");
                insertUserRole(user.getId(), roleId, connection);
            } else {
                throw new DataProcessingException("Role " + role + " has not been found");
            }
        }
    }

    private int insertUserRole(Long userId, Long roleId, Connection connection)
            throws SQLException {
        String query = "INSERT INTO users_roles (user_id, role_id)"
                + " VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, roleId);
        return preparedStatement.executeUpdate();
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT *, group_concat(roles.role_name SEPARATOR ',') AS role_titles"
                        + " FROM users"
                        + " JOIN users_roles ON users.user_id = users_roles.user_id"
                        + " JOIN roles ON users_roles.role_id = roles.role_id"
                        + " where users.user_id = ?";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet sqlResult = preparedStatement.executeQuery();
            if (sqlResult.next()) {
                User user = parseRow(sqlResult);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by id = " + id, e);
        }
        return Optional.empty();
    }

    private User parseRow(ResultSet sqlResult) throws SQLException {
        Long id = sqlResult.getLong("user_id");
        String name = sqlResult.getString("user_name");
        String login = sqlResult.getString("login");
        String password = sqlResult.getString("password");
        String[] rolesStringArr = sqlResult.getString("role_titles").split(",");
        Set<Role> roles = new HashSet();
        for (String str : rolesStringArr) {
            roles.add(Role.of(str));
        }
        return new User(id, name, login, password, roles);
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users"
                + " SET user_name = ?, password = ?, login = ? "
                + " WHERE user_id = ?";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, user.getName());
            prepareStatement.setString(2, user.getPassword());
            prepareStatement.setString(3, user.getLogin());
            prepareStatement.setLong(4, user.getId());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("User with id " + user.getId()
                    + " has not been updated", e);
        }
        return user;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE users"
                + " SET deleted = true "
                + " WHERE user_id = ?";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("User with id " + id
                    + " has not been updated", e);
        }
        return true;
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT *, roles.role_name AS role_titles"
                + " FROM users"
                + " JOIN users_roles ON users.user_id = users_roles.user_id"
                + " JOIN roles ON users_roles.role_id = roles.role_id"
                + " where users.deleted = false";
        List<User> userList = new ArrayList<>();
        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet sqlResult = preparedStatement.executeQuery();
            while (sqlResult.next()) {
                userList.add(parseRow(sqlResult));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Users have not been selected", e);
        }
        return userList;
    }
}
