package model.db.dao.impl;

import model.db.Database;
import model.db.dao.DaoException;
import model.db.dao.QuestionDao;
import model.entities.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionDaoImpl implements QuestionDao {

    @Override
    public boolean isExistByText(String question_text) {
        boolean result = false;

        Connection connection = Database.getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement stmt = connection.prepareStatement(Query.READ.value)) {

            stmt.setString(1, question_text);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Cannot close connection " + e);
            }
        }
        return result;
    }

    @Override
    public int insertQuestion(Question question) throws DaoException {

        if (isExistByText(question.getQuestion_text())) return -1;

        int result_id = -1;

        Connection connection = Database.getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(Query.CREATE.value)) {

            stmt.setInt(1, question.getCategory_id());
            stmt.setString(2, question.getQuestion_text());

            int affected_rows = stmt.executeUpdate();

            if (affected_rows == 0) {
                throw new SQLException("Додавання нового запитання провалилося!");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result_id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating new question failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Помилка під час створення нового запитання.");
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        } finally {
            Database.closeConnection(connection);
        }

        return result_id;

    }

    enum Query {
        CREATE("INSERT INTO questions(category_id, question_text) VALUES((?), (?)) RETURNING question_id"),
        READ("SELECT * FROM questions where question_text like (?)"),
        UPDATE(""),
        DELETE("");

        public final String value;

        Query(String value) { this.value = value; }

    }

}
