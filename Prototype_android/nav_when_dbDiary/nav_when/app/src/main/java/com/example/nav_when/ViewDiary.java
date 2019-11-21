package com.example.nav_when;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ViewDiary extends AppCompatActivity {
    private Button fixDiary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary);

        TextView date = (TextView)findViewById(R.id.date_diary);
        date.setText(getIntent().getStringExtra("date"));
        TextView person = (TextView)findViewById(R.id.sel_person_name);
        person.setText(getIntent().getStringExtra("person"));
        TextView tag = (TextView)findViewById(R.id.sel_person_tag);
        tag.setText(getIntent().getStringExtra("tag"));
        TextView text = (TextView)findViewById(R.id.read_box);
        text.setText(getIntent().getStringExtra("text"));
        fixDiary = (Button)findViewById(R.id.done_view);
        fixDiary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent mint = new Intent(ViewDiary.this, FixDiary.class);
                mint.putExtra("text", getIntent().getStringExtra("text"));
                mint.putExtra("date", getIntent().getStringExtra("date"));
                mint.putExtra("tag", getIntent().getStringExtra("tag"));
                mint.putExtra("person", getIntent().getStringExtra("person"));
                mint.putExtra("dateKey", getIntent().getStringExtra("dateKey"));

                startActivity(mint);
            }
        });

    }
    public String getDate(){ //database 접근용 key값
        String date = getIntent().getStringExtra("year")+getIntent().getStringExtra("month")+getIntent().getStringExtra("day");
        return date;
    }
    public String setDate(){
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy. MM. dd. ", Locale.getDefault()).format(currentTime);
        return date_text;

    }
}
