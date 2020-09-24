package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.exeptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.util.DbConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    private static final String TABLE_ORDER = "orders";
    private static final String TABLE_ORDER_PRODUCT = "orders_products";
    private static final String TABLE_PRODUCTS = "products";

    @Override
    public List<Order> getUserOrders(Long userId) {
        String query = String.format("SELECT * FROM %s AS o"
                        + " JOIN %s AS op ON o.order_id = op.orders_id"
                        + " JOIN %s AS p ON p.product_id = op.products_id"
                        + " WHERE o.user_id = ? AND o.deleted = false ",
                TABLE_ORDER, TABLE_ORDER_PRODUCT, TABLE_PRODUCTS);

        List<Order> orderList = new ArrayList<>();
        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet sqlResult = preparedStatement.executeQuery();
            while (sqlResult.next()) {
                Long id = sqlResult.getLong("order_id");
                Order order = new Order(id, userId);
                orderList.add(order);
            }
            for (Order order : orderList) {
                order.setProducts(extractProductForOrder(order.getId(), connection));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get all orders for user with id = " + userId, e);
        }
        return orderList;
    }

    @Override
    public Order create(Order order) {
        String query = String.format("INSERT INTO %s (user_id)"
                + " VALUES (?)", TABLE_ORDER);

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultKey = preparedStatement.getGeneratedKeys();
            if (resultKey.next()) {
                order.setId(resultKey.getLong(1));
            }
            insertProducts(order, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Order for user with id " + order.getUserId()
                    + " has not been added", e);
        }
        return order;
    }

    private int insertProducts(Order order, Connection connection) throws SQLException {
        String query = String.format("INSERT INTO %s (orders_id, products_id) "
                + "VALUES (?, ?)", TABLE_ORDER_PRODUCT);
        int result = 0;
        for (Product product : order.getProducts()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, order.getId());
            preparedStatement.setLong(2, product.getId());
            result += preparedStatement.executeUpdate();
        }
        return result;
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = String.format("SELECT * FROM %s"
                        + " JOIN %s ON orders.order_id"
                        + " = orders_products.orders_id"
                        + " JOIN %s ON products.product_id"
                        + " = orders_products.products_id"
                        + " where orders.order_id = ? ",
                TABLE_ORDER, TABLE_ORDER_PRODUCT, TABLE_PRODUCTS);

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet sqlResult = preparedStatement.executeQuery();
            if (sqlResult.next()) {
                Order order = parseResult(sqlResult);
                return Optional.of(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order by id = " + id, e);
        }
        return Optional.empty();
    }

    private Order parseResult(ResultSet sqlResult) throws SQLException {
        Long id = sqlResult.getLong("order_id");
        Long userId = sqlResult.getLong("user_id");
        List<Product> productList = new ArrayList<>();
        do {
            productList.add(parseProduct(sqlResult));
        } while (sqlResult.next());
        return new Order(id, userId, productList);
    }

    private Product parseProduct(ResultSet sqlResult) throws SQLException {
        Long productId = sqlResult.getLong("product_id");
        String name = sqlResult.getString("name");
        Double price = sqlResult.getDouble("price");
        return new Product(productId, name, price);
    }

    @Override
    public Order update(Order order) {
        String query = String.format("DELETE FROM %s"
                        + " WHERE orders_id = ?",
                TABLE_ORDER_PRODUCT);

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, order.getId());
            preparedStatement.executeUpdate();
            insertProducts(order, connection);
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update order for user = " + order.getUserId(), e);
        }
        return order;
    }

    @Override
    public boolean delete(Long id) {
        String query = String.format("UPDATE %s"
                        + " SET deleted = true"
                        + " WHERE order_id = ? and deleted = false",
                TABLE_ORDER);

        String query2 = String.format("DELETE FROM %s"
                        + " WHERE orders_id = ?",
                TABLE_ORDER_PRODUCT);

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
            prepareStatement = connection.prepareStatement(query2);
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Order with id =" + id
                    + " has not been deleted", e);
        }
        return true;
    }

    @Override
    public List<Order> getAll() {
        String query = String.format("SELECT * FROM %s AS o"
                        + " JOIN %s AS op ON o.order_id = op.orders_id"
                        + " JOIN %s AS p ON p.product_id = op.products_id"
                        + " WHERE o.deleted = false ",
                TABLE_ORDER, TABLE_ORDER_PRODUCT, TABLE_PRODUCTS);

        List<Order> orderList = new ArrayList<>();
        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet sqlResult = preparedStatement.executeQuery();
            while (sqlResult.next()) {
                Long id = sqlResult.getLong("order_id");
                Long userId = sqlResult.getLong("user_id");
                Order order = new Order(id, userId);
                orderList.add(order);
            }
            for (Order order : orderList) {
                order.setProducts(extractProductForOrder(order.getId(), connection));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders", e);
        }
        return orderList;
    }

    private List<Product> extractProductForOrder(Long id, Connection connection)
            throws SQLException {
        String query = String.format("SELECT * FROM %s AS p"
                        + " JOIN %s AS op ON p.product_id = op.products_id"
                        + " WHERE op.orders_id = ?",
                TABLE_PRODUCTS, TABLE_ORDER_PRODUCT);

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(parseProduct(resultSet));
        }
        return products;
    }
}
