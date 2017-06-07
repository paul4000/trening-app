package com.example.assistant.workout_assistant.authorization;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.assistant.workout_assistant.activities.LoginActivity;
import com.example.assistant.workout_assistant.activities.MainActivity;
import com.example.assistant.workout_assistant.exercises.Token;

import java.util.Date;

import retrofit2.Response;

public class Authorization {

    public boolean checkIfLogged(String token) {
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
            Toast.makeText(from.getApplicationContext(), "Błąd NotSUccessful", Toast.LENGTH_LONG).show();
            return;
        }
        String token = response.body().getToken();

        if (checkIfLogged(token)) {
            signIn(from, sharedPreferences, token);
        } else {
            Toast.makeText(from.getApplicationContext(), "Błąd logowania", Toast.LENGTH_LONG).show();
        }
    }

    public void signIn(Activity from, SharedPreferences sharedPreferences, String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("JWT_TOKEN", token);
        editor.putBoolean("LOGGED", true);
        editor.commit();

        Intent intent = new Intent(from, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        from.startActivity(intent);
    }

    public void signOut(Activity from, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("JWT_TOKEN");
        editor.putBoolean("LOGGED", false);
        editor.commit();

        from.finishAffinity();
        Intent intent = new Intent(from, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        from.startActivity(intent);

    }

    public void askLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        activity.startActivity(intent);
        activity.finish();
    }
}
