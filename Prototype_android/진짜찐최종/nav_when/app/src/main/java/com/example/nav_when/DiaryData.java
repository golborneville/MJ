package com.example.nav_when;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import org.snu.ids.ha.index.Keyword;
import org.snu.ids.ha.index.KeywordExtractor;
import org.snu.ids.ha.index.KeywordList;
import org.snu.ids.ha.ma.MExpression;
import org.snu.ids.ha.ma.MorphemeAnalyzer;
import org.snu.ids.ha.ma.Sentence;
import org.snu.ids.ha.util.Timer;

@IgnoreExtraProperties
public class DiaryData {
    public String date = " ";
    public String mail = " ";
    public String person = " ";
    public String tag = " ";
    public String text = " ";
    public String extracted_Text = " ";
    public DiaryData(){}

    public DiaryData(String date, String person, String tag, String text, String mail, String extracted_text){
        this.date = date;
        this.person = person;
        this.tag = tag;
        this.text = text;
        this.mail = mail;
        //this.extracted_Text = extract_data(text);
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("date",date);
        result.put("person",person);
        result.put("tag",tag);
        result.put("text",text);
        result.put("mail",mail);
        result.put("extracted_Text", extracted_Text);
        return result;
    }
 public String extract_data(String text) {
        // string to extract keywords
        String strToExtrtKwrd = text;
        // init KeywordExtractor
        KeywordExtractor ke = new KeywordExtractor();
        // extract keywords
        KeywordList kl = ke.extractKeyword(strToExtrtKwrd, true);
        // print result
        for (int i = 0; i < kl.size(); i++) {
            Keyword kwrd = kl.get(i);
            extracted_Text = extracted_Text + (kwrd.getString() + "," + kwrd.getCnt() + "/");
        }
        return extracted_Text;
    }
}

