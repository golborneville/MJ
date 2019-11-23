package com.example.nav_when.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.nav_when.Calendar_v;
import com.example.nav_when.DiaryData;
import com.example.nav_when.FixDiary;
import com.example.nav_when.R;
import com.example.nav_when.ViewDiary;
import com.example.nav_when.decorators.EventDecorator;
import com.example.nav_when.decorators.OneDayDecorator;
import com.example.nav_when.decorators.SaturdayDecorator;
import com.example.nav_when.decorators.SundayDecorator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

//new

public class Calendar_v_2 extends Fragment {
    String time,kcal,menu;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    Cursor cursor;
    MaterialCalendarView materialCalendarView;
    private DatabaseReference mReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.activity_calendar_v,container,false);

        materialCalendarView = (MaterialCalendarView)root.findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);

        String[] result = {"2017,03,18","2017,04,18","2017,05,18","2017,06,18"};

        new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            //이 부분을 통해 날짜를 선택하고 선택한 값을 조정 하는 것입니다.
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                Log.i("Year test", Year + "");
                Log.i("Month test", Month + "");
                Log.i("Day test", Day + "");
                final String shot_Day = Year + ". " + Month + ". " + Day;
                final String dateKey = ""+Year+Month+Day;
                Log.i("shot_Day test", shot_Day + "");
                materialCalendarView.clearSelection();
                readData(dateKey, shot_Day);
            }
        });

        return root;
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
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue(DiaryData.class)!=null){
                            DiaryData data = dataSnapshot.getValue(DiaryData.class);
                            Log.i("hah",data.date);
                            Log.i("hah",data.mail);
                            Log.i("hah",data.text);
                            Intent mint = new Intent(getActivity(), ViewDiary.class);
                            mint.putExtra("text", data.text);
                            mint.putExtra("person", data.person);
                            mint.putExtra("tag", data.tag);
                            mint.putExtra("date", shot_Day);
                            mint.putExtra("dateKey",dateKey);
                            startActivity(mint);
                        }else{
                            Toast.makeText(getContext(), "작성된 일기가 없습니다." , Toast.LENGTH_SHORT).show();
                            Intent mint = new Intent(getActivity(), FixDiary.class);
                            //mint.putExtra("text", data.text);
                            mint.putExtra("person", "no person");
                            mint.putExtra("tag", "no tags");
                            mint.putExtra("date", shot_Day);
                            mint.putExtra("dateKey",dateKey);

                            startActivity(mint);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("err", "getUser:onCancelled", databaseError.toException());
                    }
                }
        );
    }
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result){
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.length ; i ++){
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                dates.add(day);
                calendar.set(year,month-1,dayy);
            }



            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (getActivity().isFinishing()) {
                return;
            }

            materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays, getActivity()));
        }
    }
}