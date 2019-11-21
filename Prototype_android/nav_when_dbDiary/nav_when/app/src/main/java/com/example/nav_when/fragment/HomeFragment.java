package com.example.nav_when.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nav_when.MainActivity;
import com.example.nav_when.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private Button go_write_btn;
    String dddd;
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        go_write_btn = (Button)root.findViewById(R.id.go_write);

        dddd = setDate();

        TextView date = (TextView)root.findViewById(R.id.date_main);
        date.setText(dddd);

        go_write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)getActivity()).replaceFragment(select_person.newInstance());
                select_person select = new select_person();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, select);
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    public String setDate(){
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(currentTime);

        return date_text;

    }
}