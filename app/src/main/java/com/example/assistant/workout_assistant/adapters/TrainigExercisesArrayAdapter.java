package com.example.assistant.workout_assistant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.bo.SeriesBean;
import com.example.assistant.workout_assistant.bo.Training;

import java.util.List;

/**
 * Created by grybos on 08.06.17.
 */

public class TrainigExercisesArrayAdapter extends ArrayAdapter {

    private List<Training.ExercisesBean> trainingExercises;
    private final Context context;

    LinearLayout exerciseSeries;

    public TrainigExercisesArrayAdapter(Context context, List<Training.ExercisesBean> trainingExercises) {
        super(context, 0, trainingExercises);
        this.trainingExercises = trainingExercises;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_training_exercise, parent, false);
        TextView exerciseName = (TextView) rowView.findViewById(R.id.exercise_name);

        Training.ExercisesBean exercisesBean = trainingExercises.get(position);

        exerciseName.setText(exercisesBean.getExercise().getName());

        List<SeriesBean> series = exercisesBean.getSeries();

        exerciseSeries = (LinearLayout) rowView.findViewById(R.id.exercise_series);

        for (int i = 0; i < series.size(); ++i) {
            SeriesBean seriesBean = series.get(i);
            View serie = inflater.inflate(R.layout.list_exercise_serie, parent, false);


            TextView seriesNumber = (TextView) serie.findViewById(R.id.series_number);
            seriesNumber.setText("" + i + 1);

            TextView seriesLoad = (TextView) serie.findViewById(R.id.series_load);
            seriesLoad.setText("" + seriesBean.getLoad());

            TextView seriesQuantity = (TextView) serie.findViewById(R.id.series_quantity);
            seriesQuantity.setText("" + seriesBean.getQuantity());

            TextView seriesTime = (TextView) serie.findViewById(R.id.series_time);
            seriesTime.setText("" + seriesBean.getTime());


            exerciseSeries.addView(serie);
        }

//                exerciseSeries.setAdapter(new ExerciseSeriesArrayAdapter(getContext(), series));


        return rowView;
    }
}
