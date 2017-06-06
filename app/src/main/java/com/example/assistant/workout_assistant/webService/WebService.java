package com.example.assistant.workout_assistant.webService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class WebService {
    final String API_URL = "https://workout-assistant.herokuapp.com/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
