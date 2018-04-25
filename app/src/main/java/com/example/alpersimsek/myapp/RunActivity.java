package com.example.alpersimsek.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.alpersimsek.myapp.MainActivity.USERID;

public class RunActivity extends AppCompatActivity {

    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        Intent intent = getIntent();
        uid = intent.getExtras().getString(USERID);
    }
}
