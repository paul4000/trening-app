package com.example.assistant.workout_assistant.bo;

/**
 * Created by grybos on 07.06.17.
 */

public class User {

    private String id;
    private String username;
    private String email;
    private String privileges;

    public User(String id, String username, String email, String privileges) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.privileges = privileges;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }
}
