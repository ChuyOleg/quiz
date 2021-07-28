package controller;

import controller.intermediary.Intermediary;
import controller.validator.Validator_Controller;
import model.Model;
import model.db.Database;
import model.entities.Answer;
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

            view.printMessageWithNewLine(view.INITIAL_CHOICE);
            int action_num;
            while (true) {
                try {
                    action_num = InputUtility.inputIntValueWithScanner(view.INITIAL_TEXT, view.MESSAGE_FOR_WRONG_TYPE);
                    Validator_Controller.checkSelectedActionNumber(action_num);
                    break;
                } catch (Exception e) {
                    view.printMessageWithNewLine(e.getMessage());
                }
            }
            view.skipLines(1);

            takeDecision(action_num);
//            view.skipLines(1);
//            Category selected_category = usersPickCategory();
//            view.printMessageWithNewLine(selected_category.getCategory_name());

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

    private void insertQuestionIntoDB() throws SQLException {
        view.printMessageWithNewLine(view.PREPARATION_FOR_INSERTING_INTO_DB);

        Category category = Intermediary.getCategoryFromUser((S) -> view.printMessage(S));
        Question question = Intermediary.getQuestionFromUser();
        Answer answer = Intermediary.getAnswerFromUser();

        Boolean adding_is_successful = model.addQuestion(category, question, answer);

    }

    private void takeDecision(int action_num) throws SQLException {
        switch (action_num) {
            case 1:
                System.out.println("Game has been started");
            case 2:
                insertQuestionIntoDB();
            case 3:
                closeProgram();
        }

    }

    private void closeProgram() {
        view.printMessage(view.END_DATA);
        System.exit(0);
    }

}
