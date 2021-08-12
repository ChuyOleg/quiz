package model.db.dao.impl;

import model.db.Database;
import model.db.dao.DaoException;
import model.entities.Answer;
import model.entities.Question;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class AnswerDaoImplTest {

    AnswerDaoImpl answerDao = new AnswerDaoImpl();

    @Test
    public void insertAnswer() throws DaoException {

        Connection connection = Database.getConnection();
        QuestionDaoImpl questionDao = new QuestionDaoImpl();

        try {
            Question question = questionDao.getQuestionByText("Дата заснування Збройних Сил України", connection);
            String answer_1 = "London";
            String answer_2 = "Lvov";
            String answer_3 = "Lutsk";
            int correct_answer_num = 1;

            Answer answer = Answer.builder()
                    .question_id(question.getQuestion_id())
                    .answer_1(answer_1)
                    .answer_2(answer_2)
                    .answer_3(answer_3)
                    .correct_answer_num(correct_answer_num)
                    .build();

            int inserted_answer_id = answerDao.insertAnswer(answer, connection);

            Assert.assertNotEquals(inserted_answer_id, -1);

        } finally {
            Database.closeConnection(connection);
        }

    }
}