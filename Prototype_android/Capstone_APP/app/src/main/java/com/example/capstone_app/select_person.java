package com.example.capstone_app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.PopupWindow;
import android.content.Context;
import android.view.LayoutInflater;

public class select_person extends Activity {
    private Context mContext;
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    private Activity mActivity;

    String PersonName;

    CheckBox chk1 = (CheckBox) findViewById(R.id.today_person1);
    CheckBox chk2 = (CheckBox) findViewById(R.id.today_person2);
    CheckBox chk3 = (CheckBox) findViewById(R.id.today_person3);
    CheckBox chk4 = (CheckBox) findViewById(R.id.today_person4);
    CheckBox chk5 = (CheckBox) findViewById(R.id.today_person5);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_person);


        TextView txt1 = (TextView)findViewById(R.id.today_person1_Tview);
        TextView txt2 = (TextView)findViewById(R.id.today_person2_Tview);
        TextView txt3 = (TextView)findViewById(R.id.today_person3_Tview);
        TextView txt4 = (TextView)findViewById(R.id.today_person4_Tview);
        TextView txt5 = (TextView)findViewById(R.id.today_person5_Tview);


        //?
        Button btn1 = (Button)findViewById(R.id.today_person1_btn);
        Button btn2 = (Button)findViewById(R.id.today_person2_btn);
        Button btn3 = (Button)findViewById(R.id.today_person3_btn);
        Button btn4 = (Button)findViewById(R.id.today_person4_btn);
        Button btn5 = (Button)findViewById(R.id.today_person5_btn);

        Button done = (Button)findViewById(R.id.today_done); //선택완료


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                View person_popup = inflater.inflate(R.layout.select_person_popup,null);
                mPopupWindow = new PopupWindow(person_popup, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                Button OK_pop = (Button)findViewById(R.id.OK_popup_btn);

                OK_pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });

                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                View person_popup = inflater.inflate(R.layout.select_person_popup,null);
                mPopupWindow = new PopupWindow(person_popup, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                Button OK_pop = (Button)findViewById(R.id.OK_popup_btn);

                OK_pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });

                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                View person_popup = inflater.inflate(R.layout.select_person_popup,null);
                mPopupWindow = new PopupWindow(person_popup, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                Button OK_pop = (Button)findViewById(R.id.OK_popup_btn);

                OK_pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });

                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                View person_popup = inflater.inflate(R.layout.select_person_popup,null);
                mPopupWindow = new PopupWindow(person_popup, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                Button OK_pop = (Button)findViewById(R.id.OK_popup_btn);

                OK_pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });

                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                View person_popup = inflater.inflate(R.layout.select_person_popup,null);
                mPopupWindow = new PopupWindow(person_popup, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                Button OK_pop = (Button)findViewById(R.id.OK_popup_btn);

                OK_pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });

                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
            }
        });

        //선택완료->2.일기작성_일기작성(2) 으로 인물 정보 넘기기
        //NEED IMPLEMENTATION
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chk1.isChecked()){
                    PersonName = chk1.getText().toString();
                }
                else if(chk2.isChecked()){
                    PersonName = chk2.getText().toString();
                }
                else if(chk3.isChecked()){
                    PersonName = chk3.getText().toString();
                }
                else if(chk4.isChecked()){
                    PersonName = chk4.getText().toString();
                }else if(chk5.isChecked()){
                    PersonName = chk5.getText().toString();
                }

            //선택한 인물 이름을 가지고 일기 작성_(2) 로 넘어 가는 파트 구현 필요

            }
        });
    }

}
