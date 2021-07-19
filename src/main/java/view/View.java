package view;

import model.entities.Category;

import java.util.List;

public class View {

    public static final String SELECT_CATEGORY = String.format("%60s", "Оберіть категорію => ");

    public static final String WRITE_CATEGORY = String.format("%20s", "Напишіть категорію => ");

    public static final String WRITE_QUESTION = String.format("%20s", "Напишіть питання => ");

    public static final String WRITE_FIRST_ANSWER = String.format("%20s", "Напишіть першу відповідь => ");

    public static final String WRITE_SECOND_ANSWER = String.format("%20s", "Напишіть другу відповідь => ");

    public static final String WRITE_THIRD_ANSWER = String.format("%20s", "Напишіть третю відповідь =>");

    public static final String DETERMINE_NUMBER_OF_CORRECT_ANSWER = String.format("%20s", "Напишіть номер правильної відповідь (1 | 2 | 3 => ");

    public static final String MESSAGE_FOR_WRONG_TYPE = String.format("%20s", "Некоректний тип для даного поля => ");

    public static final String INCORRECT_SELECTED_CATEGORY = String.format("%10s", "Обрана некоректна категорія => ");

    public static final String PREPARATION_FOR_INSERTING_INTO_DB = String.format("%20s", "Заповніть необхідні поля, аби додати нове питання в гру.");

    public static final String REQUEST_FOR_FILL_DB = String.format("%90s", "Напишіть `fill_db`, якщо хочете добавити нове питання.");

    public void printMessage(String message) {
        System.out.print(message);
    }

    public void printMessageWithNewLine(String message) {
        System.out.println(message);
    }

    public void skipLines(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println("");
        }
    }

    public void offerThreeCategories(List<Category> categories) {
        System.out.println(SELECT_CATEGORY);
        for (Category ct : categories) {
            String category_name = ct.getCategory_name() + " (" + categories.indexOf(ct) + ")";
            System.out.print(String.format("%33s", category_name));
        }
        System.out.print(System.lineSeparator());
    }

}
