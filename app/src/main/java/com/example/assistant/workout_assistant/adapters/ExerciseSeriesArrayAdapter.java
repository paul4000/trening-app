//package com.example.assistant.workout_assistant.adapters;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import com.example.assistant.workout_assistant.R;
//import com.example.assistant.workout_assistant.bo.SeriesBean;
//
//import java.util.List;
//
///**
// * Created by grybos on 08.06.17.
// */
//
//public class ExerciseSeriesArrayAdapter extends ArrayAdapter {
//    private List<SeriesBean> exerciseSeries;
//    private final Context context;
//
//    public ExerciseSeriesArrayAdapter(Context context, List<SeriesBean> exerciseSeries) {
//        super(context, 0, exerciseSeries);
//        this.exerciseSeries = exerciseSeries;
//        this.context = context;
//    }
//
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View rowView = inflater.inflate(R.layout.list_exercise_serie, parent, false);
//        TextView seriesNumber = (TextView) rowView.findViewById(R.id.series_number);
//
//        seriesNumber.setText("helaoqwe");
//
////        seriesNumber.setText(exerciseSeries.get(position).ame());
////        seriesNumber.setText(position + 1);
//
//        Log.e("WA", "qqweqwe");
//        return rowView;
//    }
//}
