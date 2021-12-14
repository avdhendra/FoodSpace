package com.example.foodspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpNumber extends AppCompatActivity {
EditText otp;
String verificationId;
TextView phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_number);

        phonenumber=findViewById(R.id.phonenumber);
       otp=findViewById(R.id.otpnum);
       phonenumber.setText(String.format("+"+getIntent().getStringExtra("countrycode")+"-"+getIntent().getStringExtra("phone"),getIntent().getStringExtra("phone")));
       verificationId=getIntent().getStringExtra("verification");
    }



    public void backward(View view) {
        Intent intent=new Intent(OtpNumber.this,PhoneAuth.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }

    public void ResendSms(View view) {

    }

    public void verify(View view) {
        if(otp.getText().toString().trim().isEmpty() )
        {
            Toast.makeText(OtpNumber.this,"Otp is Not Valid",Toast.LENGTH_SHORT).show();
        }
        else {
            if(verificationId!=null){
                String code=otp.getText().toString().trim();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(OtpNumber.this,"Otp Verified",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(OtpNumber.this,NameOfUser.class);
                           finish();
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(OtpNumber.this,"otp is Not valid",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}