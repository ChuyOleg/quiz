package model.db.dao;

import model.entities.Question;

public interface QuestionDao {
    int insertQuestion(Question question) throws DaoException;
    boolean isExistByText(String question_text);
//    boolean updateQuestion(Question question);
//    boolean deleteQuestion(Question question);
}
