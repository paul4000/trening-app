package com.example.assistant.workout_assistant.exercises;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Training implements Serializable{

    /**
     * _id : 5910d04fea94d232b3f36eae
     * author : Kamil GryboĹ
     * name : Wszyscy zginiemy
     * __v : 0
     * exercises : [{"exercise":{"_id":"590cb72ed522013f68155945","description":"Podnoszenie ciÄ\u0099Ĺźaru na sztandze","name":"Wyciskanie sztangi","place":"indoor","__v":0,"requirements":["sprzÄ\u0099t"],"muscles":["rÄ\u0099ce","klatka piersiowa"]},"time":true,"quantity":false,"load":true,"_id":"5910d04fea94d232b3f36eaf","series":[{"time":20,"quantity":20,"_id":"5910d04fea94d232b3f36eb1"},{"time":20,"quantity":15,"_id":"5910d04fea94d232b3f36eb0"}]}]
     * updated : 2017-05-08T20:08:47.616Z
     */

    private String _id;
    private String author;
    private String name;
    private int __v;
    private String updated;
    private List<ExercisesBean> exercises;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public List<ExercisesBean> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExercisesBean> exercises) {
        this.exercises = exercises;
    }


    public static class ExercisesBean implements Serializable {
        /**
         * exercise : {"_id":"590cb72ed522013f68155945","description":"Podnoszenie ciÄ\u0099Ĺźaru na sztandze","name":"Wyciskanie sztangi","place":"indoor","__v":0,"requirements":["sprzÄ\u0099t"],"muscles":["rÄ\u0099ce","klatka piersiowa"]}
         * time : true
         * quantity : false
         * load : true
         * _id : 5910d04fea94d232b3f36eaf
         * series : [{"time":20,"quantity":20,"_id":"5910d04fea94d232b3f36eb1"},{"time":20,"quantity":15,"_id":"5910d04fea94d232b3f36eb0"}]
         */

        private Exercise exercise;
        private boolean time;
        private boolean quantity;
        private boolean load;
        private String _id;
        private List<SeriesBean> series;

        public Exercise getExercise() {
            return exercise;
        }

        public void setExercise(Exercise exercise) {
            this.exercise = exercise;
        }

        public boolean isTime() {
            return time;
        }

        public void setTime(boolean time) {
            this.time = time;
        }

        public boolean isQuantity() {
            return quantity;
        }

        public void setQuantity(boolean quantity) {
            this.quantity = quantity;
        }

        public boolean isLoad() {
            return load;
        }

        public void setLoad(boolean load) {
            this.load = load;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public List<SeriesBean> getSeries() {
            return series;
        }

        public void setSeries(List<SeriesBean> series) {
            this.series = series;
        }


    }
}
