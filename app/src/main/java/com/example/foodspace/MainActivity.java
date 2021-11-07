package com.example.foodspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {
private Button login, SignUp;
private EditText Email,Password;
private TextView forgotPassword;
private ImageView facebook,google;
private TextInputLayout emailLayout,passwordLayout;

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



 SignUp.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
Intent intent=new Intent(getApplicationContext(), com.example.foodspace.SignUp.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

startActivity(intent);
     }
 });


 login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getApplicationContext(), com.example.foodspace.Home2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
});

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