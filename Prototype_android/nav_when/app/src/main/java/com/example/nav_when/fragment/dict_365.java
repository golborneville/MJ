package com.example.nav_when.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.nav_when.R;

import java.util.ArrayList;

public class dict_365 extends Fragment {

    public static dict_365 newInstance(){
        return new dict_365();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dict_365, container, false);
        final String[] month = {"1월","2월", "3월", "4월", "5월", "6월","7월","8월", "9월", "10월", "11월", "12월"};
        final String[] date = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"
        , "21","22","23","24","25","26","27","28","29","30","31"};

        Spinner monthSpinner = (Spinner)root.findViewById(R.id.month_spinner);
        Spinner dateSpinner = (Spinner)root.findViewById(R.id.date_spinner);

        ArrayAdapter<String> adpter;
        adpter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, month);
        monthSpinner.setAdapter(adpter);

        ArrayAdapter<String> adpt;
        adpt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, date);
        dateSpinner.setAdapter(adpt);
        return root;
    }
}
