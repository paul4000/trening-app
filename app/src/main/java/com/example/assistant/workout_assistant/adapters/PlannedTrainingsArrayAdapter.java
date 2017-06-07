package com.example.assistant.workout_assistant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.database.tables.PlannedTrainingsDAO;
import com.example.assistant.workout_assistant.bo.PlannedTraining;
import com.example.assistant.workout_assistant.notifications.NotificationsConfigurator;

import java.util.List;

public class PlannedTrainingsArrayAdapter extends ArrayAdapter<PlannedTraining> {

        List<PlannedTraining> trainingsList;
        Context context;
        NotificationsConfigurator notificationsConfigurator;
        PlannedTrainingsDAO plannedTrainingsDAO;

    public PlannedTrainingsArrayAdapter(Context context, List<PlannedTraining> trainingsList, PlannedTrainingsDAO plannedTrainingsDAO){
        super(context, 0, trainingsList);
        this.trainingsList = trainingsList;
        this.context = context;
        notificationsConfigurator = new NotificationsConfigurator(context);
        this.plannedTrainingsDAO = plannedTrainingsDAO;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_with_button, parent, false);

        PlannedTraining plannedTraining = getItem(position);

        TextView time = (TextView) rowView.findViewById(R.id.trainingTime);
        time.setText(plannedTraining.getDate());

        TextView name = (TextView) rowView.findViewById(R.id.plannedTrainingName);
        name.setText(plannedTraining.getTrainingName());

        ImageButton deleteButton = (ImageButton) rowView.findViewById(R.id.deletePlannedButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationsConfigurator.cancelNotification(context, plannedTraining.getNowNotificationId());
                if(plannedTraining.getBeforeNotificationId() > 0)
                    notificationsConfigurator.cancelNotification(context, plannedTraining.getBeforeNotificationId());

                    if(plannedTrainingsDAO.deletePlannedTraining(plannedTraining.getId())){
                        Toast.makeText(context, context.getString(R.string.deletion_of_planned_trainig), Toast.LENGTH_SHORT).show();
                    }

                    trainingsList.remove(position);
                    notifyDataSetChanged();
            }
        });

        return rowView;
    }
}
