package com.example.alpersimsek.myapp.Running;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpersimsek on 15.04.2018.
 */

public class Run {

    Double length;
    String from, to, date, uid;
    public Run(){

    }

    public Run(String uid, Double length, String from, String to, String date){
        this.uid = uid;
        this.length = length;
        this.from = from;
        this.to = to;
        this.date = date;
    }



    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("from", from);
        result.put("to", to);
        result.put("length", length);
        result.put("date", date);
        result.put("uid", uid);

        return result;
    }


}
