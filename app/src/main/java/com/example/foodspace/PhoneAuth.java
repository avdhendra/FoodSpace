package com.example.foodspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class PhoneAuth extends AppCompatActivity {
EditText edt1;
    private static final int TIME_INTERVAL=2000;
    private long backPressed;
private FirebaseAuth mAuth;
private CountryCodePicker codePicker1;
public static final String TAG ="Hi";
private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        edt1=findViewById(R.id.phonenumber);
        codePicker1=findViewById(R.id.countryCodePicker);
mAuth=FirebaseAuth.getInstance();


    }

    public void emailActivity(View view) {
        Intent i1=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i1);
        finish();
        overridePendingTransition(0,0);
    }

   @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null)
        {
            startActivity(new Intent(PhoneAuth.this,Home2Activity.class));
            finish();
            overridePendingTransition(0,0);
        }
    }

    @Override
    public void onBackPressed() {
        if(backPressed+TIME_INTERVAL>System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else{
            Toast.makeText(getBaseContext(),"Press Back again to Exit",Toast.LENGTH_SHORT).show();
        }
        //this when we click after 2 second then backpressed is match with system time
        backPressed=System.currentTimeMillis();
    }

    public void sendOtp(View view) {
        if(edt1.getText().toString().trim().isEmpty())
        {
            Toast.makeText(PhoneAuth.this,"Please Enter the Valid Phone Number",Toast.LENGTH_LONG).show();

        }else if (edt1.getText().toString().trim().length()!=10)
        {
            Toast.makeText(PhoneAuth.this,"Invalid Phone Number",Toast.LENGTH_LONG).show();

        }
        else {
            otpSend();
        }
    }

    private void otpSend() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
             //   Toast.makeText(PhoneAuth.this,"Otp sent Successfully",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
Toast.makeText(PhoneAuth.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
Intent i1=new Intent(PhoneAuth.this,OtpNumber.class);
i1.putExtra("phone",edt1.getText().toString().trim());
i1.putExtra("countrycode",codePicker1.getSelectedCountryCode());
i1.putExtra("verification",verificationId);
finish();
startActivity(i1);


            }
        };

        Log.d(TAG,codePicker1.getSelectedCountryCode()+""+edt1.getText().toString().trim());
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+"+codePicker1.getSelectedCountryCode()+""+edt1.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}