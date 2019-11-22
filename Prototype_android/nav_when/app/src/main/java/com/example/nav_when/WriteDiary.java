package com.example.nav_when;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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


public class WriteDiary extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private Button setDiary;
    public String userMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        TextView date = (TextView)findViewById(R.id.date_diary);
        TextView name = (TextView)findViewById(R.id.sel_person_name);
        TextView tagview =(TextView)findViewById(R.id.sel_person_tag);
        date.setText(setDate());
        EditText editText = (EditText)findViewById(R.id.write_box);
        final String text = editText.getText().toString();
        final String person = getIntent().getStringExtra("person-name");
        final String tag = getIntent().getStringExtra("tag-name");
        name.setText(person);
        tagview.setText(getIntent().getStringExtra("tag-name"));
        setDiary = (Button)findViewById(R.id.done_write);
        setDiary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                writeData(true, person, tag);
                Intent mint = new Intent(WriteDiary.this, Calendar_v.class);
                startActivity(mint);
            }
        });

    }
    public String setDate(){
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy. MM. dd. ", Locale.getDefault()).format(currentTime);

        return date_text;

    }
    public String getDateKey(){
        Date cT = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("yyyyMMdd",Locale.getDefault()).format(cT);
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

    public void writeData(boolean add, String person, String tag){ //add : post, false : delete
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String date = setDate();
        String mail = getUserEmail();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){
            EditText editText = (EditText)findViewById(R.id.write_box);
            DiaryData data = new DiaryData(date, person, tag, editText.getText().toString(), mail);
            postValues = data.toMap();
        }
        childUpdates.put("/diary/"+getUserUid()+"/"+getDateKey(),postValues);
        databaseReference.updateChildren(childUpdates);
        //databaseReference.child("diary").setValue(childUpdates);
    }

}
