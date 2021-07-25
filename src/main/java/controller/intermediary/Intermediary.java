package controller.intermediary;

import model.db.Database;
import model.entities.Answer;
import model.entities.Category;
import model.entities.Question;
import service.InputUtility;
import view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

public class Intermediary {

    public static Category getCategoryFromUser(Consumer<String> printer) throws SQLException {
        List<Category> categories = Database.getAllCategories();

        while (true) {
            String category_name = InputUtility.inputStringValueWithScanner(View.WRITE_CATEGORY);
            if (category_name.equalsIgnoreCase("Help")) {
                for (Category ct : categories) {
                    printer.accept("| " + ct.getCategory_name() + " |  ");
                }
                printer.accept(View.LINE_SEPARATOR);
                continue;
            }
            for (Category ct : categories) {
                if (ct.getCategory_name().equalsIgnoreCase(category_name)) {
                    return Category.builder().category_name(category_name).build();
                }
            }
            printer.accept(View.INCORRECT_SELECTED_CATEGORY);
        }
    }

    public static Question getQuestionFromUser() {
        while (true) {
            String question_text = InputUtility.inputStringValueWithScanner(View.WRITE_QUESTION);
            if (!question_text.equals("")) {
                return Question.builder().question_text(question_text).build();
            }
        }
    }

    public static Answer getAnswerFromUser() {
        String answer_1 = "";
        String answer_2 = "";
        String answer_3 = "";
        while (answer_1.equals("")) {
            answer_1 = InputUtility.inputStringValueWithScanner(View.WRITE_FIRST_ANSWER);
        }
        while (answer_2.equals("")) {
            answer_2 = InputUtility.inputStringValueWithScanner(View.WRITE_SECOND_ANSWER);
        }
        while (answer_3.equals("")) {
            answer_3 = InputUtility.inputStringValueWithScanner(View.WRITE_THIRD_ANSWER);
        }
        int correct_answer_num;

//        carry this check into intermediary.Validator
        while (true) {
            correct_answer_num = InputUtility.inputIntValueWithScanner(View.DETERMINE_NUMBER_OF_CORRECT_ANSWER, View.MESSAGE_FOR_WRONG_TYPE);
            if (correct_answer_num >= 1 && correct_answer_num <= 3) {
                break;
            }
            System.out.println("Incorrect number of your real answer! Write one more time, please.");
        }

        return Answer.builder().answer_1(answer_1).answer_2(answer_2).answer_3(answer_3).correct_answer_num(correct_answer_num).build();
    }

}
