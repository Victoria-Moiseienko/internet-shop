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
    public Product create(Product item) {
        String query = "INSERT INTO internet_shop.products (product_name, product_price)"
                + " VALUES (? , ?)";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setDouble(2, item.getPrice());
            preparedStatement.executeUpdate();
            ResultSet resultKey = preparedStatement.getGeneratedKeys();
            if (resultKey.next()) {
                item.setId(resultKey.getLong(1));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Item has not been added/n" + e);
        }
        return item;
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT product_id, product_name, product_price"
                + " FROM internet_shop.products WHERE product_id = ?";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet sqlResult = preparedStatement.executeQuery();
            if (sqlResult.next()) {
                Product product = parseRow(sqlResult);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Item has not been selected/n" + e);
        }
        return Optional.empty();
    }

    @Override
    public Product update(Product item) {
        String query = "UPDATE internet_shop.products"
                + " SET product_name = ?, product_price = ? "
                + "WHERE product_id = ?";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, item.getName());
            prepareStatement.setDouble(2, item.getPrice());
            prepareStatement.setLong(3, item.getId());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Item has not been updated/n" + e);
        }
        return item;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE internet_shop.products"
                + " SET deleted = 1 "
                + "WHERE product_id = ?";

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Item has not been deleted/n" + e);
        }
        return true;
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT product_id, product_name, product_price"
                + " FROM internet_shop.products WHERE deleted = 0";
        List<Product> productList = new ArrayList<>();

        try (Connection connection = DbConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet sqlResult = preparedStatement.executeQuery();

            while (sqlResult.next()) {
                Product product = parseRow(sqlResult);
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Items has not been selected/n" + e);
        }
        return productList;
    }

    private Product parseRow(ResultSet sqlResult) throws SQLException {
        Long id = sqlResult.getLong("product_id");
        String name = sqlResult.getString("product_name");
        Double price = sqlResult.getDouble("product_price");
        return new Product(id, name, price);
    }
}
