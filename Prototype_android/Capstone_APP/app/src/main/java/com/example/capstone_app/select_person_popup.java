package com.example.capstone_app;

import android.app.Activity;
import android.os.Bundle;

public class select_person_popup extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* 인물 이름에 대한 준상세 설명 받아오는 SQLITE 연동 코드 필요
        * 받아오면 setText() 로 poup에 정보 업데이트
        * 정보 제공 외에는 기능 없음
        * */
    }
}
