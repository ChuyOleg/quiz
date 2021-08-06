package model.db.dao;

import model.entities.Question;

import java.sql.Connection;

public interface QuestionDao {
    int insertQuestion(Question question, Connection connection) throws DaoException;
    boolean isExistByText(String question_text) throws DaoException;
}
