package model;

import model.db.Database;
import model.entities.Answer;
import model.entities.Category;
import model.entities.Question;
import model.exceptions.IncorrectSelectedCategoryException;
import model.exceptions.NonExistentCategoryException;

import java.sql.SQLException;
import java.util.List;

public class Model {

    public List<Category> getThreeRandomCategories() throws SQLException {
        List<Category> three_random_categories = Database.getThreeRandomCategories();
        return three_random_categories;
    }

    public Category getCategoryByUsersPick(List<Category> categories, String action) throws IncorrectSelectedCategoryException {
        for (Category ct : categories) {
            if (action.equalsIgnoreCase(ct.getCategory_name()) || action.equals(String.valueOf(categories.indexOf(ct)))) {
                return ct;
            }
        }
        throw new IncorrectSelectedCategoryException();
    }

    public boolean addQuetion(Category category, Question question, Answer answer) throws SQLException {
        return Database.addQuestion(category, question, answer);
    }

}
