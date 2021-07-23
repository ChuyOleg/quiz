package model.db.dao;

import model.entities.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO implements DAO<Category, String> {

    private final Connection connection;

    public CategoryDAO(final Connection connection) {
        this.connection = connection;
    }

    private boolean isExist(String category_name) {
        return read(category_name).getCategory_id() != -1;
    }

    @Override
    public boolean create(Category category) {
        if (isExist(category.getCategory_name())) return false;
        boolean result = false;
        try (PreparedStatement stmt = connection.prepareStatement(Query.CREATE.value)) {
            stmt.setString(1, category.getCategory_name());
            result = stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Category read(final String category_name) {
        final Category result = new Category();
        result.setCategory_name(category_name);

        try (PreparedStatement stmt = connection.prepareStatement(Query.READ.value)) {

            stmt.setString(1, category_name);

            final ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result.setCategory_id(rs.getInt("category_id"));
            } else {
                result.setCategory_id(-1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean update(Category category) {
        boolean result = false;
        try (PreparedStatement stmt = connection.prepareStatement(Query.UPDATE.value)) {
            stmt.setString(1, category.getCategory_name());
            stmt.setInt(2, category.getCategory_id());
            result = stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Category category) {
        if (!isExist(category.getCategory_name())) return false;

        boolean result = false;
        try (PreparedStatement stmt = connection.prepareStatement(Query.DELETE.value)) {
            stmt.setString(1, category.getCategory_name());
            result = stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    enum Query {
        CREATE("INSERT INTO categories(category_name) VALUES(?) returning category_id"),
        READ("SELECT * FROM categories WHERE category_name LIKE (?)"),
        UPDATE("UPDATE categories SET category_name = (?) WHERE category_id = (?) returning category_id"),
        DELETE("DELETE FROM categories WHERE category_name LIKE (?) returning category_id");

        public final String value;

        Query(String value) {
            this.value = value;
        }

    }
}