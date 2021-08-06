package model.db.dao.impl;

import model.db.Database;
import model.db.dao.DaoException;
import model.db.dao.QuestionDao;
import model.entities.Question;

import java.sql.*;

public class QuestionDaoImpl implements QuestionDao {

    @Override
    public boolean isExistByText(String question_text) throws DaoException {
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
            throw new DaoException(e.getMessage());
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
    public int insertQuestion(Question question, Connection connection) throws DaoException {
        if (isExistByText(question.getQuestion_text())) return -1;

        int result_id;

        try (PreparedStatement stmt = connection.prepareStatement(Query.CREATE.value, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, question.getCategory_id());
            stmt.setString(2, question.getQuestion_text());

            int affected_rows = stmt.executeUpdate();

            if (affected_rows == 0) {
                throw new SQLException("Adding new question has been failed!");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result_id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Cannot get generated keys during adding new question");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Database.closeConnection(connection);
            throw new DaoException(e.getMessage());
        }

        return result_id;
    }

    enum Query {
        CREATE("INSERT INTO questions(category_id, question_text) VALUES((?), (?)) RETURNING question_id"),
        READ("SELECT * FROM questions where question_text like (?)");

        public final String value;

        Query(String value) { this.value = value; }

    }

}
