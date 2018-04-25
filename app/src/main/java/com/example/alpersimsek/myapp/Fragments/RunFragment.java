package com.example.alpersimsek.myapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alpersimsek.myapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment {


    public RunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_run, container, false);
    }

    public static RunFragment newinstance(){
        return new RunFragment();
    }
}
