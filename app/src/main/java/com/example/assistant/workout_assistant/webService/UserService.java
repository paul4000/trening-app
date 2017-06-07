package com.example.assistant.workout_assistant.webService;

import com.example.assistant.workout_assistant.bo.Token;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by grybos on 07.06.17.
 */

public class UserService extends WebService {

    public void login(String username, String password, Callback<Token> loginCallback) {
        WorkoutPlatformAPI service = retrofit.create(WorkoutPlatformAPI.class);

        Call<Token> login = service.login(username, password);
        login.enqueue(loginCallback);
    }

    public void register(String email, String username, String password, Callback<Token> registerCallback) {
        WorkoutPlatformAPI service = retrofit.create(WorkoutPlatformAPI.class);

        Call<Token> register = service.register(email, username, password);
        register.enqueue(registerCallback);
    }
}
