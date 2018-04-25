package com.example.alpersimsek.myapp.HowManyDays;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpersimsek on 15.04.2018.
 */

public class Event {
    String eid, title, uid;

    String date;
    public Event(){

    }

    public Event(String uid, String eid, String date, String title){
        this.uid = uid;
        this.eid = eid;
        this.date = date;
        this.title = title;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("eid", eid);
        result.put("date", date);
        result.put("title", title);
        result.put("uid", uid);

        return result;
    }

    public String daysLeft(){
        DateTime now = DateTime.now();
        DateTime toEvent = DateTime.parse(date);
        Duration dur = new Duration(toEvent,now);
        return dur.toStandardMinutes().toString();
    }

}
