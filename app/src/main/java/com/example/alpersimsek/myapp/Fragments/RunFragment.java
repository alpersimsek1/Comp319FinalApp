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

import com.example.alpersimsek.myapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment implements SensorEventListener{

    String uid;
    private SensorManager mSensorManager;
    private Sensor airTemp;


    public RunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_run, container, false);
        Bundle args = getArguments();
        uid = args.getString("uid");

        return view;
    }

    public static RunFragment newinstance(String uid){
        RunFragment f = new RunFragment();
        Bundle args = new Bundle();
        args.putString("uid", uid);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
