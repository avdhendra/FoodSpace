package com.example.foodspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {
Button Signup,Login;
EditText Name,Email,Password,Mobile_number;
TextInputLayout nameInput,emailInput,passwordInput,numberInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //EditText
        Name=findViewById(R.id.NameEditText);
        Email=findViewById(R.id.EmailEditText);
        Password=findViewById(R.id.PasswordEditText);

        //TextInputLayout
        nameInput=findViewById(R.id.NameInput);
        emailInput=findViewById(R.id.PasswordInput);
        passwordInput=findViewById(R.id.PasswordInput);

        //Button
        Signup=findViewById(R.id.Signup);
        Login=findViewById(R.id.Login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

            }
        });


    }
    public void onBackPressed(){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

}