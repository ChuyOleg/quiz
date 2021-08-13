package model;

import model.db.Database;
import model.db.dao.DaoException;
import model.entities.Answer;
import model.entities.Category;
import model.entities.Question;
import model.exceptions.IncorrectSelectedCategoryException;

import java.util.List;

public class Model {

    public List<Category> getThreeRandomCategories() throws DaoException {
        return Database.getThreeRandomCategories();
    }

    public Category getCategoryByUsersPick(List<Category> categories, String action) throws IncorrectSelectedCategoryException {
        for (Category ct : categories) {
            if (action.equalsIgnoreCase(ct.getCategory_name()) || action.equals(String.valueOf(categories.indexOf(ct)))) {
                return ct;
            }
        }
        throw new IncorrectSelectedCategoryException();
    }

    public void addQuestion(Question question, Answer answer) throws DaoException {
        Database.addQuestion(question, answer);
    }

    public List<Question> get_n_RandomQuestionsByCategoryName(int num_of_questions, String category_name) throws DaoException {
        return Database.get_n_RandomQuestionsByCategoryName(num_of_questions, category_name);
    }

    public Answer getAnswerByQuestion(Question question) throws DaoException {
        return Database.getAnswerByQuestion(question);
    }

}
