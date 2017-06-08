package com.example.assistant.workout_assistant.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.authorization.Authorization;
import com.example.assistant.workout_assistant.bo.PlannedTraining;
import com.example.assistant.workout_assistant.bo.Token;
import com.example.assistant.workout_assistant.database.tables.PlannedTrainingsDAO;
import com.example.assistant.workout_assistant.notifications.NotificationsConfigurator;
import com.example.assistant.workout_assistant.webService.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Authorization authorization;
    EditText usernameET;
    EditText passwordET;

    Button signIn;
    Button registration;

    private UserService userService = new UserService();

    private PlannedTrainingsDAO plannedTrainingsDAO;
    private NotificationsConfigurator notificationsConfigurator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        authorization = new Authorization(sharedPreferences);
        plannedTrainingsDAO = new PlannedTrainingsDAO(this);
        notificationsConfigurator = new NotificationsConfigurator(this);

//        String token = sharedPreferences.getString("JWT_TOKEN", null);
        if (authorization.isTokenActual()) {
            authorization.signIn(LoginActivity.this, authorization.getToken());
        }

        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);

        signIn = (Button) findViewById(R.id.sign_in);
        registration = (Button) findViewById(R.id.registration);

        signIn.setOnClickListener(v -> loginAttempt(usernameET.getText().toString(), passwordET.getText().toString()));
        registration.setOnClickListener(v -> registration());

    }

    public void registration() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void loginAttempt(String username, String password) {
        userService.login(username, password, new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                authorization.signInAttempt(LoginActivity.this, sharedPreferences, response);

                if(authorization.getUser() != null){
                    String userId = authorization.getUser().getId();
                    resumeNotifications(userId);
                }

            }

            @Override
            public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
//                TextView textView = (TextView) findViewById(R.id.textView);
//                textView.setVisibility(TextView.VISIBLE);
//                textView.setText(R.string.downloading_error);

                Toast.makeText(getApplicationContext(), "Nie można zalogować", Toast.LENGTH_LONG).show();

                usernameET.setText("");
                passwordET.setText("");
            }
        });
    }

    private void resumeNotifications(String userId) {
        List<PlannedTraining> plannedTrainings = plannedTrainingsDAO.getPlannedTrainingsForUser(userId);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.US);


        for(PlannedTraining plannedTraining: plannedTrainings){
            Calendar calendar = Calendar.getInstance();
            try{
                calendar.setTime(df.parse(plannedTraining.getDate()));
            }catch(ParseException e){
                e.printStackTrace();
            }

            notificationsConfigurator
                    .setNotification(calendar, 2, plannedTraining.getNowNotificationId(), plannedTraining.getTrainingName());

            if(plannedTraining.getBeforeNotificationId() > 0){
                calendar.add(Calendar.MINUTE, -30);
                notificationsConfigurator
                        .setNotification(calendar, 1, plannedTraining.getBeforeNotificationId(), plannedTraining.getTrainingName());
            }

        }
    }

    @Override
    protected void onDestroy() {
        plannedTrainingsDAO.close();
        super.onDestroy();
    }
}
