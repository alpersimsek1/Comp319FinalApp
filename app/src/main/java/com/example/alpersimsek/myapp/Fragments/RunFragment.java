package com.example.alpersimsek.myapp.Fragments;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alpersimsek.myapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment {

    String uid;
    private SensorManager mSensorManager;
    private Sensor airTemp;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener listener;

    TextView runText;

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
        runText = view.findViewById(R.id.runText);


        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[2] > 0.5f) {
                    runText.setText("start run");
                    startRun();
                } else if(sensorEvent.values[2] < -0.5f) {
                    runText.setText("end run ");
                    endRun();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(listener,
                sensor, SensorManager.SENSOR_DELAY_NORMAL);

        return view;
    }

    public void startRun(){

    }
    public void endRun(){

    }
    @Override
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(listener);
    }

    public static RunFragment newinstance(String uid){
        RunFragment f = new RunFragment();
        Bundle args = new Bundle();
        args.putString("uid", uid);
        f.setArguments(args);
        return f;
    }

}
