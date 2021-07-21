package model.validator;

import model.exceptions.CorrectAnswerNumberOutOfBoundaryException;
import model.exceptions.ValueIsNullException;

public class Validator_Model {

    public static void checkForCorrectAnswerNumber(int answerNumber) throws CorrectAnswerNumberOutOfBoundaryException {
        if (answerNumber < 1 || answerNumber > 3) {
            throw new CorrectAnswerNumberOutOfBoundaryException("CorrectAnswerNum isn`t right");
        }
    }

    public static <T> void checkForNotNullValue(T value) throws ValueIsNullException {
        if (value == null) {
            throw new ValueIsNullException("Value is Null");
        }
    }

}
