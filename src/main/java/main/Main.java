package main;

import db.Database;
import model.Category;
import model.Question;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        Database db = new Database();
//        List<Category> categories = db.getAllCategories();
//        List<Question> questions = db.getAllQuestions();
        Question randomQuestion = db.getQuestion(2);
    }

}
