package com.example.foodspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class SignUp extends BaseActivity {
TextView Signup;
TextInputEditText Name,Email,Password,Mobile_number,ConfirmPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //EditText
        Name=findViewById(R.id.nameEditText);
        Email=findViewById(R.id.EmailEditText);
        Password=findViewById(R.id.PasswordEditText);
ConfirmPassword=findViewById(R.id.ConfirmPasswordEditText);
Mobile_number=findViewById(R.id.MobileEditText);

        //Button
        Signup=findViewById(R.id.Signup);



    }
    public void onBackPressed(){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }

/*    public void signup(View view) {

    }
*/


    public void signup(View view) {
       registerUser();
    }

    private void registerUser() {
        if(validateRegisterDetails()) {
       showProgressDialog("Please Wait...");
String email= Objects.requireNonNull(Email.getText()).toString().trim();
String password= Objects.requireNonNull(Password.getText()).toString().trim();
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){


                            FirebaseUser firebaseUser=task.getResult().getUser();

                        if(firebaseUser != null){
                        User user=new User(firebaseUser.getUid(), Objects.requireNonNull(Name.getText()).toString().trim(),email,"",Integer.parseInt(Objects.requireNonNull(Mobile_number.getText()).toString().trim()),0);
                       new FirestoreClass().registerUser(SignUp.this,user);

                    }
                    }

                    else{
                            hideProgressDialog();
                            showErrorSnackBar(Objects.requireNonNull(task.getException()).getMessage(),true);
                        }

                }

        });
    } }
    void userRegistrationSuccess() {
    hideProgressDialog();

    Toast.makeText(this,"You are registered Successfully",Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
        // Finish the Register Screen
        overridePendingTransition(0,0);
        finish();

    }

    private boolean validateRegisterDetails() {
      if(TextUtils.isEmpty(Objects.requireNonNull(Name.getText()).toString().trim())){

                showErrorSnackBar(getResources().getString(R.string.err_msg_enter_first_name), true);
               return false;
      }
else if(TextUtils.isEmpty(Objects.requireNonNull(Email.getText()).toString().trim())){


                showErrorSnackBar(getResources().getString(R.string.err_msg_enter_email), true);
                return false;
}

     else if(TextUtils.isEmpty(Objects.requireNonNull(Password.getText()).toString().trim()))  {

                showErrorSnackBar(getResources().getString(R.string.err_msg_enter_password), true);
                return false;
            }

     else if( TextUtils.isEmpty(Objects.requireNonNull(ConfirmPassword.getText()).toString().trim())) {
                showErrorSnackBar(getResources().getString(R.string.err_msg_enter_confirm_password), true);
                 return false;
            }

         else if(TextUtils.isEmpty(Objects.requireNonNull(Mobile_number.getText()).toString().trim()) ) {
                showErrorSnackBar(
                        getResources().getString(R.string.err_msg_enter_mobile_number),
                        true
                );
                return false;
            }

            if(!Password.getText().toString().trim().equals(ConfirmPassword.getText().toString()
                    .trim())) {
                showErrorSnackBar(
                        getResources().getString(R.string.err_msg_password_and_confirm_password_mismatch),
                        true
                );
                return false;
            }
            else {
           return true;
            }

        }

    public void LoginActivity(View view) {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(0,0);
    }
}
