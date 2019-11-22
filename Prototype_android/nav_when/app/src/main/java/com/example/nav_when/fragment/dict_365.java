package com.example.nav_when.fragment;

import android.content.Context;
import android.content.res.AssetManager;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nav_when.DatabaseHelper;
import com.example.nav_when.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.nav_when.DatabaseHelper.initialize;


public class dict_365 extends Fragment {


    public static final String ROOT_DIR = "/data/data/com.example.nav_when/";
    public static final String DATABASE_NAME = "dictionary_365.db";

    DatabaseHelper dbhelper;

    ListView list;
    List<String> title;
    SQLiteDatabase sqlDB = null;
    ListAdapter adapter;
    Cursor cursor;
    String mn,dt;

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
        list = (ListView) root.findViewById(R.id.dic_title_search_listv);
        dbhelper = new DatabaseHelper(getContext());        try {
            initialize(getContext());
        } catch (SQLiteException se) {
            Toast.makeText(getContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }
        sqlDB = dbhelper.getReadableDatabase();

        cursor = sqlDB.rawQuery("SELECT title FROM dictionary_365 WHERE month = 1 and date = 1;",null);

        title = new ArrayList<String>();
        //List<String> link = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,title);
        list.setAdapter(adapter);

        while(cursor.moveToNext()){

            title.add(cursor.getString(0));
            //link.add(cursor.getString(1));
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

                cursor = sqlDB.rawQuery("SELECT title FROM dictionary_365 WHERE month = "+mn+" and date = "+ dt+";",null);

               // cursor.moveToFirst();
                title.clear();
                while(cursor.moveToNext()){

                    title.add(cursor.getString(0));
                    //link.add(cursor.getString(1));
                }
                adapter.notifyDataSetChanged();
                cursor.close();
            }
        });



       // cursor.close();
        //sqlDB.close();
       // t1.setText(attr1);

        return root;
    }


}
