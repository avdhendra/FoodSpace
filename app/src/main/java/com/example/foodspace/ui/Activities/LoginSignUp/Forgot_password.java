package com.example.foodspace.ui.Activities.LoginSignUp;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodspace.ui.Activities.BaseActivity;
import com.example.foodspace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Forgot_password extends BaseActivity {
    TextInputEditText Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Email = findViewById(R.id.emailEditText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();

    }

    public void Submit(View view) {
        String email = Objects.requireNonNull(Email.getText()).toString().trim();
        if (email.isEmpty()) {
            showErrorSnackBar("Please Enter Email", true);
        } else {
            showProgressDialog("Please Wait...");
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    hideProgressDialog();
                    if (task.isSuccessful()) {
                        Toast.makeText(Forgot_password.this, "Email send successfully to reset your password", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {

                        showErrorSnackBar(Objects.requireNonNull(task.getException()).getMessage(), true);
                    }
                }
            });
        }
    }
}