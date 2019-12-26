package com.example.nav_when.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nav_when.DBPerson;
import com.example.nav_when.DBPerson_ENG;
import com.example.nav_when.DiaryData;
import com.example.nav_when.FixDiary;
import com.example.nav_when.R;
import com.example.nav_when.ViewDiary;
import com.example.nav_when.personWebview;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static com.example.nav_when.DBPerson.initialize_PERSON;
import static com.example.nav_when.DBPerson_ENG.initialize_PERSON_ENG;


public class select_person extends Fragment  {
    private int selected_position;
    //private ArrayList<String> chkPerson = new ArrayList<String>();
    private String nameSelect,tagSelect;
    private String[] personList = {"person1","person2","person3","person4","person5"};
    private Button select, q1,q2,q3,q4,q5;
    private CheckBox chk1,chk2,chk3,chk4,chk5;
    private TextView tag1, tag2, tag3, tag4, tag5;
    private DatabaseReference mReference;
    private ArrayList<CheckBox> mchkbox = new ArrayList<CheckBox>();
    String []chkPerson,link, definy,per, ar,gn,cat;
    DBPerson dbhelp;
    DBPerson_ENG dbENG;
    SQLiteDatabase sqlDB = null;
    Cursor cursor;
    public static select_person newInstance(){
        return new select_person();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sf = getContext().getSharedPreferences("sFile",MODE_PRIVATE);
        //언어 변경 점
        String langSetting = sf.getString("language","");
        int set_ = R.layout.fragment_select_person;
        if((langSetting.equals(""))||(langSetting.equals("kor"))){
            //kor
            set_ = R.layout.fragment_select_person;
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
            set_ = R.layout.fragment_select_person_eng;

            dbENG = new DBPerson_ENG(getContext());
            try {
                initialize_PERSON_ENG(getContext());
            }catch (SQLiteException se) {
                Toast.makeText(getContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("", se.getMessage());
            }
            sqlDB = dbENG.getReadableDatabase();
        }
        // Inflate the layout for this fragment
        View root =  inflater.inflate(set_, container, false);
        nameSelect = "";
        tagSelect="";
        q1 = (Button)root.findViewById(R.id.today_person1_btn);
        q2 = (Button)root.findViewById(R.id.today_person2_btn);
        q3 = (Button)root.findViewById(R.id.today_person3_btn);
        q4 = (Button)root.findViewById(R.id.today_person4_btn);
        q5 = (Button)root.findViewById(R.id.today_person5_btn);

        chk1 = (CheckBox)root.findViewById(R.id.today_person1);
        chk2 = (CheckBox)root.findViewById(R.id.today_person2);
        chk3 = (CheckBox)root.findViewById(R.id.today_person3);
        chk4 = (CheckBox)root.findViewById(R.id.today_person4);
        chk5 = (CheckBox)root.findViewById(R.id.today_person5);

        tag1 = (TextView)root.findViewById(R.id.today_person1_Tview);
        tag2 = (TextView)root.findViewById(R.id.today_person2_Tview);
        tag3 = (TextView)root.findViewById(R.id.today_person3_Tview);
        tag4 = (TextView)root.findViewById(R.id.today_person4_Tview);
        tag5 = (TextView)root.findViewById(R.id.today_person5_Tview);

        mchkbox.add(chk1);
        mchkbox.add(chk2);
        mchkbox.add(chk3);
        mchkbox.add(chk4);
        mchkbox.add(chk5);
        int lang=0;


        //이거 DB 손 봐야 해서 (index필요해 시밤바) 인물 디비 대체 언제 주나...
        //13주차에 하기 ^^!

        link  = new String[5];
        definy = new String[5];
        per = new String[5];
        ar = new String[5];
        gn = new String[5];
        cat = new String[5];
        chkPerson = new String[5];
        String dt =setDate();
        String[] tmp = dt.split("-");
        int[] ch = new int[3];
        ch[0] = Integer.parseInt(tmp[0]);
        ch[1] = Integer.parseInt(tmp[1]);
        ch[2] = Integer.parseInt(tmp[2]);
        final String shot_Day = tmp[0] + ". " + tmp[1] + ". " + tmp[2];
        final String dateKey = ""+tmp[0]+","+tmp[1]+","+tmp[2];


        Random rnd = new Random();
        int randNum = ch[0] - 2019 + ch[1] + ch[2]; //원래는 ch[0] - 2019 + ch[1]*30 + ch[2]; 임
        //영어 디비가 인덱스가 50까지라 * 30 지움
        //randNum = rnd.nextInt(3690);

        int ind = 0;
        cursor = sqlDB.rawQuery("SELECT name, url, definition, period, area, gender, category FROM historyPerson WHERE idx = "+randNum+";",null);
        while(cursor.moveToNext()){
            chkPerson[ind] = cursor.getString(0);
            link[ind] = cursor.getString(1);
            definy[ind] = cursor.getString(2);
            per[ind] = cursor.getString(3);
            ar[ind] = cursor.getString(4);
            gn[ind] = cursor.getString(5);
            cat[ind] = cursor.getString(6);
            ind++;
        }
        ind = 0;
        chk1.setText(chkPerson[ind]);
        tag1.setText(definy[ind]);
        ind++;
        chk2.setText(chkPerson[ind]);
        tag2.setText(definy[ind]);
        ind++;
        chk3.setText(chkPerson[ind]);
        tag3.setText(definy[ind]);
        ind++;
        chk4.setText(chkPerson[ind]);
        tag4.setText(definy[ind]);
        ind++;
        chk5.setText(chkPerson[ind]);
        tag5.setText(definy[ind]);

        readData(dateKey, shot_Day);

        chk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    for(int i=0; i< mchkbox.size(); i++){
                        if(mchkbox.get(i) == v) {
                            selected_position = i;
                            nameSelect = chk1.getText().toString();
                            tagSelect = tag1.getText().toString();
                        }
                        else
                            mchkbox.get(i).setChecked(false);
                    }
                }
                else{
                    nameSelect="";
                    tagSelect="";
                }
            }
        });

        chk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    for(int i=0; i< mchkbox.size(); i++){
                        if(mchkbox.get(i) == v) {
                            selected_position = i;
                            nameSelect = chk2.getText().toString();
                            tagSelect = tag2.getText().toString();
                        }
                        else
                            mchkbox.get(i).setChecked(false);
                    }
                }
                else{
                    nameSelect="";
                    tagSelect="";
                }
            }
        });
        chk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    for(int i=0; i< mchkbox.size(); i++){
                        if(mchkbox.get(i) == v) {
                            selected_position = i;
                            nameSelect = chk3.getText().toString();
                            tagSelect = tag3.getText().toString();
                        }
                        else
                            mchkbox.get(i).setChecked(false);
                    }
                }
                else{
                    nameSelect="";
                    tagSelect="";
                }
            }
        });
        chk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    for(int i=0; i< mchkbox.size(); i++){
                        if(mchkbox.get(i) == v) {
                            selected_position = i;
                            nameSelect = chk4.getText().toString();
                            tagSelect = tag4.getText().toString();
                        }
                        else
                            mchkbox.get(i).setChecked(false);
                    }
                }
                else{
                    nameSelect="";
                    tagSelect="";
                }
            }
        });
        chk5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    for(int i=0; i< mchkbox.size(); i++){
                        if(mchkbox.get(i) == v) {
                            selected_position = i;
                            nameSelect = chk5.getText().toString();
                            tagSelect = tag5.getText().toString();
                        }
                        else
                            mchkbox.get(i).setChecked(false);
                    }
                }
                else{
                    nameSelect="";
                    tagSelect="";
                }
            }
        });


        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Button close = (Button) popupView.findViewById(R.id.close); // PopupWindow 상의 View의 Button 연결
                Button goLink = (Button) popupView.findViewById(R.id.link);
                TextView tt = (TextView) popupView.findViewById(R.id.popupquestion);
                popupWindow.setContentView(popupView);
                String scr = per[0] +ar[0] + gn[0]+ cat[0];
                tt.setText(scr);
                popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setTouchable(true); // PopupWindow 위에서 Button의 Click이 가능하도록 setTouchable(true);
                popupWindow.setFocusable(true);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss(); // PopupWindow의 해제
                    }
                });
                goLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //상세 링크 웹뷰 ㄱ
                        Intent mint = new Intent(getActivity(), personWebview.class);
                        mint.putExtra("link-name", link[0]);
                        startActivity(mint);
                    }
                });
                popupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0,0); // PopupWindow를 View 위에 뿌려 줌. 선언하지 않을 경우, PopupWindow가 보이지 않음


            }

        });
        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Button close = (Button) popupView.findViewById(R.id.close); // PopupWindow 상의 View의 Button 연결
                Button goLink = (Button) popupView.findViewById(R.id.link);
                TextView tt = (TextView) popupView.findViewById(R.id.popupquestion);
                popupWindow.setContentView(popupView);
                String scr = per[1] +ar[1] + gn[1]+ cat[1];
                tt.setText(scr);
                popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setTouchable(true); // PopupWindow 위에서 Button의 Click이 가능하도록 setTouchable(true);
                popupWindow.setFocusable(true);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss(); // PopupWindow의 해제
                    }
                });
                goLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //상세 링크 웹뷰 ㄱ
                        Intent mint = new Intent(getActivity(), personWebview.class);
                        mint.putExtra("link-name", link[1]);
                        startActivity(mint);
                    }
                });
                popupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0,0); // PopupWindow를 View 위에 뿌려 줌. 선언하지 않을 경우, PopupWindow가 보이지 않음


            }

        });

        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Button close = (Button) popupView.findViewById(R.id.close); // PopupWindow 상의 View의 Button 연결
                Button goLink = (Button) popupView.findViewById(R.id.link);
                TextView tt = (TextView) popupView.findViewById(R.id.popupquestion);
                popupWindow.setContentView(popupView);
                String scr = per[2] +ar[2] + gn[2]+ cat[2];
                tt.setText(scr);
                popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setTouchable(true); // PopupWindow 위에서 Button의 Click이 가능하도록 setTouchable(true);
                popupWindow.setFocusable(true);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss(); // PopupWindow의 해제
                    }
                });
                goLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //상세 링크 웹뷰 ㄱ
                        Intent mint = new Intent(getActivity(), personWebview.class);
                        mint.putExtra("link-name", link[2]);
                        startActivity(mint);
                    }
                });
                popupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0,0); // PopupWindow를 View 위에 뿌려 줌. 선언하지 않을 경우, PopupWindow가 보이지 않음


            }

        });

        q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Button close = (Button) popupView.findViewById(R.id.close); // PopupWindow 상의 View의 Button 연결
                Button goLink = (Button) popupView.findViewById(R.id.link);
                TextView tt = (TextView) popupView.findViewById(R.id.popupquestion);
                popupWindow.setContentView(popupView);
                String scr = per[3] +ar[3] + gn[3]+ cat[3];
                tt.setText(scr);
                popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setTouchable(true); // PopupWindow 위에서 Button의 Click이 가능하도록 setTouchable(true);
                popupWindow.setFocusable(true);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss(); // PopupWindow의 해제
                    }
                });
                goLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //상세 링크 웹뷰 ㄱ
                        Intent mint = new Intent(getActivity(), personWebview.class);
                        mint.putExtra("link-name", link[3]);
                        startActivity(mint);
                    }
                });
                popupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0,0); // PopupWindow를 View 위에 뿌려 줌. 선언하지 않을 경우, PopupWindow가 보이지 않음


            }

        });

        q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Button close = (Button) popupView.findViewById(R.id.close); // PopupWindow 상의 View의 Button 연결
                Button goLink = (Button) popupView.findViewById(R.id.link);
                TextView tt = (TextView) popupView.findViewById(R.id.popupquestion);
                popupWindow.setContentView(popupView);
                String scr = per[4] +ar[4] + gn[4]+ cat[4];
                tt.setText(scr);
                popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setTouchable(true); // PopupWindow 위에서 Button의 Click이 가능하도록 setTouchable(true);
                popupWindow.setFocusable(true);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss(); // PopupWindow의 해제
                    }
                });
                goLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //상세 링크 웹뷰 ㄱ
                        Intent mint = new Intent(getActivity(), personWebview.class);
                        mint.putExtra("link-name", link[4]);
                        startActivity(mint);
                    }
                });
                popupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0,0); // PopupWindow를 View 위에 뿌려 줌. 선언하지 않을 경우, PopupWindow가 보이지 않음


            }

        });
        select = (Button)root.findViewById(R.id.today_done);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLogin()){
                    Intent mint = new Intent(getActivity(), logInOut.class);
                    Toast.makeText(getContext(),"로그인 해주세요", Toast.LENGTH_SHORT).show();
                    startActivity(mint);
                }
                //Intent mint = new Intent(getActivity(), WriteDiary.class);
                //select_person fg = new select_person();
                Bundle bundle = new Bundle();
                if(nameSelect.equals("") && tagSelect.equals("")){
                    Toast.makeText(getContext(),"인물을 선택해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    bundle.putString("person-name", nameSelect);
                    bundle.putString("tag-name",tagSelect);
                    bundle.putStringArray("person-list",chkPerson);
                    //startActivity(mint);
                    //bundle.setArguments(bundle);
                    Fragment frag = new WriteDiary();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
                    frag.setArguments(bundle);
                    fragmentTransaction.replace(R.id.nav_host_fragment,  frag);
                    fragmentTransaction.commit();
                }


            }
        });
        return root;
    }
    public boolean isLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            return true;
        }else{
            return false;
        }
    }
    public String setDate(){
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime);

        return date_text;

    }
    public String getUserUid(){
        String Uid = null;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Uid = user.getUid();
        }else{
            //힝
        }
        return Uid;
    }
    public void readData(final String dateKey, final String shot_Day){
        mReference = FirebaseDatabase.getInstance().getReference();
        mReference.child("diary").child(getUserUid()).child(dateKey).addListenerForSingleValueEvent(
                //위에서 갑자기 에러나서 터짐... 뭐지?
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //firebase에 데이터는 제대로 들어가는데 값 가져 올때 여기가 문제라는 소리...
                        //아마 액티비티->프래그먼트로 수정되면서 자바 파일 자체를 변경해서 오류가 난다.
                        //이부분 유심히 다시 볼것.

                        if(dataSnapshot.getValue(DiaryData.class)!=null){
                        //lock selection
                            chk1.setEnabled(false);
                            chk2.setEnabled(false);
                            chk3.setEnabled(false);
                            chk4.setEnabled(false);
                            chk5.setEnabled(false);
                            Toast.makeText(getContext(),"인물선택은 하루 한번만 가능합니다", Toast.LENGTH_SHORT).show();

                        }else{
                            //do nothing
                            chk1.setEnabled(true);
                            chk2.setEnabled(true);
                            chk3.setEnabled(true);
                            chk4.setEnabled(true);
                            chk5.setEnabled(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("err", "getUser:onCancelled", databaseError.toException());
                    }
                }
        );
    }
}
