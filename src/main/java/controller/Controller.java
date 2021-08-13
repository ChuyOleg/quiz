package controller;

import controller.intermediary.Intermediary;
import controller.validator.Validator_Controller;
import model.Model;
import model.db.dao.DaoException;
import model.entities.Answer;
import model.entities.Category;
import model.entities.Game;
import model.entities.Question;
import service.InputUtility;
import view.View;

import java.util.List;

public class Controller {

    private final View view;
    private final Model model;

    public Controller() {
        view = new View();
        model = new Model();
    }

    public void runProgram() {

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

        int number_of_rounds = Intermediary.getNumberOfSmthFromUsers(view::printMessageWithNewLine, View.CHOOSE_NUMBER_OF_ROUNDS);
        int number_of_questions = Intermediary.getNumberOfSmthFromUsers(view::printMessageWithNewLine, View.CHOOSE_NUMBER_OF_QUESTIONS);

        Game game = Game.builder().max_rounds(number_of_rounds).max_questions(number_of_questions).build();

        for (int round_num = 0; round_num < game.getMax_rounds(); round_num++) {
            Category selected_category;
            while (true) {
                try {
                    selected_category = usersPickCategory();
                    break;
                } catch (DaoException e) {
                    view.printMessageWithNewLine(e.getMessage());
                }
            }

            List<Question> questions;
            try {
                questions = model.get_n_RandomQuestionsByCategoryName(game.getMax_questions(), selected_category.getCategory_name());
            } catch (DaoException e) {
                view.printMessageWithNewLine("Сталася помилка при отриманні питань з Бази Даних.");
                continue;
            }

            for (Question question : questions) {

                Answer answer;
                try {
                    answer = model.getAnswerByQuestion(question);
                } catch (DaoException e) {
                    view.printMessageWithNewLine("Не вдалося отримати відповіді на запитання з Бази Даних");
                    continue;
                }

                System.out.println("Запитання => " + question.getQuestion_text());
                System.out.println("1) " + answer.getAnswer_1());
                System.out.println("2) " + answer.getAnswer_2());
                System.out.println("3) " + answer.getAnswer_3());
                String user_answer = InputUtility.inputStringValueWithScanner("Вкажіть відповідь ");



                game.setCurrent_question_num(game.getCurrent_question_num() + 1);

            }

            game.setCurrent_round_num(round_num);

        }

    }

    private void takeDecision(int action_num) {
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
