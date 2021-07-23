package model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {

    private int answer_id;
    private int question_id;
    private String answer_1;
    private String answer_2;
    private String answer_3;
    private int correct_answer_num;

}
