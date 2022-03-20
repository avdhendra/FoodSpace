package com.example.foodspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends BaseActivity{
private TextView login, SignUp;
private TextView forgotPassword;
private ImageView facebook,google;
private TextInputLayout emailLayout,passwordLayout;
private TextInputEditText Email,Password;
//backpress stop
    private static final int TIME_INTERVAL=2000;
    private long backPressed;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
//EditText
    Email=findViewById(R.id.emailEditText);
    Password=findViewById(R.id.passwordEditText);

//TextInputLayout

    emailLayout=findViewById(R.id.textInputEmail);
    passwordLayout=findViewById(R.id.textinputpassword);

//TextView

    forgotPassword=findViewById(R.id.forgotpassword);

//image view
    facebook=findViewById(R.id.facebook);
    google=findViewById(R.id.google);

 //Button

 login=findViewById(R.id.Login);
 SignUp=findViewById(R.id.Signup);


}
    //OnBackpressed is the method of class Appcompat
    public  void onBackPressed(){
//if second click happened within this second
       // then this if statement gonna happen
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

    public void login(View view) {
      logInRegisteredUser();
    }

    private void logInRegisteredUser() {
    if(validateLoginDetails()){
        showProgressDialog("Please Wait...");
        String email= Objects.requireNonNull(Email.getText()).toString().trim();
        String password= Objects.requireNonNull(Password.getText()).toString().trim();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               hideProgressDialog();
                if(task.isSuccessful()){
showErrorSnackBar("You are Logged in Successfully",false);
Intent i=new Intent(MainActivity.this,Home2Activity.class);
startActivity(i);
overridePendingTransition(0,0);
finish();
                }
                else{
                    if(Objects.requireNonNull(task.getException()).getMessage()!=null){
                        showErrorSnackBar(task.getException().getMessage().toString(),true);
                    }

                }
            }
        });

    }
    }

    private boolean validateLoginDetails() {
        if(TextUtils.isEmpty(Objects.requireNonNull(Email.getText()).toString().trim())){

            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_email), true);
            return false;
        }
        else if(TextUtils.isEmpty(Objects.requireNonNull(Email.getText()).toString().trim())){


            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_password), true);
            return false;
        }
        else{
           return true;
        }
    }

    public void Signup(View view) {
        Intent intent=new Intent(getApplicationContext(), com.example.foodspace.SignUp.class);
        startActivity(intent);
        overridePendingTransition(0,0);

    }

    public void forgetPassword(View view) {
    Intent i=new Intent(this,Forgot_password.class);
    startActivity(i);
    finish();
    overridePendingTransition(0,0);
    }
}
/**
 getContext() — returns the Context which is linked to the
                 Activity from which is called,

 getApplicationContext() — returns the Context which is linked to Application
                           which holds all activities running inside it,

 getBaseContext() —is related to ContextWrapper, which
                    is created around existing Context and let us
                    change its behavior. With getBaseContext() we can
                   fetch the existing Context inside ContextWrapper class.

 If you need access to a Context from within another context, you use a
 ContextWrapper. The Context referred to from inside that ContextWrapper is
 accessed via getBaseContext().





 */