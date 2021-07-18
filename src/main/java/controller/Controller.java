package controller;

import model.Model;
import model.db.Database;
import model.entities.Category;
import model.entities.Question;
import service.InputUtility;

import java.sql.SQLException;
import java.util.List;

public class Controller {

    public void runProgram() throws SQLException {
        while(true) {
            String action = InputUtility.inputStringValueWithScanner("Enter something what you want => ");
//            Database db = new Database();
//            List<Category> categories = db.getAllCategories();
//            List<Question> questions = db.getAllQuestions();
//            Question randomQuestion = db.getQuestion(2);
            Model model = new Model();
            model.getThreeRandomCategories();
        }
    }

}
