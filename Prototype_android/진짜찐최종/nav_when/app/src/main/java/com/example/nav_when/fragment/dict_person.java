package com.example.nav_when.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nav_when.DBPerson;
import com.example.nav_when.DBPerson_ENG;
import com.example.nav_when.R;
import com.example.nav_when.SearchAdapter;
import com.example.nav_when.dict_365_view;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.nav_when.DBPerson.initialize_PERSON;
import static com.example.nav_when.DBPerson_ENG.initialize_PERSON_ENG;


public class dict_person extends Fragment {

    public static dict_person newInstance(){
        return new dict_person();
    }
    private List<String> list,link;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;
    int lang;
    DBPerson dbhelp;
    SQLiteDatabase sqlDB;
    Cursor cursor;
    DBPerson_ENG dbENG;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dict_person, container, false);

        editSearch = (EditText) root.findViewById(R.id.dic_person_search);
        listView = (ListView) root.findViewById(R.id.dic_person_search_listv);

        // 리스트를 생성한다.
        list = new ArrayList<String>();
        link = new ArrayList<String>();
        // 검색에 사용할 데이터을 미리 저장한다.
        settingList();

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<String>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(list, getContext());

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });

        SharedPreferences sf = getContext().getSharedPreferences("sFile",MODE_PRIVATE);
        //언어 변경 점
        String langSetting = sf.getString("language","");

        if((langSetting.equals(""))||(langSetting.equals("kor"))){
            //kor

            dbhelp = new DBPerson(getContext());
            try {
                initialize_PERSON(getContext());
            }catch (SQLiteException se) {
                Toast.makeText(getContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("", se.getMessage());
            }
            sqlDB = dbhelp.getReadableDatabase();
        }
        else if(langSetting.equals("eng")){
            //eng

            dbENG = new DBPerson_ENG(getContext());
            try {
                initialize_PERSON_ENG(getContext());
            }catch (SQLiteException se) {
                Toast.makeText(getContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("", se.getMessage());
            }
            sqlDB = dbENG.getReadableDatabase();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mint = new Intent(getActivity(), dict_365_view.class);
                String tmp = list.get(position);
                String[] tmpInd = tmp.split(" ");
                //String nm = "\""+tmp"/"";
                String urlLink="";

                cursor = sqlDB.rawQuery("SELECT url FROM historyPerson WHERE name = \""+tmpInd[0]+"\";",null);
                while(cursor.moveToNext()){
                    urlLink = cursor.getString(0);
                }
                mint.putExtra("link-name", urlLink);
                startActivity(mint);
            }
        });
        return root;
    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    private void settingList(){
        String tmp = "";
        SharedPreferences sf = getContext().getSharedPreferences("sFile",MODE_PRIVATE);
        //언어 변경 점
        String langSetting = sf.getString("language","");

        if((langSetting.equals(""))||(langSetting.equals("kor"))){
            //kor

            dbhelp = new DBPerson(getContext());
            try {
                initialize_PERSON(getContext());
            }catch (SQLiteException se) {
                Toast.makeText(getContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("", se.getMessage());
            }
            sqlDB = dbhelp.getReadableDatabase();
        }
        else if(langSetting.equals("eng")){
            //eng

            dbENG = new DBPerson_ENG(getContext());
            try {
                initialize_PERSON_ENG(getContext());
            }catch (SQLiteException se) {
                Toast.makeText(getContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("", se.getMessage());
            }
            sqlDB = dbENG.getReadableDatabase();
        }
        cursor = sqlDB.rawQuery("SELECT name, period, category, url FROM historyPerson;",null);
        while(cursor.moveToNext()){
            tmp +=cursor.getString(0) + " ";
            tmp+= cursor.getString(1)+ " ";
            tmp+= cursor.getString(2);
            list.add(tmp);
            tmp = "";
            //link.add(cursor.getString(3)); //응 여기 안맞아^^.. 낼 마저 하자 ㅅㅂ
        }
    }

}
