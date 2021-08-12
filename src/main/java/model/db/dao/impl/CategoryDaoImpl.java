package model.db.dao.impl;

import model.db.Database;
import model.db.dao.CategoryDao;
import model.db.dao.DaoException;
import model.entities.Category;

import java.sql.*;

public class CategoryDaoImpl implements CategoryDao {

    private boolean isExist(String category_name) throws DaoException {
        Connection connection = Database.getConnection();

        try {
            return getCategoryByName(category_name, connection).getCategory_id() != -1;
        } finally {
            Database.closeConnection(connection);
        }
    }

    @Override
    public boolean insertCategory(final Category category, final Connection connection) throws DaoException {
        if (isExist(category.getCategory_name())) return false;

        try (PreparedStatement stmt = connection.prepareStatement(Query.CREATE.value)) {
            stmt.setString(1, category.getCategory_name());
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(connection);
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Category getCategoryByName(final String category_name, final Connection connection) throws DaoException {
        final Category result = new Category();

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
            Database.closeConnection(connection);
            throw new DaoException(e.getMessage());
        }

        return result;
    }

    @Override
    public boolean updateCategory(final Category category, final Connection connection) throws DaoException {
        try (PreparedStatement stmt = connection.prepareStatement(Query.UPDATE.value)) {
            stmt.setString(1, category.getCategory_name());
            stmt.setInt(2, category.getCategory_id());
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(connection);
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public boolean deleteCategory(final Category category, final Connection connection) throws DaoException {
        if (!isExist(category.getCategory_name())) return false;

        try (PreparedStatement stmt = connection.prepareStatement(Query.DELETE.value)) {
            stmt.setString(1, category.getCategory_name());
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(connection);
            throw new DaoException(e.getMessage());
        }
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
