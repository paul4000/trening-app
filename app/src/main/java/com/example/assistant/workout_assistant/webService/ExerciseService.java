package com.example.assistant.workout_assistant.webService;

import com.example.assistant.workout_assistant.exercises.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ExerciseService extends WebService {

    public void loadExercises(Callback<List<Exercise>> exercisesCallback){

        WorkoutPlatformAPI service = retrofit.create(WorkoutPlatformAPI.class);

        Call<List<Exercise>> exercises = service.getExercises();
        exercises.enqueue(exercisesCallback);
    }
}
