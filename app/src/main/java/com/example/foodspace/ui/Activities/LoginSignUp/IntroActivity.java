package com.example.foodspace.ui.Activities.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foodspace.R;
import com.example.foodspace.ui.Activities.Home.Home2Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class IntroActivity extends AppCompatActivity {
    private FirebaseAuth mAuth; //Authentication object of firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mAuth = FirebaseAuth.getInstance();
    }

    //Onclike of getStarted Button
    public void getStarted(View view) {

        Intent intent = new Intent(IntroActivity.this, PhoneAuth.class);
        startActivity(intent);
        finish();

    }

    /**
     * overrided function of AppCompatActivity class onStart when the app start
     */
    @Override
    protected void onStart() {
        super.onStart();
        //creating the user instance
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            //if user is not created go to PhoneAuth
            startActivity(new Intent(IntroActivity.this, Home2Activity.class));
            finish();
        }

    }

    /**
     * when activity wont get destroyed and it got restarted after it being stop
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        //creating the user instance of FirebaseUser
        FirebaseUser user = mAuth.getCurrentUser();
        //if user is already being logged in
        if (user != null) {
            startActivity(new Intent(IntroActivity.this, Home2Activity.class));
            finish();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
    }
}