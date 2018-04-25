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
import com.example.alpersimsek.myapp.HowManyDays.Event;
import com.example.alpersimsek.myapp.Notes.Note;
import com.example.alpersimsek.myapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    EditText editable,date;
    Button toDB;
    DatabaseReference mDatabase, childData;
    String uid;
    EventAdapter adapter ;
    ListView listView;
    private ArrayList<Event> events;

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
        date = view.findViewById(R.id.dateText);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        childData = mDatabase.child("events").child(uid);
        listView = view.findViewById(R.id.eventsList2);
        toDB = view.findViewById(R.id.eventFragmentButton);
        events = new ArrayList<>();

        toDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNewEvent(uid,String.valueOf(Math.random()),DateTime.now().toString(),editable.getText().toString());
            }
        });
        childData.addChildEventListener(childEventListener);

        adapter = new EventAdapter(events,this.getContext());

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

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
