package com.example.assistant.workout_assistant.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.authorization.Authorization;
import com.example.assistant.workout_assistant.database.tables.PlannedTrainingsDAO;
import com.example.assistant.workout_assistant.bo.Training;
import com.example.assistant.workout_assistant.fragments.ForecastFragment;
import com.example.assistant.workout_assistant.fragments.pickers.DatePickerFragment;
import com.example.assistant.workout_assistant.fragments.pickers.TimePickerFragment;
import com.example.assistant.workout_assistant.notifications.NotificationIdsFactory;
import com.example.assistant.workout_assistant.notifications.NotificationsConfigurator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class PlanTrainingActivity extends FragmentActivity implements TimePickerFragment.TimePickerFragmentListener, DatePickerFragment.DatePickerFragmentListener {

    private Training training;

    private TextView displayDate;
    private TextView displayTime;
    private Button saveDate;
    private Button saveTime;
    Calendar calendar;
    FragmentManager fragmentManager;
    TimePickerFragment timePickerFragment;
    DatePickerFragment datePickerFragment;


    NotificationsConfigurator notificationsConfigurator;
    PlannedTrainingsDAO plannedTrainingsDAO;


    SharedPreferences sharedPreferences;
    Authorization authorization;

    String userId;
    Button saveButton;
    Button checkWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_training);

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        authorization = new Authorization(sharedPreferences);


        if (!authorization.isLogged()) {
            authorization.askLogin(this);
        }

        userId = authorization.getUser().getId();

        Bundle bundle = getIntent().getExtras();
        training = (Training) bundle.getSerializable("TRAINING");

        displayDate = (TextView) findViewById(R.id.chosenDate);
        displayTime = (TextView) findViewById(R.id.chosenTime);

        saveDate = (Button) findViewById(R.id.changeDateButton);
        saveTime = (Button) findViewById(R.id.changeTimeButton);

        fragmentManager = getFragmentManager();
        timePickerFragment = TimePickerFragment.newInstance(this);
        datePickerFragment = DatePickerFragment.newInstance(this);

        notificationsConfigurator = new NotificationsConfigurator(this);
        plannedTrainingsDAO = new PlannedTrainingsDAO(this);

        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));

        saveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerFragment.show(fragmentManager, "timePicker");
            }
        });

        saveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment.show(fragmentManager, "datePicker");
            }
        });

        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (planTraining()) {
                    Toast.makeText(PlanTrainingActivity.this, getString(R.string.planned_training), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlanTrainingActivity.this, getString(R.string.non_planned_training), Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkWeather = (Button) findViewById(R.id.checkWeatherButton);
        checkWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .replace(R.id.placeForForecast, ForecastFragment.newInstance(calendar))
                        .commit();

                Toast.makeText(PlanTrainingActivity.this, "Sprawdzam pogode!", Toast.LENGTH_LONG).show();
            }
        });

        updateDisplayDate();
        updateDisplayTime();

//        if (training.isOutdoor()) {
//            checkWeather.setVisibility(View.VISIBLE);
//        } else {
//            checkWeather.setVisibility(View.GONE);
//        }

    }

    private boolean planTraining() {
        if (calendar.before(Calendar.getInstance())) return false;

        String date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.US).format(calendar.getTime());

        int nowNotificationId = NotificationIdsFactory.getInstance();
        notificationsConfigurator.setNotification(calendar, 2, nowNotificationId, training.getName());

        int beforeNotificationId = NotificationIdsFactory.getInstance();
        calendar.add(Calendar.MINUTE, -30);
        boolean shouldAddBeforeNotification = !calendar.before(Calendar.getInstance());

        if (shouldAddBeforeNotification) notificationsConfigurator
                .setNotification(calendar, 1, beforeNotificationId, training.getName());

        return plannedTrainingsDAO.insertPlannedTraining(userId, training.get_id(), date, training.getName(),
                shouldAddBeforeNotification ? beforeNotificationId : -1, nowNotificationId);

    }


    public void updateDisplayDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String formattedDate = dateFormatter.format(calendar.getTime());
        displayDate.setText(formattedDate);

        Calendar currentCalendar = Calendar.getInstance();

        int daysBetween = daysBetween(currentCalendar.getTime(), calendar.getTime());

        if(daysBetween > 5) {
            checkWeather.setEnabled(false);
            Toast.makeText(PlanTrainingActivity.this, R.string.days_available_weather, Toast.LENGTH_LONG).show();
        } else {
            checkWeather.setEnabled(true);
        }

    }
    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }


    public void updateDisplayTime() {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String formattedTime = timeFormatter.format(calendar.getTime());
        displayTime.setText(formattedTime);
    }

    @Override
    public void onTimeSet(Calendar c) {
        calendar.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, 0);
        updateDisplayTime();
    }

    @Override
    public void onDateSet(Calendar c) {
        calendar.set(Calendar.YEAR, c.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, c.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
        updateDisplayDate();
    }
}
