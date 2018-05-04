package com.example.alpersimsek.myapp.Fragments;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.alpersimsek.myapp.Adapters.NoteAdapter;
import com.example.alpersimsek.myapp.Models.Note;
import com.example.alpersimsek.myapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment implements SensorEventListener {

    private ArrayList<Note> notes;

    private SensorManager sensorManager;
    private Sensor senAccelerometer;

    NoteAdapter noteAdapter;
    DatabaseReference mDatabase, childData;
    EditText text;
    String uid;
    long lastUpdate;
    Note note;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        sensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        Button button = view.findViewById(R.id.fragmentNoteButton);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        lastUpdate = System.currentTimeMillis();
        text = view.findViewById(R.id.fragmentNoteText);

        ListView noteList = view.findViewById(R.id.fragmentNoteList);
        notes = new ArrayList<>();

        Bundle args = getArguments();
        uid = args.getString("uid");

        childData = mDatabase.child("notes").child(uid);

        childData.addChildEventListener(childEventListener);

        noteAdapter = new NoteAdapter(notes,this.getContext());

        noteList.setAdapter(noteAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note(uid,text.getText().toString());
                writeNewNote(note);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    public static NoteFragment newinstance(String uid){
        NoteFragment f = new NoteFragment();
        Bundle args = new Bundle();
        args.putString("uid", uid);
        f.setArguments(args);
        return f;
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Note note = dataSnapshot.getValue(Note.class);
            notes.add(note);
            noteAdapter.notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Note note = dataSnapshot.getValue(Note.class);
            notes.remove(note);
            noteAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void writeNewNote(Note note){
        String key = childData.push().getKey();
        Map<String, Object> postValues = note.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/notes/"+uid+'/' + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    public ArrayList<Note> reverse(ArrayList<Note> list) {
        if(list.size() > 1) {
            Note value = list.remove(0);
            reverse(list);
            list.add(value);
        }
        return list;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(sensorEvent);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 10) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            note = new Note(uid, text.getText().toString());
            writeNewNote(note);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
