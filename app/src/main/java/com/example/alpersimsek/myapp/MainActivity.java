package com.example.alpersimsek.myapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alpersimsek.myapp.Fragments.BudgetFragment;
import com.example.alpersimsek.myapp.Fragments.EventFragment;
import com.example.alpersimsek.myapp.Fragments.RunFragment;
import com.example.alpersimsek.myapp.Fragments.NoteFragment;
import com.example.alpersimsek.myapp.Login.User;
import com.example.alpersimsek.myapp.Notes.Note;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView eventList, budgetList, noteList, runlist;
    private TextView eventText, budgetText, noteText, runText;
    private BottomNavigationView menu;
    private DrawerLayout mDrawerLayout;
    private DatabaseReference mDatabase;
    public String[] events = {
           "asimsek 11231 06/12/2018 birtyday",
           "brnk 3432 23/11/2021 rocket",
          "mkac 123123 14/03/2022 Martian"
    };
    ArrayList<Note> notes;
    FrameLayout frameLayout;

    SelectFromNavigation navSelecter;

    public static final String USERID= "com.example.alpersimsek.myapp.userid";
    public static final String USEREMAIL= "com.example.alpersimsek.myapp.useremail";

    User dummyUser = new User("asimsek1994","alpersim94@gmail.com");
    private String TAG_DETAIL_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        eventText = findViewById(R.id.eventTextView);
//        budgetText = findViewById(R.id.budgetTextView);
//        noteText = findViewById(R.id.notesTextView);
//        runText = findViewById(R.id.runTextView);
//        eventList = findViewById(R.id.eventsList);
//        noteList = findViewById(R.id.notesList);
//        runlist = findViewById(R.id.budgetList);
        menu = findViewById(R.id.bottom_navigation);
        notes = new ArrayList<Note>();

        frameLayout = findViewById(R.id.mainFragment);
//        notes.add(new Note("123","New Note","yasasin oldu"));
//        notes.add(new Note("123123","Yeni Not", "Holleeyyy"));
//
//        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.two_line_list_item,
//                android.R.id.text1, events);
//
//        eventList.setAdapter(adapter);
//
//        navSelecter = new SelectFromNavigation(menu, getApplicationContext(), dummyUser);
//
//        NoteAdapter noteAdapter = new NoteAdapter(notes,getApplicationContext());
//
//        noteList.setAdapter(noteAdapter);
//
//        navSelecter.select();

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected = null;
                FragmentManager fragmentManager = getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.action_note:
                        selected =  NoteFragment.newinstance(dummyUser.getUid());
                        break;
                    case R.id.action_budget:
                        selected = BudgetFragment.newinstance(dummyUser.getUid());
                        break;
                    case R.id.action_event:
                        selected = EventFragment.newinstance(dummyUser.getUid());
                        break;
                    case R.id.action_run:
                        selected = RunFragment.newinstance();
                        break;
                }
                fragmentTransaction.replace(R.id.mainFragment, selected);
                fragmentTransaction.commit();
                return true;
            }
        });


    }


    public void eventToDB(View view) {

    }
}
