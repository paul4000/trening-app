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

public class RegisterActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Authorization authorization;

    EditText emailET;
    EditText usernameET;
    EditText passwordET;

    Button login;
    Button register;

    private UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authorization = new Authorization();
        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);

        String token = sharedPreferences.getString("JWT_TOKEN", null);
        if (authorization.checkIfLogged(token)) {
            authorization.signIn(RegisterActivity.this, sharedPreferences, token);
        }

        emailET = (EditText) findViewById(R.id.email);
        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(v -> registerAttempt(emailET.getText().toString(), usernameET.getText().toString(), passwordET.getText().toString()));
        login.setOnClickListener(v -> login());
    }


    public void login() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void registerAttempt(String email, String username, String password) {
        userService.register(email, username, password, new Callback<Token>() {
            @Override
            public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                authorization.signInAttempt(RegisterActivity.this, sharedPreferences, response);

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
