package com.example.alpersimsek.myapp.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alpersimsek.myapp.Adapters.BudgetAdapter;
import com.example.alpersimsek.myapp.MapsActivity;
import com.example.alpersimsek.myapp.Models.Budget;
import com.example.alpersimsek.myapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.alpersimsek.myapp.MainActivity.USERID;

/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetFragment extends Fragment {


    String uid;
    LocationManager locationManager;
    TextView locationView, weeklyView, dailyView;
    Button budgetButton, showOnMap;
    DatabaseReference mDatabase, childData;
    ListView budgetList;
    String locationText;
    Double locx, locy;
    Double total;
    EditText title, amount;
    ArrayList<Budget> budgets;
    BudgetAdapter adapter;
    public BudgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_budget, container, false);

        budgetList = view.findViewById(R.id.budgetList2);
        title = view.findViewById(R.id.newBudgetText);
        amount = view.findViewById(R.id.newBudgetAmount);
        weeklyView = view.findViewById(R.id.weeklyBudget);
        dailyView = view.findViewById(R.id.dailyBudget);
        Bundle args = getArguments();
        uid = args.getString("uid");
        budgets = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        childData = mDatabase.child("budgets").child(uid);

        locationView = view.findViewById(R.id.locationViewBudget);
        budgetButton = view.findViewById(R.id.newBudgetButton);
        showOnMap = view.findViewById(R.id.showOnMap);
        childData.addChildEventListener(childEventListener);
        checkLocationPermission();
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        locx = 0.0 ;
        locy = 0.0 ;

        adapter = new BudgetAdapter(budgets, this.getContext());

        budgetList.setAdapter(adapter);

        budgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
//                Toast.makeText(getContext(),"hi there",Toast.LENGTH_LONG).show();

                String locationProvider = LocationManager.GPS_PROVIDER;
//                locationManager.requestLocationUpdates(locationProvider,0,0,locationListener);
                locationManager.requestSingleUpdate(locationProvider,locationListener, Looper.myLooper());

//                locationView.setText(locationText);
//                locationManager.removeUpdates(locationListener);
            }
        });

        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                intent.putExtra(USERID,uid);
                startActivity(intent);
            }
        });

        return view;
    }
    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Budget budget = dataSnapshot.getValue(Budget.class);
            budgets.add(budget);
            adapter.notifyDataSetChanged();
            weeklyView.setText(getResources().getString(R.string.weeklyBudget)+": "+lastSomeAmountOfDay(budgets,7));
            dailyView.setText(getResources().getString(R.string.dailyBudget)+": "+lastSomeAmountOfDay(budgets,1));

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Budget budget = dataSnapshot.getValue(Budget.class);
            budgets.remove(budget);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public static BudgetFragment newinstance(String uid){
        BudgetFragment f = new BudgetFragment();
        Bundle args = new Bundle();
        args.putString("uid", uid);
        f.setArguments(args);
        return f;    }


    final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            locx = location.getLatitude();
            locy = location.getLongitude();
            locationText = locx + " , " + locy;
            writeNewBudget(uid, title.getText().toString(),DateTime.now().toString(),Double.parseDouble(amount.getText().toString()),locx,locy);
            locationView.setText(locationText);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };



    private void createAlert() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private void writeNewBudget(String uid, String title, String date, Double amount, Double xloc, Double yloc){
        String key = mDatabase.child("budgets").child(uid).push().getKey();
        Budget budget = new Budget(uid,date,amount, title, xloc,yloc);
        Map<String, Object> postValues = budget.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/budgets/"+uid+'/' + key, postValues);

        mDatabase.updateChildren(childUpdates);

    }

    public String lastSomeAmountOfDay(ArrayList<Budget> list, int day){
        Double total = 0.0;
        DateTime today = DateTime.now();
        for(int i = 0; i < list.size(); i++){
            Budget budget = list.get(i);
            DateTime budgetDate = DateTime.parse(budget.getDay());
            int days = Days.daysBetween(budgetDate, today).getDays();
            Log.d("",String.valueOf(days));
            if(days < day){
                total += budget.getAmount();
            }
        }
        return String.valueOf(total);

    }

    @Override
    public void onPause() {
        super.onPause();
//        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
