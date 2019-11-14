package com.example.nav_when;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViewDiary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary);

        TextView date = (TextView)findViewById(R.id.date_diary);
        date.setText(setDate());
    }

    public String setDate(){
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy. MM. dd. ", Locale.getDefault()).format(currentTime);

        return date_text;

    }
}
