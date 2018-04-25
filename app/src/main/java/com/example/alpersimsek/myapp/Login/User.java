package com.example.alpersimsek.myapp.Login;

/**
 * Created by alpersimsek on 15.04.2018.
 */

public class User {
    private String username, email, uid;
    public User(){}

    public User(String uid, String email){
        this.uid = uid;
        this.email = email;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
