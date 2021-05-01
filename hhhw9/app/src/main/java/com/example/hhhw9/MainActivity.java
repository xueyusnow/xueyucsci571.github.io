package com.example.hhhw9;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Thread background = new Thread() {
        public void run() {
            try {
                // Thread will sleep for 5 seconds
                sleep(2*1000);

                // After 5 seconds redirect to another intent
                Intent i=new Intent(getBaseContext(), FirstScreen.class);
                startActivity(i);

                //Remove activity
                finish();
            } catch (Exception e) {
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        background.start();

    }

}

