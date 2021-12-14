package com.example.foodspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class NameOfUser extends AppCompatActivity {
TextInputEditText editText1;
TextInputEditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_of_user);
    editText1=findViewById(R.id.nameUser);
    editText2=findViewById(R.id.tellus);

    }

    public void backward(View view) {
        Intent intent=new Intent(NameOfUser.this,PhoneAuth.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }

    public void Continue(View view) {
Intent intent=new Intent(NameOfUser.this,Home2Activity.class);
finish();
startActivity(intent);
        overridePendingTransition(0,0);
    }
}