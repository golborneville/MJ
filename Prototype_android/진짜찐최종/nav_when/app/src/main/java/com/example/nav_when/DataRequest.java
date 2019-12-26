package com.example.nav_when;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataRequest {
    MaterialCalendarView materialCalendarView;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private DatabaseReference databaseReference;
    public String[] daysString = {"2019,12,15","2019,12,15"};
    public static ArrayList<String> daysArray = new ArrayList<String>();
    public DataRequest(){

    }

    public boolean isLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            return true;
        }else{
            return false;
        }
    }
    public void postMostSelected(final String[] selected, final String selectedPerson){
        mReference = FirebaseDatabase.getInstance().getReference();
        for(int i=0;i<selected.length;i++){
            final String tmp = selected[i];
            mReference.child("Selected").child(selected[i]).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue(SelectedData.class)!=null){
                                SelectedData sel = dataSnapshot.getValue(SelectedData.class);
                                int tmpSelected = sel.selected;
                                int tmpRecommended = sel.recommended;
                                if(tmp.equals(selectedPerson)){
                                    post(tmpSelected+1,tmpRecommended+1,tmp);
                                }
                                else{
                                    post(tmpSelected,tmpRecommended+1,tmp);
                                }
                            }else{
                                int tmpSelected = 0;
                                int tmpRecommended = 1;
                                if(tmp.equals(selectedPerson)){
                                    post(tmpSelected+1,tmpRecommended,tmp);
                                }
                                else{
                                    post(tmpSelected,tmpRecommended,tmp);
                                }
                                post(tmpSelected,tmpRecommended,tmp);
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
    public void post(int selected, int recommmended, String person){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        SelectedData select = new SelectedData(selected,recommmended);
        Map<String,Object> childUpdates = new HashMap<>();
        Map<String,Object> postValues = select.toMap();
        childUpdates.put("/Selected/"+person,postValues);
        databaseReference.updateChildren(childUpdates);

    }
    public void readDays(){
        mReference = FirebaseDatabase.getInstance().getReference();
        mReference.child("diary").child(getUserUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> Array = new ArrayList<String>();
                for(DataSnapshot  data_ : dataSnapshot.getChildren()){
                    String str = data_.getKey();
                    Array.add(str);
                    //Toast.makeText(getContext(),str, Toast.LENGTH_SHORT).show();
                    daysArray.add(str);
                }
                Array.add("2020,12,31");
                daysArray.add("2020,12,31");
                //Toast.makeText(getContext(),Array.size()+"gggasdg", Toast.LENGTH_SHORT).show();
                String[] tmptmptmp = Array.toArray(new String[Array.size()]);
                //Toast.makeText(getContext(),tmptmptmp.length+"gggasdg", Toast.LENGTH_SHORT).show();
                daysString = tmptmptmp;
                //여기까지는 들어가는군
                //setDaysString(Array);
                //result = Array.toArray(new String[Array.size()]);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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
    public String[] getDaysString(){
        readDays();

        return daysArray.toArray(new String[daysArray.size()]);
    }
}
