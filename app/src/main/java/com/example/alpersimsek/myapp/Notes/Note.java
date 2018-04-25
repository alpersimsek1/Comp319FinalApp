package com.example.alpersimsek.myapp.Notes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpersimsek on 15.04.2018.
 */

public class Note {

    String uid, text, title;
    public Note(){}


    public Note(String uid, String title, String text){
        this.uid = uid;
        this.text = text;
        this.title = title;


    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("title",title);
        result.put("text", text);

        return result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
