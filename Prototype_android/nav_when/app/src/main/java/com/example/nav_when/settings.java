package com.example.nav_when;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class settings extends AppCompatActivity {
    private Button sync, delete, copyright,note;
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
                Toast.makeText(getApplicationContext(), "test for button ", Toast.LENGTH_SHORT).show();
            }
        });


        copyright = (Button)findViewById(R.id.copyrights);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        note = (Button)findViewById(R.id.developer_note);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }
}
