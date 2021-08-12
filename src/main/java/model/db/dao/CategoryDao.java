package model.db.dao;

import model.entities.Category;

import java.sql.Connection;

public interface CategoryDao {
    boolean insertCategory(Category category, Connection connection) throws DaoException;
    Category getCategoryByName(String category_name, Connection connection) throws DaoException;
    boolean updateCategory(Category category, Connection connection) throws DaoException;
}
