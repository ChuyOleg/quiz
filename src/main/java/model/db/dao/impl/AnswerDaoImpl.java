package model.db.dao.impl;

import model.db.Database;
import model.db.dao.AnswerDao;
import model.db.dao.DaoException;
import model.entities.Answer;

import java.sql.*;

public class AnswerDaoImpl implements AnswerDao {

    @Override
    public int insertAnswer(Answer answer) {
//            if (isExist(category.getCategory_name())) return (-1);
        int result_id = -1;

        Connection connection = Database.getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(SQL_QUERY.CREATE.query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, answer.getQuestion_id());
            stmt.setString(2, answer.getAnswer_1());
            stmt.setString(3, answer.getAnswer_2());
            stmt.setString(4, answer.getAnswer_3());
            stmt.setInt(5, answer.getCorrect_answer_num());

            int affected_rows = stmt.executeUpdate();

            if (affected_rows == 0) {
                throw new SQLException("Додавання нової відповіді провалилося!");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result_id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating new answer failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error during inserting new answer into Database.");
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Cannot close connection to Database");
                e.printStackTrace();
            }
        }

        return result_id;
    }

    @Override
    public int insertAnswer(Answer answer, Connection connection) throws DaoException {

        int result_id = -1;

        try (PreparedStatement stmt = connection.prepareStatement(SQL_QUERY.CREATE.query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, answer.getQuestion_id());
            stmt.setString(2, answer.getAnswer_1());
            stmt.setString(3, answer.getAnswer_2());
            stmt.setString(4, answer.getAnswer_3());
            stmt.setInt(5, answer.getCorrect_answer_num());

            int affected_rows = stmt.executeUpdate();

            if (affected_rows == 0) {
                throw new SQLException("Додавання нової відповіді провалилося!");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result_id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating new answer failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Помилка під час створення нової відповіді.");
            e.printStackTrace();
            Database.closeConnection(connection);
            throw new DaoException(e.getMessage());
        }

        return result_id;

    }

    enum SQL_QUERY {
        CREATE("INSERT INTO answers (question_id, answer_1, answer_2, answer_3, correct_answer_num) VALUES((?), (?), (?), (?), (?)) RETURNING answer_id");

        final String query;

        SQL_QUERY(String query ) {
            this.query = query;
        }

    }
}
