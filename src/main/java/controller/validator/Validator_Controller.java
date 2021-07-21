package controller.validator;


import controller.exceptions.SelectedActionNumberOutOfBoundaryException;
import view.View;

public class Validator_Controller {

    public static void checkSelectedActionNumber(int answerNumber) throws SelectedActionNumberOutOfBoundaryException {
        if (answerNumber < 1 || answerNumber > 3) {
            throw new SelectedActionNumberOutOfBoundaryException(View.NUMBER_IS_OUT_OF_BOUNDARY);
        }
    }

}
