package model;

import model.db.dao.DaoException;
import model.entities.Category;
import model.exceptions.IncorrectSelectedCategoryException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ModelTest {

    Model model = new Model();

    @Test(expected = IncorrectSelectedCategoryException.class)
    public void testGetCategoryByUsersPick() throws IncorrectSelectedCategoryException, DaoException {

        List<Category> list = model.getThreeRandomCategories();

        String existent_category = list.get(1).getCategory_name();
        String nonexistent_category = "Fake";

        Category selected_category = model.getCategoryByUsersPick(list, existent_category);

        Assert.assertNotNull(selected_category);

        // should throw an exception
        model.getCategoryByUsersPick(list, nonexistent_category);

    }

}