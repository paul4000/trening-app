package com.example.assistant.workout_assistant.exercises;

import java.io.Serializable;
import java.util.List;

public class Exercise implements Serializable{

    /**
     * _id : 59121e5003238d5961c79585
     * description : Bieganie dĹugodystansowe
     * name : Bieganie
     * place : outdoor
     * __v : 0
     * requirements : []
     * muscles : ["nogi"]
     */

    private String _id;
    private String description;
    private String name;
    private String place;
    private int __v;
    private List<String> requirements;
    private List<String> muscles;

    public Exercise(String _id, String description, String name, String place, int __v, List<String> requirements, List<String> muscles) {
        this._id = _id;
        this.description = description;
        this.name = name;
        this.place = place;
        this.__v = __v;
        this.requirements = requirements;
        this.muscles = muscles;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public List<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<String> requirements) {
        this.requirements = requirements;
    }

    public List<String> getMuscles() {
        return muscles;
    }

    public void setMuscles(List<String> muscles) {
        this.muscles = muscles;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(name + "\n" + description + "\n"
                +"Miejsce: " + place + "\n");

        sb.append("Wymagania: \n");
        for(String req: requirements){
            sb.append(req);
        }
        sb.append("Mięśnie: ");
        for(String mus: muscles){
            sb.append(mus);
        }

        return sb.toString();
    }
}

