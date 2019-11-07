package com.example.nav_when.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.nav_when.LanguageConv;
import com.example.nav_when.MainActivity;
import com.example.nav_when.R;
import com.example.nav_when.WriteDiary;
import com.example.nav_when.settings;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class select_person extends Fragment  {
    private int selected_position;
    private ArrayList<String> chkPerson = new ArrayList<String>();
    private String nameSelect,tagSelect;
    private Button select, q1,q2,q3,q4,q5;
    private CheckBox chk1,chk2,chk3,chk4,chk5;
    private TextView tag1, tag2, tag3, tag4, tag5;
    private ArrayList<CheckBox> mchkbox = new ArrayList<CheckBox>();
    public static select_person newInstance(){
        return new select_person();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_select_person, container, false);

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

        chkPerson.add("person1");
        chkPerson.add("person2");
        chkPerson.add("person3");
        chkPerson.add("person4");
        chkPerson.add("person5");

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
            }
        });


        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Button close = (Button) popupView.findViewById(R.id.close); // PopupWindow 상의 View의 Button 연결
                Button goLink = (Button) popupView.findViewById(R.id.link);
                popupWindow.setContentView(popupView);
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
                popupWindow.setContentView(popupView);
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
                popupWindow.setContentView(popupView);
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
                popupWindow.setContentView(popupView);
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
                popupWindow.setContentView(popupView);
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
                    }
                });
                popupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0,0); // PopupWindow를 View 위에 뿌려 줌. 선언하지 않을 경우, PopupWindow가 보이지 않음


            }

        });
        select = (Button)root.findViewById(R.id.today_done);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mint = new Intent(getActivity(), WriteDiary.class);
                mint.putExtra("person-name", nameSelect);
                mint.putExtra("tag-name",tagSelect);
                startActivity(mint);
            }
        });
        return root;
    }

}
