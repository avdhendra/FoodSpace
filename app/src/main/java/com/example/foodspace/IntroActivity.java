package com.example.foodspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {
Button getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


    }

    public void getStarted(View view) {
        Intent intent=new Intent(getApplicationContext(),com.example.foodspace.PhoneAuth.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=new Intent(getApplicationContext(),com.example.foodspace.PhoneAuth.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }


}