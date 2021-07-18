package model.entities;

public class Question {

    private long question_id;
    private long category_id;
    private String question_text;

    public Question() {}

    public Question(long question_id, long category_id, String question_text) {
        this.question_id = question_id;
        this.category_id = category_id;
        this.question_text = question_text;
    }

    public long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
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
