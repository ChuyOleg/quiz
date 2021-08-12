package model.db.dao.impl;

import model.db.Database;
import model.db.dao.DaoException;
import model.entities.Category;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;


public class CategoryDaoImplTest {

    final CategoryDaoImpl categoryDao = new CategoryDaoImpl();

    @Test
    public void insertCategory() throws DaoException {

        Connection connection = Database.getConnection();

        try {
            boolean category_id;
            Category existent_category = Category.builder()
                    .category_name("Музика")
                    .build();

            Category nonexistent_category = Category.builder()
                    .category_name("Автомобілі")
                    .build();

            category_id = categoryDao.insertCategory(existent_category, connection);
            Assert.assertFalse (category_id);

            category_id = categoryDao.insertCategory(nonexistent_category, connection);
            Assert.assertTrue(category_id);
        } finally {
            Database.closeConnection(connection);
        }
    }

    @Test
    public void getCategoryByName() throws DaoException {

        Connection connection = Database.getConnection();

        try {
            String existent_category_name = "Історія";
            String nonexistent_category_name = "Автомобілі";

            Category existent_category = categoryDao.getCategoryByName(existent_category_name, connection);
            Assert.assertNotEquals(existent_category.getCategory_id(), -1);
            Assert.assertEquals(existent_category.getCategory_name(), existent_category_name);

            Category fake_category = categoryDao.getCategoryByName(nonexistent_category_name, connection);
            Assert.assertEquals(fake_category.getCategory_id(), -1);
            Assert.assertNull(fake_category.getCategory_name());
        } finally {
            Database.closeConnection(connection);
        }

    }

    @Test
    public void updateCategory() throws DaoException {

        Connection connection = Database.getConnection();
        String old_category_name = "Історія";
        String updated_category_name = "Історія 2";

        try {
            Category category = categoryDao.getCategoryByName(old_category_name, connection);
            category.setCategory_name(updated_category_name);

            boolean updating_result = categoryDao.updateCategory(category, connection);

            Category updated_category = categoryDao.getCategoryByName(updated_category_name, connection);

            Assert.assertTrue(updating_result);
            Assert.assertEquals(category.getCategory_id(), updated_category.getCategory_id());
            Assert.assertEquals(updated_category.getCategory_name(), updated_category_name);

        } finally {
            Database.closeConnection(connection);
        }

    }

}