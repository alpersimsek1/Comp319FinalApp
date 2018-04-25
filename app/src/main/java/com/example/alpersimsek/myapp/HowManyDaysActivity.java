package com.example.alpersimsek.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.alpersimsek.myapp.HowManyDays.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.alpersimsek.myapp.MainActivity.USERID;

public class HowManyDaysActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    EditText editable;
    Button sendToDB;
    private String uid;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_many_days);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        editable = findViewById(R.id.eventEditText);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        uid = bundle.getString(USERID);

    }

    public void eventToDB(View view) {
        String title = editable.getText().toString();

        writeNewEvent(uid,String.valueOf(Math.random()),DateTime.now().toString(),title);
    }

    private void writeNewEvent(String uid, String eid, String date, String title){
        String key = mDatabase.child("events").push().getKey();
        Event event = new Event(uid, eid,date, title);
        Map<String, Object> postValues = event.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/events/" + key, postValues);

        mDatabase.updateChildren(childUpdates);

    }
}
