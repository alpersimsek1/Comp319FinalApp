package com.example.alpersimsek.myapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.alpersimsek.myapp.Models.Budget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.alpersimsek.myapp.MainActivity.USERID;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<Budget> budgets;
    DatabaseReference database;
    GoogleMap map;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        uid = bundle.getString(USERID);
        budgets = new ArrayList<>();

        database = FirebaseDatabase.getInstance().getReference().child("budgets").child(uid);
        database.addChildEventListener(childEventListener);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        map = googleMap;
        // Add a marker in Sydney and move the camera

        for (Budget budget : budgets) {
            double xloc = budget.getXloc();
            double yloc = budget.getYloc();
            String title = budget.getTitle();
            double amount = budget.getAmount();
            LatLng budgetLat = new LatLng(xloc, yloc);
            String markerTitle = title + " " + String.valueOf(amount);
            if (xloc != 0 && yloc != 0) {
                mMap.addMarker(new MarkerOptions().position(budgetLat).title(markerTitle));
            }
        }
    }

    public void AddToTheMap(Budget budget, GoogleMap googleMap) {
        mMap = googleMap;


        double xloc = budget.getXloc();
        double yloc = budget.getYloc();
        String title = budget.getTitle();
        double amount = budget.getAmount();
        LatLng budgetLat = new LatLng(xloc, yloc);
        String markerTitle = title + " " + String.valueOf(amount);
        if (xloc != 0 && yloc != 0) {
            mMap.addMarker(new MarkerOptions().position(budgetLat).title(markerTitle));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(budgetLat));
        }


    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Budget budget = dataSnapshot.getValue(Budget.class);
            budgets.add(budget);
            AddToTheMap(budget, map);
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
