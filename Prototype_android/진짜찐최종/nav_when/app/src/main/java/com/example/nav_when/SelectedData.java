package com.example.nav_when;

import java.util.HashMap;
import java.util.Map;

public class SelectedData {
    public int selected = 0;
    public int recommended = 0;
    public SelectedData(){

    }
    public SelectedData(int selected, int recommended){
        this.recommended = recommended;
        this.selected = selected;
    }
    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("recommended",recommended);
        result.put("selected",selected);
        return result;
    }
}
