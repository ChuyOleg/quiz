package db;

import model.Category;
import model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String url = DB_connectData.getUrl();
    private String user = DB_connectData.getUser();
    private String password = DB_connectData.getPassword();

    public List<Category> getAllCategories() throws SQLException {

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
                System.out.println(category);
            }

            resultSet.close();
            stmt.close();

            return categories;

        } finally {
            connection.close();
        }

    }

    public List<Question> getAllQuestions() throws SQLException {

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

    public Question getQuestion(long question_id) throws SQLException {

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

}
