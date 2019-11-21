package com.example.nav_when;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class DiaryData {
    public String date = " ";
    public String text = " ";
    public String mail = " ";
    public String person = " ";
    public String tag = " ";
    public DiaryData(){}

    public DiaryData(String date, String person, String tag, String text, String mail){
        this.date = date;
        this.person = person;
        this.tag = tag;
        this.text = text;
        this.mail = mail;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("date",date);
        result.put("person",person);
        result.put("tag",tag);
        result.put("text",text);
        result.put("mail",mail);
        return result;
    }
}
