package com.example.foodspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpNumber extends AppCompatActivity {
EditText otp;
String verificationId;
String PhoneNumber1;
TextView phonenumber;
Button resendBtn;
String mVerification;
PhoneAuthProvider.ForceResendingToken mResendToken;
ProgressDialog progress;
    CountDownTimer countDownTimer;
FirebaseAuth mAuth;
private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_number);
resendBtn=findViewById(R.id.resendSms);
        phonenumber=findViewById(R.id.phonenumber);
       otp=findViewById(R.id.otpnum);
       phonenumber.setText(String.format("+"+getIntent().getStringExtra("countrycode")+"-"+getIntent().getStringExtra("phone"),getIntent().getStringExtra("phone")));
       verificationId=getIntent().getStringExtra("verification");

        mAuth=FirebaseAuth.getInstance();
       initView();
       startVerify();

    }

    private void startVerify() {
        startPhoneNumberVerification(PhoneNumber1);
        showTimer(60000);
        progress=createProgressDialog("Sending a Verification code",false);
        progress.show();
    }

    private void startPhoneNumberVerification(String phoneNumber1) {
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber1)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void initView() {
PhoneNumber1=getIntent().getStringExtra("phone_number");
phonenumber.setText(PhoneNumber1);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                //   Toast.makeText(PhoneAuth.this,"Otp sent Successfully",Toast.LENGTH_SHORT).show();
             if(progress.isShowing())
             {
                 progress.dismiss();
             }
            String smsCode=credential.getSmsCode();
             if(!smsCode.isEmpty())
             {
                 otp.setText(smsCode);
             }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
if(progress.isShowing())
{
    progress.dismiss();
}
                if (e instanceof FirebaseAuthInvalidCredentialsException ){
                    // Invalid request
                    Log.e("Exception:", "FirebaseAuthInvalidCredentialsException", e);
                    Log.e("=========:", "FirebaseAuthInvalidCredentialsException " + e.getMessage());
                } else if ( e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // The SMS quota for the project has been exceeded
                    Log.e("Exception:", "FirebaseTooManyRequestsException", e);
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

progress.dismiss();
Log.e("OnCodeSent==","onCodeSent"+verificationId);

mVerification=verificationId;
mResendToken=token;

            }
        };
    }

    private void showTimer(long millisecond) {
        CountDownTimer countDownTimer=new CountDownTimer(millisecond,1000) {
            @Override
            public void onTick(long millisUntilFinised) {

                resendBtn.setText("Resend Sms: "+millisUntilFinised/1000);
            }

            @Override
            public void onFinish() {
resendBtn.setText("Resend Sms");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    public void backward(View view) {
        Intent intent=new Intent(OtpNumber.this,PhoneAuth.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }

    public void ResendSms(View view) {
if(mResendToken!=null)
{
    resendVerification(PhoneNumber1.toString());
    showTimer(6000);
progress=createProgressDialog("Sending a Verification code ",false);
progress.show();
}
    }

    private void resendVerification(String Phonenumber1) {

        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(Phonenumber1)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        if(otp.getText().toString().trim().isEmpty())
        {
            Toast.makeText(OtpNumber.this,"Otp is Not Valid",Toast.LENGTH_SHORT).show();
        }
        else{
            if(mVerification!=null)
            {
                mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            if(progress.isShowing())
                            {
                                progress.dismiss();
                            }
                            if(task.getResult().getAdditionalUserInfo().isNewUser())
                            {
                                showNameofUser();
                            }
                            else{
                                showHomeActivity();
                            }
                        }
                        else{
                            if(progress.isShowing())
                            {
                                progress.dismiss();
                            }
                            notifyUserAndRetry("Your Phone Number Verification is Failed.Retry again!");
                        }
                    }
                });

            }
        }

    }

    private void notifyUserAndRetry(String s) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder=new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setMessage(s);
        materialAlertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showLoginActivity();
            }
        });
    }

    private void showLoginActivity() {
        Intent intent=new Intent(this,PhoneAuth.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(0,0);
    }

    private void showHomeActivity() {
        Intent intent=new Intent(this,Home2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);

    }

    private void showNameofUser() {
        Intent intent=new Intent(this,NameOfUser.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);

    }


   ProgressDialog createProgressDialog(String message,Boolean isCancelable){
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(isCancelable);
        return  progressDialog;
   }


    public void verify(View view) {
        String code=otp.getText().toString();
        if(!code.isEmpty() && !mVerification.isEmpty())
        {
            progress=createProgressDialog("Please wait...",false);
            progress.show();
            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerification,code.toString());
            signInWithPhoneAuthCredential(credential);
        }
    }
}