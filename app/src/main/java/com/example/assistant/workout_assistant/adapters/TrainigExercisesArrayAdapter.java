package com.example.assistant.workout_assistant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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

        exerciseSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("WA", "" + v.getId());
            }
        });

        for (int i = 0; i < series.size(); ++i) {
            SeriesBean seriesBean = series.get(i);
//            new Inflater().inflate(R.layout.list_exercise_serie, parent, false);
            View serie = inflater.inflate(R.layout.list_exercise_serie, parent, false);


            TextView seriesNumber = (TextView) serie.findViewById(R.id.series_number);
            seriesNumber.setText("" + (i + 1));

            TextView seriesLoad = (TextView) serie.findViewById(R.id.series_load);
            seriesLoad.setText("" + seriesBean.getLoad());

            TextView seriesQuantity = (TextView) serie.findViewById(R.id.series_quantity);
            seriesQuantity.setText("" + seriesBean.getQuantity());

            TextView seriesTime = (TextView) serie.findViewById(R.id.series_time);
            seriesTime.setText("" + seriesBean.getTime());


            CheckBox isDone = (CheckBox) serie.findViewById(R.id.is_done);
            serie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox isDone = (CheckBox) v.findViewById(R.id.is_done);
                    isDone.toggle();

                    Log.e("WA", "" + isDone.isChecked());

                }
            });
//            serie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    Log.e("WA", "Pressed");
//                    buttonView.isChecked();
//                    Log.e("WA", "" + buttonView.getId());
//                    Log.e("WA", "" + isDone.getId());
//                    Log.e("WA", isChecked ? "true" : "false");
//                    Log.e(buttonView.find);
//
//                    TextView number = (TextView)
//                }
//            });

            exerciseSeries.addView(serie);
        }


        return rowView;
    }
}
