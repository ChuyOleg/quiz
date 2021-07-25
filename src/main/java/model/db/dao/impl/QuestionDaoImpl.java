package model.db.dao.impl;

import model.db.Database;
import model.db.dao.QuestionDao;

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

    enum Query {
        CREATE("INSERT INTO questions(category_id, question_text) VALUES((?), (?)) RETURNING question_id"),
        READ("SELECT * FROM questions where question_text like (?)"),
        UPDATE(""),
        DELETE("");

        public final String value;

        Query(String value) { this.value = value; }

    }

}
