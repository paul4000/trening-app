package com.example.assistant.workout_assistant.fragments.panels;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.assistant.workout_assistant.activities.PlanTrainingActivity;
import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.database.tables.PlannedTrainingsDAO;
import com.example.assistant.workout_assistant.database.tables.UserTrainingsDAO;
import com.example.assistant.workout_assistant.bo.Training;
import com.example.assistant.workout_assistant.notifications.NotificationsConfigurator;

import java.util.List;

public class EditPanelFragment extends Fragment {

    private Training training;
    UserTrainingsDAO userTrainingsDAO;
    DialogInterface.OnClickListener deleteDialog;
    Context context;
    String userId;

    NotificationsConfigurator notificationsConfigurator;
    PlannedTrainingsDAO plannedTrainingsDAO;




    public EditPanelFragment() {

    }

    public static EditPanelFragment newInstance(Training training, String userId) {
        EditPanelFragment fragment = new EditPanelFragment();
        Bundle args = new Bundle();
        args.putSerializable("TRAINING", training);
        args.putString("USER", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        training = (Training) getArguments().getSerializable("TRAINING");
        userId = getArguments().getString("USER");
        userTrainingsDAO = new UserTrainingsDAO(getActivity());
        plannedTrainingsDAO = new PlannedTrainingsDAO(getActivity());
        deleteDialog = createDeletionDialog();
        context = getActivity();
        notificationsConfigurator = new NotificationsConfigurator(context);

    }

    private DialogInterface.OnClickListener createDeletionDialog() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:

                        List<Integer> notificationIds = plannedTrainingsDAO.getAllNotificationsForUserTraining(userId, training.get_id());
                        boolean result = userTrainingsDAO.deleteTraining(userId, training.get_id());

                        if(result){
                            for(int notificationId: notificationIds){
                                notificationsConfigurator.cancelNotification(context, notificationId);
                            }
                        }

                        String info;
                        if(result) info = getString(R.string.success_deleting);
                        else info = getString(R.string.error_deleting);
                        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
                        getActivity().finish();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edit_panel, container, false);

        final Button deleteButton = (Button) view.findViewById(R.id.deleteTrainingButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.yes, deleteDialog)
                        .setNegativeButton(R.string.no, deleteDialog)
                        .show();
            }
        });

        final Button planButton = (Button) view.findViewById(R.id.planningButton);

        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlanTrainingActivity.class);
                intent.putExtra("TRAINING", training);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        userTrainingsDAO.close();
        plannedTrainingsDAO.close();
        super.onDestroy();
    }
}
