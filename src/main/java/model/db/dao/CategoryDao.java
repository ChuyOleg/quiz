package model.db.dao;

import model.entities.Category;

public interface CategoryDao {
    boolean insertCategory(Category category);
    Category getCategoryByName(String category_name);
    boolean updateCategory(Category category);
    boolean deleteCategory(Category category);
}
