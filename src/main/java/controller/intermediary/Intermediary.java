package controller.intermediary;

import model.db.Database;
import model.db.dao.DaoException;
import model.entities.Answer;
import model.entities.Category;
import model.entities.Question;
import service.InputUtility;
import view.View;

import java.util.List;
import java.util.function.Consumer;

public class Intermediary {

    public static Category getCategoryFromUser(Consumer<String> printer) throws DaoException {

        List<Category> categories;

        try {
            categories = Database.getAllCategories();
        } catch (DaoException e) {
            throw new DaoException(e.getMessage());
        }

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
                    return ct;
                }
            }
            printer.accept(View.INCORRECT_SELECTED_CATEGORY);
        }
    }

    public static Question getQuestionFromUser(Category category) {
        while (true) {
            String question_text = InputUtility.inputStringValueWithScanner(View.WRITE_QUESTION);
            if (!question_text.equals("")) {
                return Question.builder().question_text(question_text).category_id(category.getCategory_id()).build();
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

    public static int getNumberOfSmthFromUsers(Consumer<String> printer, String message_invite) {

        int number_of_rounds;
        while (true) {
            number_of_rounds = InputUtility.inputIntValueWithScanner(message_invite, View.MESSAGE_FOR_WRONG_TYPE);
            if (message_invite.equals(View.CHOOSE_NUMBER_OF_ROUNDS)) {
                if (number_of_rounds == 5 || number_of_rounds == 10 || number_of_rounds == 15) {
                    return number_of_rounds;
                }
            } else if (message_invite.equals(View.CHOOSE_NUMBER_OF_QUESTIONS)) {
                if (number_of_rounds == 3 || number_of_rounds == 5) {
                    return number_of_rounds;
                }
            }
            printer.accept(View.INCORRECT_NUMBER_OF_ROUNDS);
        }

    }

}
