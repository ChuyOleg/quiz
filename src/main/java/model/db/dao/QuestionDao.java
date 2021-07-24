package model.db.dao;

import model.entities.Question;

public interface QuestionDao {
//    boolean insertQuestion(Question question);
    boolean isExistByText(String question_text);
//    boolean updateQuestion(Question question);
//    boolean deleteQuestion(Question question);
}
