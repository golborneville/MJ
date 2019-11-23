package com.example.nav_when.fragment;

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

import com.example.nav_when.DatabaseHelper;
import com.example.nav_when.MainActivity;
import com.example.nav_when.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;

import static com.example.nav_when.DatabaseHelper.initialize;

public class HomeFragment extends Fragment {
    DatabaseHelper dbhelper;
    SQLiteDatabase sqlDB = null;
    Cursor cursor;
    private Button go_write_btn;
    String dddd, his_main;
    Vector<String> title_main;
    Vector<String> year_main;
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
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
        cursor = sqlDB.rawQuery("SELECT title, year FROM dictionary_365 WHERE month = "+mn+" and date = "+ dt+";",null);
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
}