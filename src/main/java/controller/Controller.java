package controller;

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

        view.printMessageWithNewLine(view.REQUEST_FOR_FILL_DB);
        insertQuestionIntoDB();

        while(true) {
            view.skipLines(1);
            Category selected_category = usersPickCategory();
            view.printMessageWithNewLine(selected_category.getCategory_name());

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

    private void insertQuestionIntoDB() {
        view.printMessageWithNewLine(view.PREPARATION_FOR_INSERTING_INTO_DB);
        // create and show list with available categories for user
        String category_name = InputUtility.inputStringValueWithScanner(view.WRITE_CATEGORY);
        String question_text = InputUtility.inputStringValueWithScanner(view.WRITE_QUESTION);
        String answer_1 = InputUtility.inputStringValueWithScanner(view.WRITE_FIRST_ANSWER);
        String answer_2 = InputUtility.inputStringValueWithScanner(view.WRITE_SECOND_ANSWER);
        String answer_3 = InputUtility.inputStringValueWithScanner(view.WRITE_THIRD_ANSWER);
        int correct_answer_num = InputUtility.inputIntValueWithScanner(view.DETERMINE_NUMBER_OF_CORRECT_ANSWER, view.MESSAGE_FOR_WRONG_TYPE);
        // generate exception for wrong int value (< 1 or > 3)

        Category category = new Category(category_name);
        Question question = new Question(question_text);
        Answer answer = new Answer(answer_1, answer_2, answer_3, correct_answer_num);

        System.out.println(category);
        System.out.println(question);
        System.out.println(answer);

    }

}
