package com.example.nav_when.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nav_when.Calendar_v;
import com.example.nav_when.DataRequest;
import com.example.nav_when.DiaryData;
import com.example.nav_when.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class WriteDiary extends Fragment {
    private DatabaseReference databaseReference;
    private Button setDiary;
    public String userMail;
    View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.activity_write_diary, container, false);

        TextView date = (TextView)root.findViewById(R.id.date_diary);
        TextView name = (TextView)root.findViewById(R.id.sel_person_name);
        TextView tagview =(TextView)root.findViewById(R.id.sel_person_tag);
        date.setText(setDate());
        EditText editText = (EditText)root.findViewById(R.id.write_box);
        final String text = editText.getText().toString();
        final String person = getArguments().getString("person-name");
        final String tag = getArguments().getString("tag-name");
        final String[] personList = getArguments().getStringArray("person-list");
        name.setText(person);
        tagview.setText(getArguments().getString("tag-name"));
        setDiary = (Button)root.findViewById(R.id.done_write);
        setDiary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                writeData(true, person, tag, root);
                DataRequest dr = new DataRequest();
                dr.postMostSelected(personList, person);

                Fragment frag = new Calendar_v_2();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,  frag);
                fragmentTransaction.commit();
            }
        });
        return root;
    }
    public String setDate(){
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy. MM. dd. ", Locale.getDefault()).format(currentTime);

        return date_text;

    }
    public String getDateKey(){
        Date cT = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("yyyy,MM,dd",Locale.getDefault()).format(cT);
        return date;
    }
    public String getUserEmail(){
        String mail = "sam";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            mail = user.getEmail();
        }else{

        }
        return mail;
    }
    public String getUserUid(){
        String Uid = null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Uid = user.getUid();
        }else{

        }
        return Uid;
    }

    public void writeData(boolean add, String person, String tag, View v){ //add : post, false : delete
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String date = setDate();
        String mail = getUserEmail();
        String extraTXT = "";
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){
            EditText editText = (EditText)v.findViewById(R.id.write_box);
            DiaryData data = new DiaryData(date, person, tag, editText.getText().toString(), mail,extraTXT);
            postValues = data.toMap();
        }
        childUpdates.put("/diary/"+getUserUid()+"/"+getDateKey(),postValues);
        databaseReference.updateChildren(childUpdates);
        //databaseReference.child("diary").setValue(childUpdates);
    }

}
