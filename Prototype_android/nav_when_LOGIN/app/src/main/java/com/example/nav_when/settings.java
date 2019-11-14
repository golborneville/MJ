package com.example.nav_when;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class settings extends AppCompatActivity {
    private Button sync, delete, copyright,note, lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sync = (Button)findViewById(R.id.sync);
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //WORKING TOTALLY FINE
                //NEED IMPLEMENTATION
                Intent mint = new Intent(settings.this, logInOut_.class);
                startActivity(mint);
                Toast.makeText(getApplicationContext(), "test for button ", Toast.LENGTH_SHORT).show();
            }
        });


        copyright = (Button)findViewById(R.id.copyrights);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mint = new Intent(settings.this, Calendar_v.class);
                startActivity(mint);
            }
        });

        note = (Button)findViewById(R.id.developer_note);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mint = new Intent(settings.this, DevelopersNote.class);
                startActivity(mint);
            }
        });
        lang = (Button)findViewById(R.id.language);
        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mint = new Intent(settings.this, go_cal_te.class);
                startActivity(mint);
            }
        });
    }
}
