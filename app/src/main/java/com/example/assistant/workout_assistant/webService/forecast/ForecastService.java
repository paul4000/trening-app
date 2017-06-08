package com.example.assistant.workout_assistant.webService.forecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastService {

    private static final String API_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "47417ba2275d609220e3654d8ec5aa24";

    public void loadForecastData(Callback<Forecast> forecastCallback, float lat, float lon){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ForecastAPI service = retrofit.create(ForecastAPI.class);

        Call<Forecast> forecast = service.getForecast(lat, lon, API_KEY);
        forecast.enqueue(forecastCallback);

    }


}
