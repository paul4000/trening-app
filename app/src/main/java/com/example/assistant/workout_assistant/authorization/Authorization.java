package com.example.assistant.workout_assistant.authorization;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.assistant.workout_assistant.activities.LoginActivity;
import com.example.assistant.workout_assistant.activities.MainActivity;
import com.example.assistant.workout_assistant.bo.Token;
import com.example.assistant.workout_assistant.bo.User;

import java.util.Date;

import retrofit2.Response;

public class Authorization {

    private SharedPreferences sharedPreferences;

    public Authorization(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    public String getToken() {
        return sharedPreferences.getString("JWT_TOKEN", null);
    }

    public boolean isLogged() {
        return sharedPreferences.getBoolean("LOGGED", false);
    }

    public boolean isTokenActual() {
        return validateToken(getToken());
    }

    public User getUser() {
        return convertToUser(getToken());
    }

    public boolean validateToken(String token) {
        if (token == null) {
            return false;
        }

        JWT jwt = new JWT(token);
        Date expiresAt = jwt.getExpiresAt();
        Date date = new Date();

        return date.before(expiresAt);
    }


    public void signInAttempt(Activity from, SharedPreferences sharedPreferences, Response<Token> response) {
        if (!response.isSuccessful()) {
            Toast.makeText(from.getApplicationContext(), "NotSuccessful", Toast.LENGTH_LONG).show();
            return;
        }

        String token = response.body().getToken();
        if (validateToken(token)) {
            signIn(from, token);
        } else {
            Toast.makeText(from.getApplicationContext(), "Błąd logowania", Toast.LENGTH_LONG).show();
        }
    }

    public void signIn(Activity activity, String token) {
//        this.user = convertToUser(token);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("JWT_TOKEN", token);
        editor.putBoolean("LOGGED", true);
        editor.commit();

        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }


    public User convertToUser(String token) {
        if (token == null) {
            return null;
        }

        JWT jwt = new JWT(token);
        Claim username = jwt.getClaim("username");
        Claim email = jwt.getClaim("email");
        Claim privileges = jwt.getClaim("privileges");
        Claim id = jwt.getClaim("_id");

        return new User(id.asString(), username.asString(), email.asString(), privileges.asString());
    }

    public void signOut(Activity activity) {
//        user = null;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("JWT_TOKEN");
        editor.remove("LOGGED");
        editor.commit();

        activity.finishAffinity();
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);

    }

    public void askLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);

        activity.startActivity(intent);
        activity.finish();
    }
}
