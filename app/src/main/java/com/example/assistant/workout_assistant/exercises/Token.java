package com.example.assistant.workout_assistant.exercises;

/**
 * Created by grybos on 07.06.17.
 */

public class Token {

    private String token;


    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }
}
