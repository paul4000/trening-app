package com.example.assistant.workout_assistant.webService;

import com.example.assistant.workout_assistant.exercises.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WorkoutPlatformAPI {

    @GET("exercises")
    Call<List<Exercise>> getExercises();

}
