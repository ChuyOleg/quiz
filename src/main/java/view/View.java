package view;

import model.entities.Category;

import java.util.List;

public class View {

    private final String SELECT_CATEGORY = String.format("%60s", "Оберіть категорію");

    public static final String INCORRECT_SELECTED_CATEGORY = String.format("%10s", "Обрана некоректна категорія");

    public void printMessage(String message) {
        System.out.print(message);
    }

    public void printMessageWithNewLine(String message) {
        System.out.println(message);
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
