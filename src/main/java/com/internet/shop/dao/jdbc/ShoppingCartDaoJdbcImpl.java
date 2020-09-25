package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.exeptions.DataProcessingException;
import com.internet.shop.lib.Dao;
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

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String query = "SELECT * FROM shopping_carts"
                + " LEFT JOIN shopping_carts_products ON shopping_carts.shopping_cart_id"
                + " = shopping_carts_products.shopping_carts_id"
                + " LEFT JOIN products ON products.product_id"
                + " = shopping_carts_products.products_id"
                + " where shopping_carts.user_id = ?";

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
        String query = "INSERT INTO shopping_carts (user_id)"
                + " VALUES (?)";

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
        String query = "SELECT * FROM shopping_carts"
                + " JOIN shopping_carts_products ON shopping_carts.shopping_cart_id"
                + " = shopping_carts_products.shopping_carts_id"
                + " JOIN products ON products.product_id"
                + " = shopping_carts_products.products_id"
                + " where shopping_carts.shopping_cart_id = ? ";

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
            Product product = parseProduct(sqlResult);
            if (null != product) {
                productList.add(parseProduct(sqlResult));
            }
        } while (sqlResult.next());
        return new ShoppingCart(id, userId, productList);
    }

    private Product parseProduct(ResultSet sqlResult) throws SQLException {
        Long productId = sqlResult.getLong("product_id");
        String name = sqlResult.getString("name");
        Double price = sqlResult.getDouble("price");
        if (name == null) {
            return null;
        }
        return new Product(productId, name, price);
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        String query = "DELETE FROM shopping_carts_products WHERE shopping_carts_id = ?";

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
        String query = "INSERT INTO shopping_carts_products (shopping_carts_id, products_id) "
                + "VALUES (?, ?)";

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
        String query = "UPDATE shopping_carts"
                + " SET deleted = true "
                + "WHERE shopping_cart_id = ? and deleted = false";

        String query2 = "DELETE FROM shopping_carts_products"
                + " WHERE shopping_carts_id = ?";

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
        String query = "SELECT * FROM shopping_carts"
                + " JOIN shopping_carts_products ON shopping_carts.shopping_cart_id"
                + " = shopping_carts_products.shopping_carts_id"
                + " JOIN products ON products.product_id"
                + " = shopping_carts_products.products_id"
                + " where shopping_carts.deleted = false ";

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
        String query = "SELECT * FROM shopping_carts "
                + " JOIN shopping_carts_products ON products.product_id"
                + " = shopping_carts_products.products_id"
                + " WHERE shopping_carts_products.shopping_carts_id = ?";

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
