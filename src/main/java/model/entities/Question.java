package model.entities;

public class Question {

    private int question_id;
    private int category_id;
    private String question_text;

    public Question() {}

    public Question(String question_text) {
        this.question_text = question_text;
    }

    public Question(int category_id, String question_text) {
        this.category_id = category_id;
        this.question_text = question_text;
    }

    public Question(int question_id, int category_id, String question_text) {
        this.question_id = question_id;
        this.category_id = category_id;
        this.question_text = question_text;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question_id=" + question_id +
                ", category_id=" + category_id +
                ", question_text='" + question_text + '\'' +
                '}';
    }
}
