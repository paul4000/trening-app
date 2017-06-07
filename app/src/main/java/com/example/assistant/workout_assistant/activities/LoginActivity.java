package com.example.assistant.workout_assistant.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.authorization.Authorization;
import com.example.assistant.workout_assistant.webService.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Authorization authorization;
    EditText usernameET;
    EditText passwordET;

    Button login;

    private UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authorization = new Authorization();
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        if (authorization.checkIfLogged(sharedPreferences.getString("JWT_TOKEN", null))) {
            login();
        }

        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(v -> loginAttempt(usernameET.getText().toString(), passwordET.getText().toString()));
    }

    public void loginAttempt(String username, String password) {
        userService.login(username, password, new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                String token = response.body();

                if (authorization.checkIfLogged(token)) {
                    login();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setVisibility(TextView.VISIBLE);
                textView.setText(R.string.downloading_error);

                usernameET.setText("");
                passwordET.setText("");
            }
        });
    }

    public void login() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
