package model.db;

import model.entities.Answer;
import model.entities.Category;
import model.entities.Question;
import model.exceptions.NonExistentCategoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static String url = DB_connectData.getUrl();
    private static String user = DB_connectData.getUser();
    private static String password = DB_connectData.getPassword();

    public static List<Category> getAllCategories() throws SQLException {

        final Connection connection = DriverManager.getConnection(url, user, password);

        connection.setAutoCommit(false);

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM categories")) {

            final ResultSet resultSet =  stmt.executeQuery();

            List<Category> categories = new ArrayList<>();

            while (resultSet.next()) {
                long category_id = resultSet.getLong("category_id");
                String category_name = resultSet.getString("category_name");
                Category category = new Category(category_id, category_name);
                categories.add(category);
            }

            resultSet.close();
            stmt.close();

            return categories;

        } finally {
            connection.close();
        }

    }

    public static List<Question> getAllQuestions() throws SQLException {

        final Connection connection = DriverManager.getConnection(url, user, password);

        connection.setAutoCommit(false);

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM questions")) {

            final ResultSet resultSet =  stmt.executeQuery();

            List<Question> questions = new ArrayList<>();

            while (resultSet.next()) {
                long question_id = resultSet.getLong("question_id");
                long category_id = resultSet.getLong("category_id");
                String question_text = resultSet.getString("question_text");

                Question question = new Question(question_id, category_id, question_text);
                questions.add(question);
                System.out.println(question);
            }

            resultSet.close();
            stmt.close();

            return questions;

        } finally {
            connection.close();
        }

    }

    public static Question getQuestion(long question_id) throws SQLException {

        final Connection connection = DriverManager.getConnection(url, user, password);

        connection.setAutoCommit(false);

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM questions where question_id = ?")) {

            stmt.setLong(1, question_id);

            final ResultSet resultSet =  stmt.executeQuery();

            if (resultSet.next()) {
                long category_id = resultSet.getLong("category_id");
                String question_text = resultSet.getString("question_text");

                Question question = new Question(question_id, category_id, question_text);

                System.out.println(question);

                resultSet.close();
                stmt.close();

                return question;
            } else {
                resultSet.close();
                stmt.close();

                System.out.println("Haven't foound the question with question id = " + question_id);
                return null;
            }

        } finally {
            connection.close();
        }

    }

    public static List<Category> getThreeRandomCategories() throws SQLException {

        final Connection connection = DriverManager.getConnection(url, user, password);

        connection.setAutoCommit(false);

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM getThreeRandomCategories()")) {

            final ResultSet resultSet =  stmt.executeQuery();

            List<Category> categories = new ArrayList<>();

            while (resultSet.next()) {
                long category_id = resultSet.getLong("category_id");
                String category_name = resultSet.getString("category_name");

                Category category = new Category(category_id, category_name);

                categories.add(category);

            }

            resultSet.close();

            return categories;

        } finally {
            connection.close();
        }

    }

    public static boolean addQuestion(Category category, Question question, Answer answer) throws SQLException {

        final Connection connection = DriverManager.getConnection(url, user, password);

        connection.setAutoCommit(false);

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM categories where LOWER(category_name) like LOWER(?)")) {

            stmt.setString(1, category.getCategory_name());
            final ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                category.setCategory_id(resultSet.getLong("category_id"));
                question.setCategory_id(category.getCategory_id());

                String query = "INSERT INTO questions(category_id, question_text) values (?, ?)";
                PreparedStatement question_stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                question_stmt.setLong(1, question.getCategory_id());
                question_stmt.setString(2, question.getQuestion_text());

                question_stmt.executeUpdate();
                ResultSet generatedKeys = question_stmt.getGeneratedKeys();
                if (generatedKeys.next()) {

                }

                // get question_id
                // insert into answers
                // close all stmt and resultSets
                // commit into db
                // make method in controller

                return true;

            }

            return false;

        } finally {
            System.out.println("Before");
            connection.close();
            System.out.println("After");
        }

    }

}
