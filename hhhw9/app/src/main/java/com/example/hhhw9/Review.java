package com.example.hhhw9;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Review extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_review);
        Intent intent = getIntent();
        String date=intent.getStringExtra("date");
        String content=intent.getStringExtra("content");
        String rate=intent.getStringExtra("rate");
        System.out.println(rate);
        String author=intent.getStringExtra("author");
        TextView text1=findViewById(R.id.text1);
        if(!rate.equals("null")&&!rate.equals("")){
            double ratee = Double.parseDouble(rate) / 2;
            text1.setText(ratee + "/5");
        }else
            text1.setText("0/5");
        TextView text2=findViewById(R.id.text2);
        text2.setText("By "+author+" on "+date);
        TextView text3=findViewById(R.id.text3);
        if(!content.equals("null")&&!content.equals("")) {
            text3.setText(content);
        }
    }
}