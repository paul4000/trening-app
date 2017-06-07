package com.example.assistant.workout_assistant.adapters;

import android.content.Context;
import android.graphics.Color;
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
 * Created by Paulina on 01.06.2017.
 */

public class ExerciseBeanArrayAdapter extends ArrayAdapter {
    private List<Training.ExercisesBean> exercisesBeanList;
    private final Context context;

    public ExerciseBeanArrayAdapter(Context context, List<Training.ExercisesBean> exerciseList){
        super(context, 0, exerciseList);
        this.exercisesBeanList = exerciseList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.exercise_row, parent, false);

        TextView exerciseName = (TextView) rowView.findViewById(R.id.exercise);

        exerciseName.setText(exercisesBeanList.get(position).getExercise().getName());

        LinearLayout exerciseLayout = (LinearLayout) rowView.findViewById(R.id.exerciseLayout);

        LinearLayout seriesLayout = new LinearLayout(context);
        seriesLayout.setOrientation(LinearLayout.VERTICAL);

        for(SeriesBean series: exercisesBeanList.get(position).getSeries()){
            TextView seriesView = getViewForSeries();
            seriesView.setText(series.toString());
            seriesLayout.addView(seriesView);
        }
        exerciseLayout.addView(seriesLayout);

        return rowView;
    }

    @NonNull
    private TextView getViewForSeries() {
        TextView resultSeriesTextView = new TextView(context);
        resultSeriesTextView.setPadding(5,5,5,5);
        resultSeriesTextView.setTextColor(Color.BLACK);
        resultSeriesTextView.setTextSize(16);
        return resultSeriesTextView;

    }

}
