package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.ui.diary2.Diary2Fragment;

public class Diary_2 extends AppCompatActivity {
    private Button ButtonToCal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_2_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Diary2Fragment.newInstance())
                    .commitNow();
        }
        ButtonToCal = (Button) findViewById(R.id.btn_toCal);
    }
    protected void onStart(){
        super.onStart();
        ButtonToCal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(Diary_2.this, Calender.class);
                startActivity(intent);
            }
        });
    }
}
