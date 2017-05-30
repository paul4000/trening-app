package com.example.assistant.workout_assistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.assistant.workout_assistant.exercises.Exercise;
import com.example.assistant.workout_assistant.webService.ExerciseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ExerciseService exerciseService = new ExerciseService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    private void test(){
        exerciseService.loadExercises(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                List<Exercise> exercises = response.body();
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText("Pobrano ćwiczenia: " + exercises.size() + "sztuk");
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(R.string.downloading_error);
            }
        });
    }
}
