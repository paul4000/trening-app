package com.example.assistant.workout_assistant.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assistant.workout_assistant.R;
import com.example.assistant.workout_assistant.authorization.Authorization;
import com.example.assistant.workout_assistant.bo.User;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Authorization authorization;

    User user;


    TextView id;
    TextView username;
    TextView email;
    TextView privileges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        authorization = new Authorization(sharedPreferences);

        if (!authorization.isLogged()) {
            authorization.askLogin(this);
        }


        id = (TextView) findViewById(R.id.id);
        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        privileges = (TextView) findViewById(R.id.privileges);

        user = authorization.getUser();

        if (user == null) {
            Toast.makeText(this, "Brak usera", Toast.LENGTH_LONG).show();
            authorization.askLogin(this);
            return;
        }

        id.setText(user.getId());
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        privileges.setText(user.getPrivileges());
    }
}
