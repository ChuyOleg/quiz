package model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Game {

    private final int max_rounds;
    private int current_round_num = 0;

    private final int max_questions;
    private int current_question_num = 0;

}
