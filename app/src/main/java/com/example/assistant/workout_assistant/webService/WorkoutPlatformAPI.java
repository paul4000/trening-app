package com.example.assistant.workout_assistant.webService;

import com.example.assistant.workout_assistant.exercises.Exercise;
import com.example.assistant.workout_assistant.exercises.Training;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface WorkoutPlatformAPI {

    @GET("exercises")
    Call<List<Exercise>> getExercises();

    @GET("trainings")
    Call<List<ResponseTrainingsHeader>> getTrainings();

    @GET("trainings/{trainingId}")
    Call<Training> getTraining(
            @Path("trainingId") String trainingId
    );


}
