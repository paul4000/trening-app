package com.example.assistant.workout_assistant.webService;

import com.example.assistant.workout_assistant.bo.Training;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Paulina on 31.05.2017.
 */

public class TrainingService extends WebService {

    WorkoutPlatformAPI service = retrofit.create(WorkoutPlatformAPI.class);

    public void loadTrainings(Callback<List<ResponseTrainingsHeader>> trainingsCallback){

        Call<List<ResponseTrainingsHeader>> trainings = service.getTrainings();
        trainings.enqueue(trainingsCallback);
    }

    public void loadTraining(Callback<Training> trainingCallback, String trainingId){
        Call<Training> training = service.getTraining(trainingId);

        training.enqueue(trainingCallback);
    }


}
