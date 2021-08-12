package model.db;

import model.db.dao.DaoException;
import model.db.dao.impl.AnswerDaoImpl;
import model.db.dao.impl.CategoryDaoImpl;
import model.db.dao.impl.QuestionDaoImpl;
import model.entities.Answer;
import model.entities.Category;
import model.entities.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final static String url = DB_connectData.getUrl();
    private final static String user = DB_connectData.getUser();
    private final static String password = DB_connectData.getPassword();

    private final static AnswerDaoImpl answerDao = new AnswerDaoImpl();
    private final static CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    private final static QuestionDaoImpl questionDao = new QuestionDaoImpl();

    enum SQL_QUERY {
        GET_THREE_RANDOM_CATEGORIES("SELECT * FROM getThreeRandomCategories()"),
        GET_ALL_CATEGORIES("SELECT * FROM categories");

        final String value;

        SQL_QUERY(String value) {
            this.value = value;
        }

    }

    public static Connection getConnection() throws DaoException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            return connection;
        } catch(SQLException e) {
            if (connection != null) {
                closeConnection(connection);
            }
            System.out.println("Error during creating connection to Database " + e);
            throw new DaoException(e.getMessage());
        }
    }

    public static void closeConnection(Connection connection) throws DaoException {
        try {
            if (connection != null && (!connection.isClosed())) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Cannot close connection to Database");
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        }
    }

    public static void commitToDatabase(Connection connection) throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Cannot commit changes to Database");
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        }
    }

    public static List<Category> getAllCategories() throws DaoException {

        final Connection connection = getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(SQL_QUERY.GET_ALL_CATEGORIES.value)) {

            final ResultSet resultSet =  stmt.executeQuery();

            List<Category> categories = new ArrayList<>();

            while (resultSet.next()) {
                int category_id = resultSet.getInt("category_id");
                String category_name = resultSet.getString("category_name");
                Category category = new Category(category_id, category_name);
                categories.add(category);
            }

            resultSet.close();
            stmt.close();

            return categories;

        } catch (SQLException e) {
            throw new DaoException("Помилка під час отримання всіх категорій.");
        } finally {
            closeConnection(connection);
        }

    }


    public static List<Category> getThreeRandomCategories() throws DaoException {

        final Connection connection = getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(SQL_QUERY.GET_THREE_RANDOM_CATEGORIES.value)) {

            final ResultSet resultSet =  stmt.executeQuery();

            List<Category> categories = new ArrayList<>();

            while (resultSet.next()) {
                int category_id = resultSet.getInt("category_id");
                String category_name = resultSet.getString("category_name");

                Category category = new Category(category_id, category_name);

                categories.add(category);

            }

            resultSet.close();

            return categories;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DaoException("Error during getting three random categories");
        } finally {
            closeConnection(connection);
        }

    }

    public static void addQuestion(Question question, Answer answer) throws DaoException {

        final Connection connection = getConnection();

        try {
            int question_id = questionDao.insertQuestion(question, connection);

            answer.setQuestion_id(question_id);
            answerDao.insertAnswer(answer, connection);
            commitToDatabase(connection);
        } catch (DaoException e) {
            System.out.println("Помилка під час додавання нового питання юзером");
            throw new DaoException(e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

}
