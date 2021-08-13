package view;

import model.entities.Category;

import java.util.List;

public class View {

    public static final String INITIAL_TEXT = String.format("%83s", "Оберіть цифру відповідної дії => ");

    public static final String INITIAL_CHOICE = String.format("%50s%30s%20s", "Почати гру (1)", "Додати нове питання (2)", "Вихід (3)");

    public static final String NUMBER_IS_OUT_OF_BOUNDARY = "Вказане вами число некоректне. Спробуйте ще раз.";

    public static final String SELECT_CATEGORY = String.format("%70s", "Оберіть категорію.");

    public static final String WRITE_CATEGORY = String.format("%20s", "Напишіть категорію (help - показати всі категорії) => ");

    public static final String WRITE_QUESTION = String.format("%20s", "Напишіть питання => ");

    public static final String WRITE_FIRST_ANSWER = String.format("%20s", "Напишіть першу відповідь => ");

    public static final String WRITE_SECOND_ANSWER = String.format("%20s", "Напишіть другу відповідь => ");

    public static final String WRITE_THIRD_ANSWER = String.format("%20s", "Напишіть третю відповідь => ");

    public static final String DETERMINE_NUMBER_OF_CORRECT_ANSWER = String.format("%20s", "Напишіть номер правильної відповідь (1 | 2 | 3) => ");

    public static final String MESSAGE_FOR_WRONG_TYPE = String.format("%20s", "Некоректний тип для даного поля.");

    public static final String INCORRECT_SELECTED_CATEGORY = String.format("%10s", "Обрана некоректна категорія. ");

    public static final String PREPARATION_FOR_INSERTING_INTO_DB = String.format("%20s", "Заповніть необхідні поля, аби додати нове питання в гру.");

    public static final String REQUEST_FOR_FILL_DB = String.format("%90s", "Напишіть `fill_db`, якщо хочете добавити нове питання.");

    public static final String LINE_SEPARATOR = System.lineSeparator();

    public static final String END_DATA = String.format("%75s", "Ви успішно вийшли зі гри.");

    public static final String CHOOSE_NUMBER_OF_ROUNDS = "Оберіть кількість раундів (5 | 10 | 15) => ";

    public static final String CHOOSE_NUMBER_OF_QUESTIONS = "Оберіть кількість питань у раунді (3 | 5) => ";

    public static final String INCORRECT_NUMBER_OF_ROUNDS = "Обрана невірна кількість раундів.";

    public void printMessage(String message) {
        System.out.print(message);
    }

    public void printMessageWithNewLine(String message) {
        System.out.println(message);
    }

    public void skipLines(int count) {
        for (int i = 0; i < count; i++) {
            printMessage(LINE_SEPARATOR);
        }
    }

    public void offerThreeCategories(List<Category> categories) {
        System.out.println(SELECT_CATEGORY);
        for (Category ct : categories) {
            String category_name = ct.getCategory_name() + " (" + categories.indexOf(ct) + ")";
            printMessage(String.format("%33s", category_name));
        }
        printMessage(LINE_SEPARATOR);
    }

}
