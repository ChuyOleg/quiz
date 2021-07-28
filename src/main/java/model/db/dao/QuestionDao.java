package model.db.dao;

import model.entities.Question;

import java.sql.Connection;

public interface QuestionDao {
    int insertQuestion(Question question) throws DaoException;
    int insertQuestion(Question question, Connection connection) throws DaoException;
    boolean isExistByText(String question_text);
//    boolean updateQuestion(Question question);
//    boolean deleteQuestion(Question question);
}
