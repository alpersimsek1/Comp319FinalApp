package com.example.alpersimsek.myapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.alpersimsek.myapp.Fragments.BudgetFragment;
import com.example.alpersimsek.myapp.Fragments.EventFragment;
import com.example.alpersimsek.myapp.Fragments.RunFragment;
import com.example.alpersimsek.myapp.Fragments.NoteFragment;
import com.example.alpersimsek.myapp.Models.User;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView menu;
    public String[] events = {
           "asimsek 11231 06/12/2018 birtyday",
           "brnk 3432 23/11/2021 rocket",
          "mkac 123123 14/03/2022 Martian"
    };
    FrameLayout frameLayout;

    public static final String USERID= "com.example.alpersimsek.myapp.userid";
    public static final String USEREMAIL= "com.example.alpersimsek.myapp.useremail";

    User dummyUser = new User("asimsek1994","alpersim94@gmail.com");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu = findViewById(R.id.bottom_navigation);

        frameLayout = findViewById(R.id.mainFragment);
        if(savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.mainFragment, EventFragment.newinstance(dummyUser.getUid()));
            transaction.commit();
        }

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
                        selected = RunFragment.newinstance(dummyUser.getUid());
                        break;
                }
                fragmentTransaction.replace(R.id.mainFragment, selected);
                fragmentTransaction.commit();
                return true;
            }
        });



    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 99: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}
