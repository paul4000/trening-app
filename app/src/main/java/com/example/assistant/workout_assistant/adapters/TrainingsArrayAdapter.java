package com.example.assistant.workout_assistant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.exercises.Training;

import java.util.List;

/**
 * Created by Paulina on 31.05.2017.
 */

public class TrainingsArrayAdapter extends ArrayAdapter {
    private List<Training> trainingsList;
    private final Context context;

    public TrainingsArrayAdapter(Context context,List<Training> trainingsList){
        super(context, 0, trainingsList);
        this.trainingsList = trainingsList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        TextView trainingName = (TextView) rowView.findViewById(R.id.textView1);

        trainingName.setText(trainingsList.get(position).getName());

        return rowView;
    }
}
