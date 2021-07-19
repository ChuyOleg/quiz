package controller;

import model.Model;
import model.db.Database;
import model.entities.Category;
import model.entities.Question;
import service.InputUtility;
import view.View;

import java.sql.SQLException;
import java.util.List;

public class Controller {

    private final View view;
    private final Model model;

    public Controller() {
        view = new View();
        model = new Model();
    }

    public void runProgram() throws SQLException {
        while(true) {
            Category selected_category = usersPickCategory();
            System.out.println(selected_category.getCategory_name());

//          get randomQuestion

//            Database db = new Database();
//            List<Category> categories = db.getAllCategories();
//            List<Question> questions = db.getAllQuestions();
//            Question randomQuestion = db.getQuestion(2);
        }
    }

    private Category usersPickCategory() throws SQLException {
        List<Category> three_random_categories = model.getThreeRandomCategories();
        Category selected_category = null;
        while(selected_category == null) {
            view.offerThreeCategories(three_random_categories);
            String action = InputUtility.inputStringValueWithScanner(String.format("%60s", "Категорія => "));
            try {
                selected_category = model.getCategoryByUsersPick(three_random_categories, action);
            } catch (Exception e) {
                view.printMessageWithNewLine(view.INCORRECT_SELECTED_CATEGORY);
            }
        }
        return selected_category;
    }

}
