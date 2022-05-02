package com.example.foodspace.ui.Activities.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodspace.ui.Activities.BaseActivity;
import com.example.foodspace.Firestore.FirestoreClass;
import com.example.foodspace.R;
import com.example.foodspace.FirebaseModels.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class SignUp extends BaseActivity {
    Button Signup;//Button for Signup
    TextInputEditText Name, Email, Password, Mobile_number, ConfirmPassword;

    FirebaseFirestore mFireStore;
    FirebaseAuth mauth;
    String userId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Name = findViewById(R.id.nameEditText);
        Email = findViewById(R.id.EmailEditText);
        Password = findViewById(R.id.PasswordEditText);
        ConfirmPassword = findViewById(R.id.ConfirmPasswordEditText);
        Mobile_number = findViewById(R.id.MobileEditText);
        Signup = findViewById(R.id.Signup);

        mFireStore = FirebaseFirestore.getInstance();//getInstance of Firestore
        mauth = FirebaseAuth.getInstance();//getInstance of Authentication

    }

    //Onclick of SignUp Button
    public void signup(View view) {
        //register the User inside the firebase
        registerUser();
    }

    private void registerUser() {
//Check with validate function if the entries are valid or not
        if (validateRegisterDetails()) {
            //show the progress dialog
            showProgressDialog("Please Wait...");

            String email = Objects.requireNonNull(Email.getText()).toString().trim();
            String password = Objects.requireNonNull(Password.getText()).toString().trim();
            //Create an instance and register a user with email and password
            mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //if registeration task is successfully don
                    if (task.isSuccessful()) {
                        //get the userid of current user register
                        userId = Objects.requireNonNull(mauth.getCurrentUser()).getUid();
                        //create a user object out of the value we pass in the editext and userId
                        User user = new User(userId, "", "", Objects.requireNonNull(Name.getText()).toString().trim(), email, "", Objects.requireNonNull(Mobile_number.getText()).toString().trim(), 0);
                        //pass the required value in the function to store the value of user inside the firestore
                        new FirestoreClass().registerUser(SignUp.this, user);
                    } else {
                        //Hide the progress dialog
                        hideProgressDialog();
                        //If the registering is not successfull then show error message
                        showErrorSnackBar(Objects.requireNonNull(task.getException()).getMessage(), true);
                    }
                }
            });
        }
    }

    /**
     * A function to notify the success result of Firestore entry when the user is registered successfully.
     */

    public void userRegistrationSuccess() {
        hideProgressDialog();

        Toast.makeText(this, "You are registered Successfully", Toast.LENGTH_SHORT).show();
        /**
         * Here the new user registered is automatically signed-in so we just sign-out the user from firebase
         * and send him to Intro Screen for Sign-In
         */
        mauth.signOut();

// Finish the Register Screen
        finish();
        //No Animation while navigation
        overridePendingTransition(0, 0);

    }

    /**
     * A function to validate the entries of a new user.
     */

    private boolean validateRegisterDetails() {
        if (TextUtils.isEmpty(Objects.requireNonNull(Name.getText()).toString().trim())) {

            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_first_name), true);
            return false;
        } else if (TextUtils.isEmpty(Objects.requireNonNull(Email.getText()).toString().trim())) {


            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_email), true);
            return false;
        } else if (TextUtils.isEmpty(Objects.requireNonNull(Password.getText()).toString().trim())) {

            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_password), true);
            return false;
        } else if (TextUtils.isEmpty(Objects.requireNonNull(ConfirmPassword.getText()).toString().trim())) {
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_confirm_password), true);
            return false;
        } else if (TextUtils.isEmpty(Objects.requireNonNull(Mobile_number.getText()).toString().trim())) {
            showErrorSnackBar(
                    getResources().getString(R.string.err_msg_enter_mobile_number),
                    true
            );
            return false;
        }

        if (!Password.getText().toString().trim().equals(ConfirmPassword.getText().toString()
                .trim())) {
            showErrorSnackBar(
                    getResources().getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
            );
            return false;
        } else {
            return true;
        }
    }

    //TextView to go back to login activity onClick
    public void LoginActivity(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

    }
}
