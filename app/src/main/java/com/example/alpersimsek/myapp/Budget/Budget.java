package com.example.alpersimsek.myapp.Budget;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpersimsek on 15.04.2018.
 */

public class Budget {
    String title, uid;
    Double amount, xloc, yloc;
    String day;


    public Budget(){

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public Double getXloc() {
        return xloc;
    }

    public void setXloc(Double xloc) {
        this.xloc = xloc;
    }

    public Double getYloc() {
        return yloc;
    }

    public void setYloc(Double yloc) {
        this.yloc = yloc;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Budget(String uid, String day, Double amount, String title, Double xloc, Double yloc){
        this.uid = uid;
        this.day = day;
        this.amount = amount;
        this.title = title;
        this.xloc = xloc;
        this.yloc = yloc;

    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("day", day);
        result.put("title", title);
        result.put("amount", amount);
        result.put("uid", uid);
        result.put("xloc", xloc);
        result.put("yloc", yloc);

        return result;

    }




}
