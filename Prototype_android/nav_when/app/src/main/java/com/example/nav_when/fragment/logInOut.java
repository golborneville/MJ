package com.example.nav_when.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nav_when.R;


public class logInOut extends Fragment {

    public static logInOut newInstance(String param1, String param2) {
        logInOut fragment = new logInOut();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_log_in_out, container, false);
        return root;
    }
}