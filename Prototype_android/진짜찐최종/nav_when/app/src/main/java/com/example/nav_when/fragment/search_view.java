package com.example.nav_when.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nav_when.R;
import com.example.nav_when.personWebview;
import com.example.nav_when.termSearch;

public class search_view extends Fragment {

    private String lt;
    private String urltxt;
    EditText term;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_search_view, container, false);

        Button gogo = (Button)root.findViewById(R.id.goSearch);
        term = (EditText)root.findViewById(R.id.lookup);



        gogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lt = term.getText().toString();
                urltxt = "https://ko.dict.naver.com/#/search?query="+ lt;
                Intent mint = new Intent(getActivity(), termSearch.class);
                mint.putExtra("link-name", urltxt);
                startActivity(mint);
            }
        });
        return root;
    }


}
