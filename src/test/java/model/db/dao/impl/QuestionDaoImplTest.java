package model.db.dao.impl;

import model.db.Database;
import model.db.dao.DaoException;
import model.entities.Question;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class QuestionDaoImplTest {

    final QuestionDaoImpl questionDao = new QuestionDaoImpl();

    @Test
    public void IsExistByText() throws DaoException {

        String existent_question = "Дата ухвалення Верховною Радою України Конституції України";
        String fake_question = "Фейк";
        boolean question_exist;

        question_exist = questionDao.isExistByText(existent_question);
        Assert.assertTrue(question_exist);

        question_exist = questionDao.isExistByText(fake_question);
        Assert.assertFalse(question_exist);
    }

    @Test
    public void InsertQuestion() throws DaoException {

        Connection connection = Database.getConnection();

        try {
            int question_id;
            Question existent_question = Question.builder()
                    .question_text("Дата ухвалення Верховною Радою України Конституції України")
                    .category_id(3).build();

            Question nonexistent_question = Question.builder()
                    .question_text("Фейк")
                    .category_id(3).build();

            question_id = questionDao.insertQuestion(existent_question, connection);
            Assert.assertEquals(question_id, -1);

            question_id = questionDao.insertQuestion(nonexistent_question, connection);
            Assert.assertNotEquals(question_id, -1);
        } finally {
            Database.closeConnection(connection);
        }
    }

    @Test
    public void getQuestionByText() throws DaoException {

        Connection connection = Database.getConnection();

        try {
            final String question_text = "Дата заснування Збройних Сил України";
            final String fake_question_text = "FAKE";

            Question question;

            question = questionDao.getQuestionByText(question_text, connection);
            Assert.assertNotEquals(question.getQuestion_id(), -1);
            Assert.assertEquals(question.getQuestion_text(), question_text);

            question = questionDao.getQuestionByText(fake_question_text, connection);
            Assert.assertEquals(question.getQuestion_id(), -1);

        } finally {
            Database.closeConnection(connection);
        }

    }
}