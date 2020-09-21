package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.exeptions.DataProcessingException;
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

public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (name, price)"
                + " VALUES (? , ?)";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.executeUpdate();
            ResultSet resultKey = preparedStatement.getGeneratedKeys();
            if (resultKey.next()) {
                product.setId(resultKey.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Item has not been added", e);
        }
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT product_id, name, price"
                + " FROM products WHERE product_id = ? and deleted = false";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet sqlResult = preparedStatement.executeQuery();
            if (sqlResult.next()) {
                Product product = parseRow(sqlResult);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get product by id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products"
                + " SET name = ?, price = ?, deleted = ? "
                + "WHERE product_id = ?";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, product.getName());
            prepareStatement.setDouble(2, product.getPrice());
            prepareStatement.setBoolean(3, true);
            prepareStatement.setLong(4, product.getId());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Item has not been updated", e);
        }
        return product;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE products"
                + " SET deleted = true "
                + "WHERE product_id = ? and deleted = false";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Item has not been deleted", e);
        }
        return true;
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT product_id, name, price"
                + " FROM products WHERE deleted = false";
        List<Product> productList = new ArrayList<>();

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet sqlResult = preparedStatement.executeQuery();

            while (sqlResult.next()) {
                productList.add(parseRow(sqlResult));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Items has not been selected", e);
        }
        return productList;
    }

    private Product parseRow(ResultSet sqlResult) throws SQLException {
        Long id = sqlResult.getLong("product_id");
        String name = sqlResult.getString("name");
        Double price = sqlResult.getDouble("price");
        return new Product(id, name, price);
    }
}
