package com.example.nav_when.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nav_when.DatabaseHelper;
import com.example.nav_when.R;
import com.example.nav_when.dict_365_view;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.example.nav_when.DatabaseHelper.initialize;



public class dict_365 extends Fragment {


    public static final String ROOT_DIR = "/data/data/com.example.nav_when/";
    public static final String DATABASE_NAME = "dictionary_365.db";

    DatabaseHelper dbhelper;
    private String tablName;
    ListView list;
    List<String> title, link;
    SQLiteDatabase sqlDB = null;
    ListAdapter adapter;
    Cursor cursor;
    String mn,dt, urlLink;

    public static dict_365 newInstance(){
        return new dict_365();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dict_365, container, false);
        final String[] month = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};
        final String[] date = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
                , "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        Button go_search = (Button)root.findViewById(R.id.search_365) ;
        final Spinner monthSpinner = (Spinner) root.findViewById(R.id.month_spinner);
        final Spinner dateSpinner = (Spinner) root.findViewById(R.id.date_spinner);
        ArrayAdapter<String> adpter;
        adpter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, month);
        monthSpinner.setAdapter(adpter);

        ArrayAdapter<String> adpt;
        adpt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, date);
        dateSpinner.setAdapter(adpt);

        SharedPreferences sf = getContext().getSharedPreferences("sFile",MODE_PRIVATE);
        //언어 변경 점
        String langSetting = sf.getString("language","");

        if((langSetting.equals(""))||(langSetting.equals("kor"))){
            //kor
            tablName = "dictionary_365";
        }
        else if(langSetting.equals("eng")){
            //eng
            tablName = "eng_365";

        }
        dbhelper = new DatabaseHelper(getContext());
        try {
            initialize(getContext());
        }catch (SQLiteException se) {
            Toast.makeText(getContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }
        sqlDB = dbhelper.getReadableDatabase();
        String dt_txt = setDate();
        String[] dtdt = dt_txt.split("\\.");
        int mndt = Integer.parseInt(dtdt[1]);
        int dydt = Integer.parseInt(dtdt[0]);

        list = (ListView) root.findViewById(R.id.dic_title_search_listv);

        cursor = sqlDB.rawQuery("SELECT title, link FROM "+tablName+" WHERE month = "+mndt+" and date = " + dydt+ ";",null);

        title = new ArrayList<String>();
        link = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,title);
        list.setAdapter(adapter);

        while(cursor.moveToNext()){

            title.add(cursor.getString(0));
            link.add(cursor.getString(1));
        }
        cursor.close();
        go_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mn = monthSpinner.getSelectedItem().toString();
                dt = dateSpinner.getSelectedItem().toString();
                String[] mnA = mn.split("월");
                mn = mnA[0];
                //sqlDB = dbhelper.getReadableDatabase();
               //참고로 영어 버전은 12/15 ~ 12/19 만 했으니까 그 사이 값만 검색하셈 아님 터짐
                cursor = sqlDB.rawQuery("SELECT title, link FROM "+tablName+" WHERE month = "+mn+" and date = "+ dt+";",null);

                // cursor.moveToFirst();
                title.clear();
                link.clear();
                while(cursor.moveToNext()){

                    title.add(cursor.getString(0));
                    link.add(cursor.getString(1));
                }
                adapter.notifyDataSetChanged();
                cursor.close();
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mint = new Intent(getActivity(), dict_365_view.class);
                urlLink = link.get(position);
                mint.putExtra("link-name", urlLink);
                startActivity(mint);
            }
        });


        // cursor.close();
        //sqlDB.close();
        // t1.setText(attr1);

        return root;
    }
    public String setDate(){
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(currentTime);

        return date_text;

    }

}
