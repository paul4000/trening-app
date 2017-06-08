package com.example.assistant.workout_assistant.webService.forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ForecastAPI {

    @GET("forecast?")
    Call<Forecast> getForecast(
            @Query("lat") float lat,
            @Query("lon") float lon,
            @Query("APPID") String key
    );
}
