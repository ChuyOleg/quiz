package model.entities;

public class Answer {

    private int answer_id;
    private int question_id;
    private String answer_1;
    private String answer_2;
    private String answer_3;
    private int correct_answer_num;

    public Answer() {
    }

    public Answer(String answer_1, String answer_2, String answer_3, int correct_answer_num) {
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.correct_answer_num = correct_answer_num;
    }

    public Answer(int question_id, String answer_1, String answer_2, String answer_3, int correct_answer_num) {
        this.question_id = question_id;
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.correct_answer_num = correct_answer_num;
    }

    public Answer(int answer_id, int question_id, String answer_1, String answer_2, String answer_3, int correct_answer_num) {
        this.answer_id = answer_id;
        this.question_id = question_id;
        this.answer_1 = answer_1;
        this.answer_2 = answer_2;
        this.answer_3 = answer_3;
        this.correct_answer_num = correct_answer_num;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public void setAnswer_1(String answer_1) {
        this.answer_1 = answer_1;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public void setAnswer_2(String answer_2) {
        this.answer_2 = answer_2;
    }

    public String getAnswer_3() {
        return answer_3;
    }

    public void setAnswer_3(String answer_3) {
        this.answer_3 = answer_3;
    }

    public int getCorrect_answer_num() {
        return correct_answer_num;
    }

    public void setCorrect_answer_num(int correct_answer_num) {
        if (correct_answer_num >= 1 && correct_answer_num <= 3) {
            this.correct_answer_num = correct_answer_num;
        }
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer_id=" + answer_id +
                ", question_id=" + question_id +
                ", answer_1='" + answer_1 + '\'' +
                ", answer_2='" + answer_2 + '\'' +
                ", answer_3='" + answer_3 + '\'' +
                ", correct_answer_num=" + correct_answer_num +
                '}';
    }
}
