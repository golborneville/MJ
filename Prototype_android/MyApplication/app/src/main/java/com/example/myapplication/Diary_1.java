package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.ui.diary1.Diary1Fragment;

public class Diary_1 extends AppCompatActivity {
    private Button ButtonToDiary2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_1_activity);
       if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Diary1Fragment.newInstance())
                    .commitNow();
        }
        ButtonToDiary2 = (Button) findViewById(R.id.btn_toDiary2);
    }
   protected void onStart(){
        super.onStart();
       ButtonToDiary2.setOnClickListener(new View.OnClickListener(){
           public void onClick(View view){
               Intent intent = new Intent(Diary_1.this, Diary_2.class);
               startActivity(intent);
           }
       });
    }
}
