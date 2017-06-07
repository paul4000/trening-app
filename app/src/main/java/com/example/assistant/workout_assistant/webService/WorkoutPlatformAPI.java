package com.example.assistant.workout_assistant.webService;

import com.example.assistant.workout_assistant.exercises.Exercise;
import com.example.assistant.workout_assistant.exercises.Training;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WorkoutPlatformAPI {

    @GET("/api/exercises")
    Call<List<Exercise>> getExercises();

    @GET("/api/trainings")
    Call<List<Training>> getTrainings();

    @POST("/api/users/login")
    Call<String> login(@Field("username") String username,
                       @Field("password") String password);

    @POST("/api/users/register")
    Call<String> register(@Field("email") String email,
                          @Field("username") String username,
                          @Field("password") String password);
}
