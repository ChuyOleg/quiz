package model.db.dao;

import model.entities.Answer;

import java.sql.Connection;

public interface AnswerDao {
    int insertAnswer(Answer answer, Connection connection) throws DaoException;
    int insertAnswer(Answer answer);
//    Answer getAnswer(String a);
//    boolean updateAnswer(Answer answer);
//    boolean deleteAnswer(Answer answer);
}
