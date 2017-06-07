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
import com.example.assistant.workout_assistant.exercises.Token;
import com.example.assistant.workout_assistant.webService.UserService;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        authorization = new Authorization(sharedPreferences);

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
        startActivity(intent);
    }

    public void loginAttempt(String username, String password) {
        userService.login(username, password, new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                authorization.signInAttempt(LoginActivity.this, sharedPreferences, response);
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
}
