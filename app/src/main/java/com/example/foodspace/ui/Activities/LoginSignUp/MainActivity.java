package com.example.foodspace.ui.Activities.LoginSignUp;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodspace.Constants;
import com.example.foodspace.FirebaseModels.User;
import com.example.foodspace.Firestore.FirestoreClass;
import com.example.foodspace.ui.Activities.BaseActivity;
import com.example.foodspace.R;
import com.example.foodspace.ui.Activities.Home.Home2Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.util.Objects;

public class MainActivity extends BaseActivity {

    private ImageView facebook, google;
    public String emailActivity = "email";
    private TextInputEditText Email, Password;
    //backpress stop
    private static final int TIME_INTERVAL = 2000;
    private long backPressed;
    Constants constants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
//EditText
        Email = findViewById(R.id.emailEditText);
        Password = findViewById(R.id.passwordEditText);


//image view

        facebook = findViewById(R.id.facebook);
        google = findViewById(R.id.google);


        constants = new Constants();
    }

    //OnBackpressed is the method of class Appcompat
    public void onBackPressed() {
//if second click happened within this second
        // then this if statement gonna happen
        if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press Back again to Exit", Toast.LENGTH_SHORT).show();
        }
        //this when we click after 2 second then backpressed is match with system time
        backPressed = System.currentTimeMillis();
    }

    //Login Button Onclick
    public void login(View view) {
        logInRegisteredUser();
    }

    private void logInRegisteredUser() {
        //Check with validate function if the entries are valid or not
        if (validateLoginDetails()) {

            //show the progress dialog
            showProgressDialog("Please Wait...");

            //Gett the text from editText and trim the space
            String email = Objects.requireNonNull(Email.getText()).toString().trim();
            String password = Objects.requireNonNull(Password.getText()).toString().trim();
            //Log-In firebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //if the Signin with given email and password task is successfulyy
                    if (task.isSuccessful()) {
                        //show SnackBar
                        showErrorSnackBar("You are Logged in Successfully", false);
                        //get User details from the firestore
                        new FirestoreClass().getUserDetails(MainActivity.this);
                    } else {
                        //Hide the progress dialog is task is not success
                        hideProgressDialog();
                        //show the error message if task completion failed
                        if (Objects.requireNonNull(task.getException()).getMessage() != null) {
                            showErrorSnackBar(task.getException().getMessage(), true);
                        }

                    }
                }
            });

        }
    }

    private boolean validateLoginDetails() {
        if (TextUtils.isEmpty(Objects.requireNonNull(Email.getText()).toString().trim())) {

            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_email), true);
            return false;
        } else if (TextUtils.isEmpty(Objects.requireNonNull(Email.getText()).toString().trim())) {


            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_password), true);
            return false;
        } else {
            return true;
        }
    }

    //Lauch the SignUp activity when user click on the button
    public void Signup(View view) {
        Intent intent = new Intent(getApplicationContext(), com.example.foodspace.ui.Activities.LoginSignUp.SignUp.class);
        startActivity(intent);

        overridePendingTransition(0, 0);

    }

    //Lauch the forgot password screen when user click on forget password textview
    public void forgetPassword(View view) {
        Intent i = new Intent(this, Forgot_password.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);
    }

    /**
     * A function to notify user that logged in success and get the user details from the FireStore database after authentication.
     */
    public void userLoggedInSuccess(User user) {
        //Hide the progress dialog
        hideProgressDialog();
        //
        if (user.profileCompleted == 0) {
            //If the user profile is incomplete then lauch the user Profile
            Intent intent = new Intent(this, NameOfUser.class);
            intent.putExtra("Verfication", emailActivity);
            intent.putExtra(constants.EXTRA_USER_DETAILS, user);
            startActivity(intent);

        } else {
            //if user profile is completed then redirect the user to dashboard
            new FirestoreClass().setSharedPreferenceShow(this);
            Intent intent = new Intent(this, Home2Activity.class);
            startActivity(intent);


        }
        finish();
        overridePendingTransition(0, 0);

    }
}
/**
 * getContext() — returns the Context which is linked to the
 * Activity from which is called,
 * <p>
 * getApplicationContext() — returns the Context which is linked to Application
 * which holds all activities running inside it,
 * <p>
 * getBaseContext() —is related to ContextWrapper, which
 * is created around existing Context and let us
 * change its behavior. With getBaseContext() we can
 * fetch the existing Context inside ContextWrapper class.
 * <p>
 * If you need access to a Context from within another context, you use a
 * ContextWrapper. The Context referred to from inside that ContextWrapper is
 * accessed via getBaseContext().
 */