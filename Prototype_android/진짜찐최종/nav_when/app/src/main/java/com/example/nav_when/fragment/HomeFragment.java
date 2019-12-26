package com.example.nav_when.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nav_when.BackPressHandler;
import com.example.nav_when.DatabaseHelper;
import com.example.nav_when.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;

import static android.content.Context.MODE_PRIVATE;
import static com.example.nav_when.DatabaseHelper.initialize;

public class HomeFragment extends Fragment {
    DatabaseHelper dbhelper;
    SQLiteDatabase sqlDB = null;
    Cursor cursor;
    private Button go_write_btn;
    String dddd, his_main;
    Vector<String> title_main;
    Vector<String> year_main;
    private String tablName;
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
    private BackPressHandler backPressHandler = new BackPressHandler(getActivity());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sf = this.getActivity().getSharedPreferences("sFile",MODE_PRIVATE);
        String langSetting = sf.getString("language","");
        if(!isLogin()){
            Intent mint = new Intent(getActivity(), logInOut.class);
            startActivity(mint);
        }
        //langSetting = "kor";
        int set_ = R.layout.fragment_home;
        if((langSetting.equals(""))||(langSetting.equals("kor"))){
            set_ = R.layout.fragment_home;
            tablName = "dictionary_365";

        }else if(langSetting.equals("eng")){
            set_ = R.layout.fragment_home_eng;
            tablName = "eng_365";

        }
        View root = inflater.inflate(set_, container, false);
        if(!isLogin()){
            Intent mint = new Intent(getActivity(), logInOut.class);
            startActivity(mint);
        }

        go_write_btn = (Button)root.findViewById(R.id.go_write);
        TextView main = (TextView)root.findViewById(R.id.history_main);
        TextView year = (TextView)root.findViewById(R.id.year_main);
        dddd = setDate();
        title_main = new Vector<String>(20,1);
        year_main = new Vector<String>(20,1);

        TextView date = (TextView)root.findViewById(R.id.date_main);

        date.setText(dddd);

        String mn ,dt,dump;

        String[] tmp= dddd.split(" ");
        dump = tmp[1];
        String[] tmp2 = dump.split("월");
        mn = tmp2[0];

        dump = tmp[2];
        tmp2 = dump.split("일");
        dt = tmp2[0];

        dbhelper = new DatabaseHelper(getContext());
        try {
            initialize(getContext());
        } catch (SQLiteException se) {
            Toast.makeText(getContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }
        sqlDB = dbhelper.getReadableDatabase();
        cursor = sqlDB.rawQuery("SELECT title, year FROM "+tablName+" WHERE month = "+mn+" and date = "+ dt+";",null);
        while(cursor.moveToNext()){//여기 작업중
            title_main.add(cursor.getString(0));
            year_main.add(cursor.getString(1));
        }
        Random random = new Random();
        int rand = random.nextInt(title_main.size());
        year.setText(year_main.get(rand));
        main.setText(title_main.get(rand));

        cursor.close();
        sqlDB.close();
        go_write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)getActivity()).replaceFragment(select_person.newInstance());
                select_person select = new select_person();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, select);
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    public String setDate(){
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(currentTime);

        return date_text;

    }
    public boolean isLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            return true;
        }else{
            return false;
        }
    }

}