package com.example.assistant.workout_assistant.bo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by grybos on 08.06.17.
 */

public class ActualTraining implements Serializable {

    private Training training;
    private List<List<Boolean>> checkboxes;

    ActualTraining(Training training, List<List<Boolean>> checkboxes) {
        this.training = training;
        this.checkboxes = checkboxes;
    }





}
