package com.example.alpersimsek.myapp.Fragments;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alpersimsek.myapp.Adapters.NoteAdapter;
import com.example.alpersimsek.myapp.Notes.Note;
import com.example.alpersimsek.myapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {

    private ArrayList<Note> notes;

    NoteAdapter noteAdapter;
    DatabaseReference mDatabase, childData;
    EditText text;
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        Button button = view.findViewById(R.id.fragmentNoteButton);

        mDatabase = FirebaseDatabase.getInstance().getReference();


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
                Note note = new Note(uid,text.getText().toString(),"FragmentTest");
                writeNewNote(note);
            }
        });
        return view;
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
}
