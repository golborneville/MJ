package com.example.nav_when;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WriteDiary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        TextView date = (TextView)findViewById(R.id.date_diary);
        TextView name = (TextView)findViewById(R.id.sel_person_name);
        TextView tagview =(TextView)findViewById(R.id.sel_person_tag);
        date.setText(setDate());

        String person =getIntent().getStringExtra("person-name");
        String tag = getIntent().getStringExtra("tag-name");
        name.setText(person);
        tagview.setText(tag);



    }
    public String setDate(){
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy. MM. dd. ", Locale.getDefault()).format(currentTime);

        return date_text;

    }
}
