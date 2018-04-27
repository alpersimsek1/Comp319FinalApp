package com.example.alpersimsek.myapp.Running;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpersimsek on 15.04.2018.
 */

public class Run {

    Double distance;
    String date, uid, title;
    public Run(){

    }

    public Run(String uid, String title, Double distance, String date){
        this.title = title;
        this.uid = uid;
        this.distance = distance;
        this.date = date;
    }



    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title",title);
        result.put("length", distance);
        result.put("date", date);
        result.put("uid", uid);

        return result;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
