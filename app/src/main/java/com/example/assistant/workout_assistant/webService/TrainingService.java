package com.example.assistant.workout_assistant.webService;

import com.example.assistant.workout_assistant.exercises.Exercise;
import com.example.assistant.workout_assistant.exercises.Training;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Paulina on 31.05.2017.
 */

public class TrainingService extends WebService {

    public void loadTrainings(Callback<List<Training>> trainingsCallback){

        WorkoutPlatformAPI service = retrofit.create(WorkoutPlatformAPI.class);

        Call<List<Training>> trainings = service.getTrainings();
        trainings.enqueue(trainingsCallback);
    }
}
