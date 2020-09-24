package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.exeptions.DataProcessingException;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.util.DbConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    private static final String TABLE_SHOPPING_CART = "shopping_carts";
    private static final String TABLE_SHOPPING_CART_PRODUCT = "shopping_carts_products";
    private static final Object TABLE_PRODUCTS = "products";

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String query = String.format("SELECT * FROM %s"
                        + " JOIN %s ON shopping_carts.shopping_cart_id"
                        + " = shopping_carts_products.shopping_carts_id"
                        + " JOIN %s ON products.product_id"
                        + " = shopping_carts_products.products_id"
                        + " where shopping_carts.user_id = ? ",
                TABLE_SHOPPING_CART, TABLE_SHOPPING_CART_PRODUCT, TABLE_PRODUCTS);

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet sqlResult = preparedStatement.executeQuery();
            if (sqlResult.next()) {
                ShoppingCart cart = parseResult(sqlResult);
                return Optional.of(cart);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get cart by userId = " + userId, e);
        }
        return Optional.empty();
    }

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        String query = String.format("INSERT INTO %s (user_id)"
                + " VALUES (?)", TABLE_SHOPPING_CART);

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, cart.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultKey = preparedStatement.getGeneratedKeys();
            if (resultKey.next()) {
                cart.setId(resultKey.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("ShoppingCart for user with id " + cart.getUserId()
                    + " has not been added", e);
        }
        return cart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = String.format("SELECT * FROM %s"
                        + " JOIN %s ON shopping_carts.shopping_cart_id"
                        + " = shopping_carts_products.shopping_carts_id"
                        + " JOIN %s ON products.product_id"
                        + " = shopping_carts_products.products_id"
                        + " where shopping_carts.shopping_cart_id = ? ",
                TABLE_SHOPPING_CART, TABLE_SHOPPING_CART_PRODUCT, TABLE_PRODUCTS);

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet sqlResult = preparedStatement.executeQuery();
            if (sqlResult.next()) {
                ShoppingCart cart = parseResult(sqlResult);
                return Optional.of(cart);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart by id = " + id, e);
        }
        return Optional.empty();
    }

    private ShoppingCart parseResult(ResultSet sqlResult) throws SQLException {
        Long id = sqlResult.getLong("shopping_cart_id");
        Long userId = sqlResult.getLong("user_id");
        List<Product> productList = new ArrayList<>();
        do {
            productList.add(parseProduct(sqlResult));
        } while (sqlResult.next());
        return new ShoppingCart(id, userId, productList);
    }

    private Product parseProduct(ResultSet sqlResult) throws SQLException {
        Long productId = sqlResult.getLong("product_id");
        String name = sqlResult.getString("name");
        Double price = sqlResult.getDouble("price");
        return new Product(productId, name, price);
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        String query = String.format("DELETE FROM %s WHERE shopping_carts_id = ?",
                TABLE_SHOPPING_CART_PRODUCT);

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, cart.getId());
            preparedStatement.executeUpdate();
            insertProducts(cart, connection);
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update cart for user = " + cart.getUserId(), e);
        }
        return cart;
    }

    private int insertProducts(ShoppingCart cart, Connection connection) throws SQLException {
        String query = String.format("INSERT INTO %s (shopping_carts_id, products_id) "
                + "VALUES (?, ?)", TABLE_SHOPPING_CART_PRODUCT);
        int result = 0;
        for (Product product : cart.getProducts()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, cart.getId());
            preparedStatement.setLong(2, product.getId());
            result += preparedStatement.executeUpdate();
        }
        return result;
    }

    @Override
    public boolean delete(Long id) {
        String query = String.format("UPDATE %s"
                + " SET deleted = true "
                + "WHERE shopping_cart_id = ? and deleted = false", TABLE_SHOPPING_CART);

        String query2 = String.format("DELETE FROM %s WHERE shopping_carts_id = ?",
                TABLE_SHOPPING_CART_PRODUCT);

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
            prepareStatement = connection.prepareStatement(query2);
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Shopping cart with id =" + id
                    + " has not been deleted", e);
        }
        return true;
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = String.format("SELECT * FROM %s"
                        + " JOIN %s ON shopping_carts.shopping_cart_id"
                        + " = shopping_carts_products.shopping_carts_id"
                        + " JOIN %s ON products.product_id"
                        + " = shopping_carts_products.products_id"
                        + " where shopping_carts.deleted = false ",
                TABLE_SHOPPING_CART, TABLE_SHOPPING_CART_PRODUCT, TABLE_PRODUCTS);

        List<ShoppingCart> cartList = new ArrayList<>();
        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet sqlResult = preparedStatement.executeQuery();
            while (sqlResult.next()) {
                Long id = sqlResult.getLong("shopping_cart_id");
                Long userId = sqlResult.getLong("user_id");
                ShoppingCart cart = new ShoppingCart(id, userId);
                cartList.add(cart);
            }
            for (ShoppingCart cart : cartList) {
                cart.setProducts(extractProductForCart(cart.getId(), connection));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all shopping carts", e);
        }
        return cartList;
    }

    private List<Product> extractProductForCart(Long cartId, Connection connection)
            throws SQLException {
        String query = String.format("SELECT * FROM %s "
                        + " JOIN %s ON products.product_id"
                        + " = shopping_carts_products.products_id"
                        + " WHERE shopping_carts_products.shopping_carts_id = ?",
                TABLE_PRODUCTS, TABLE_SHOPPING_CART_PRODUCT);

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, cartId);
        ResultSet resultSet = statement.executeQuery();
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(parseProduct(resultSet));
        }
        return products;
    }
}
