package com.example.nav_when;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class settings extends AppCompatActivity {
    private Button sync, delete, copyright,note, lang, account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);

        account = (Button)findViewById(R.id.logIn);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mint = new Intent(settings.this, logInOut_.class);
                startActivity(mint);
            }
        });

        copyright = (Button)findViewById(R.id.copyrights);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mint = new Intent(settings.this, CopyRight.class);
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
                SharedPreferences.Editor editor = sf.edit();
                //언어 변경 점
                String langSetting = sf.getString("language","");

                if((langSetting.equals(""))||(langSetting.equals("kor"))){
                    langSetting = "eng";
                    editor.putString("language",langSetting);
                    Log.i("set the lang",langSetting);
                    Log.i("set the lang","언어가 영어로 설정되었습니다");
                    Toast.makeText(getApplicationContext(),"언어가 영어로 설정되었습니다",Toast.LENGTH_SHORT).show();
                }else if(langSetting.equals("eng")){
                    langSetting = "kor";
                    editor.putString("language",langSetting);
                    Log.i("set the lang",langSetting);
                    Log.i("set the lang","언어가 한국어로 설정되었습니다");
                    Toast.makeText(getApplicationContext(),"언어가 한국어로 설정되었습니다",Toast.LENGTH_SHORT).show();
                }

                editor.commit();
            }

        });
    }
}
