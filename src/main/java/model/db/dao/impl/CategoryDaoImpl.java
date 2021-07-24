package model.db.dao.impl;

import model.db.Database;
import model.db.dao.CategoryDao;
import model.entities.Category;

import java.sql.*;

public class CategoryDaoImpl implements CategoryDao {

    private boolean isExist(String category_name) {
        return getCategoryByName(category_name).getCategory_id() != -1;
    }

    @Override
    public boolean insertCategory(Category category) {
        if (isExist(category.getCategory_name())) return false;
        boolean result = false;

        Connection connection = Database.getConnection();
        if (connection == null) return false;

        try (PreparedStatement stmt = connection.prepareStatement(Query.CREATE.value)) {
            stmt.setString(1, category.getCategory_name());
            result = stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Category getCategoryByName(final String category_name) {
        final Category result = new Category();

        Connection connection = Database.getConnection();
        if (connection == null) {
            result.setCategory_id(-1);
            return result;
        }

        try (PreparedStatement stmt = connection.prepareStatement(Query.READ.value)) {

            stmt.setString(1, category_name);

            final ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result.setCategory_name(category_name);
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
    public boolean updateCategory(Category category) {
        boolean result = false;

        Connection connection = Database.getConnection();
        if (connection == null) return false;

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
    public boolean deleteCategory(Category category) {
        if (!isExist(category.getCategory_name())) return false;
        boolean result = false;

        Connection connection = Database.getConnection();
        if (connection == null) return false;

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
