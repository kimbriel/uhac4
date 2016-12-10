package com.example.patrick.uhacproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class FlashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent x = new Intent(FlashScreen.this,MainActivity.class);
                startActivity(x);
                finish();
            }
        },1500);
    }

}
