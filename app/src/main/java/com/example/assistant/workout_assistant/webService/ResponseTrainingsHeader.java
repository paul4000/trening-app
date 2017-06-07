package com.example.assistant.workout_assistant.webService;

import com.example.assistant.workout_assistant.bo.Training;

import java.io.Serializable;

/**
 * Created by Paulina on 07.06.2017.
 */

public class ResponseTrainingsHeader implements Serializable {

    /**
     * _id : 5910d8e4d87b343d10aa4090
     * author : {"_id":"593735873e197c0011737cc1","username":"Kamil"}
     * name : Jak zdobyÄ robote w Akamai
     * updated : 2017-05-08T20:45:24.618Z
     */

    private String _id;
    private Training.Author author;
    private String name;
    private String updated;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Training.Author getAuthor() {
        return author;
    }

    public void setAuthor(Training.Author author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

}
