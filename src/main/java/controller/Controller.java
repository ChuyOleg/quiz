package controller;

import controller.intermediary.Intermediary;
import controller.validator.Validator_Controller;
import model.Model;
import model.db.dao.DaoException;
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

            view.printMessageWithNewLine(View.INITIAL_CHOICE);
            int action_num;
            while (true) {
                try {
                    action_num = InputUtility.inputIntValueWithScanner(View.INITIAL_TEXT, View.MESSAGE_FOR_WRONG_TYPE);
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

    private Category usersPickCategory() throws DaoException {

        List<Category> three_random_categories = model.getThreeRandomCategories();

        Category selected_category = null;
        while(selected_category == null) {
            view.offerThreeCategories(three_random_categories);
            String action = InputUtility.inputStringValueWithScanner(String.format("%65s", "Категорія => "));
            try {
                selected_category = model.getCategoryByUsersPick(three_random_categories, action);
            } catch (Exception e) {
                view.printMessageWithNewLine(View.INCORRECT_SELECTED_CATEGORY);
            }
        }
        return selected_category;
    }

    private void insertQuestionIntoDB() {
        view.printMessageWithNewLine(View.PREPARATION_FOR_INSERTING_INTO_DB);

        Category category;

        try {
            category = Intermediary.getCategoryFromUser(view::printMessage);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
            return;
        }
        Question question = Intermediary.getQuestionFromUser(category);
        Answer answer = Intermediary.getAnswerFromUser();

        try {
            model.addQuestion(question, answer);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void startGame() {

        // create new class `Game` !!!

        int number_of_rounds = Intermediary.getNumberOfRoundsFromUsers(view::printMessageWithNewLine);

        for (int round_num = 0; round_num < number_of_rounds; round_num++) {
            Category selected_category;
            while (true) {
                try {
                    usersPickCategory();
                    break;
                } catch (DaoException e) {
                    view.printMessageWithNewLine(e.getMessage());
                }
            }

            for (int question_num = 0; question_num < 5; question_num++) {
                // delete this  text
                System.out.println("question_num = " + question_num);
                String answer = InputUtility.inputStringValueWithScanner("Write something");
            }

        }

    }

    private void takeDecision(int action_num) throws SQLException {
        switch (action_num) {
            case 1 -> startGame();
            case 2 -> insertQuestionIntoDB();
            case 3 -> closeProgram();
        }

    }

    private void closeProgram() {
        view.printMessage(View.END_DATA);
        System.exit(0);
    }

}
