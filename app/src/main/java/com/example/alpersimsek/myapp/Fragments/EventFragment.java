package com.example.alpersimsek.myapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.alpersimsek.myapp.Adapters.EventAdapter;
import com.example.alpersimsek.myapp.Models.Event;
import com.example.alpersimsek.myapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shrikanthravi.collapsiblecalendarview.*;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    EditText editable,date;
    Button toDB;
    DatabaseReference mDatabase, childData;
    String uid, calendarDate;
    EventAdapter adapter ;
    ListView listView;
    private ArrayList<Event> events;

    CollapsibleCalendar calendar;
    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        Bundle args = getArguments();
        uid = args.getString("uid");
        editable = view.findViewById(R.id.eventFragmentText);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        childData = mDatabase.child("events").child(uid);
        listView = view.findViewById(R.id.eventsList2);
        toDB = view.findViewById(R.id.eventFragmentButton);
        events = new ArrayList<>();

        toDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNewEvent(uid,String.valueOf(Math.random()),calendarDate,editable.getText().toString());
            }
        });
        childData.addChildEventListener(childEventListener);

        adapter = new EventAdapter(events,this.getContext());

        calendar = view.findViewById(R.id.eventCalendarView);

        calendar.setCalendarListener(calendarListener);
        listView.setAdapter(adapter);
        return view;
    }

    public static EventFragment newinstance(String uid){
        EventFragment f = new EventFragment();
        Bundle args = new Bundle();
        args.putString("uid", uid);
        f.setArguments(args);
        return f;
    }

    private void writeNewEvent(String uid, String eid, String date, String title){
        String key = childData.push().getKey();
        Event event = new Event(uid, eid,date, title);
        Map<String, Object> postValues = event.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/events/"+uid+'/' + key, postValues);

        mDatabase.updateChildren(childUpdates);

    }
    CollapsibleCalendar.CalendarListener calendarListener = new CollapsibleCalendar.CalendarListener() {
        @Override
        public void onDaySelect() {
            Day day = calendar.getSelectedDay();
            String da = String.valueOf(day.getDay());
            String mo = String.valueOf(day.getMonth());
            if(day.getDay() < 10) da = "0"+String.valueOf(day.getDay());
            if(day.getMonth() < 10) mo = "0"+String.valueOf(day.getMonth()+1);
            calendarDate = day.getYear()+"-"+mo+"-"+da+"T12:00:00.000+03:00";
        }

        @Override
        public void onItemClick(View view) {

        }

        @Override
        public void onDataUpdate() {

        }

        @Override
        public void onMonthChange() {

        }

        @Override
        public void onWeekChange(int i) {

        }
    };

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Event event = dataSnapshot.getValue(Event.class);
            events.add(event);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Event event = dataSnapshot.getValue(Event.class);
            events.remove(event);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
