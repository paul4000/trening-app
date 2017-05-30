package com.example.assistant.workout_assistant.webService;

import com.example.assistant.workout_assistant.exercises.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ExerciseService {
    private final String API_URL = "https://workout-assistant.herokuapp.com/";

    public void loadExercises(Callback<List<Exercise>> exercisesCallback){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WorkoutPlatformAPI service = retrofit.create(WorkoutPlatformAPI.class);

        Call<List<Exercise>> exercises = service.getExercises();
        exercises.enqueue(exercisesCallback);
    }
}
