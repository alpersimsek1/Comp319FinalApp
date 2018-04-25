package com.example.alpersimsek.myapp.Budget;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpersimsek on 15.04.2018.
 */

public class Budget {
    String day, item, uid, xloc, yloc;
    Double amount;


    public Budget(){

    }

    public Budget(String uid, String day, Double amount, String item, String xloc, String yloc){
        this.uid = uid;
        this.day = day;
        this.amount = amount;
        this.item = item;
        this.xloc = xloc;
        this.yloc = yloc;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("day", day);
        result.put("item", item);
        result.put("amount", amount);
        result.put("uid", uid);
        result.put("xloc", xloc);
        result.put("yloc", yloc);

        return result;

    }


}
