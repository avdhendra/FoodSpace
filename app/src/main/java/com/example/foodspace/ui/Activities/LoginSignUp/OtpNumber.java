package com.example.foodspace.ui.Activities.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodspace.Constants;
import com.example.foodspace.FirebaseModels.User;
import com.example.foodspace.Firestore.FirestoreClass;
import com.example.foodspace.R;
import com.example.foodspace.ui.Activities.BaseActivity;
import com.example.foodspace.ui.Activities.Home.Home2Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OtpNumber extends BaseActivity {
    EditText otp;
    Constants constants;
    String verificationId, PhoneNumber1, mVerification, userId;

    TextView phonenumber;
    public static String TAG = "MainActivity";
    Button resendBtn;

    PhoneAuthProvider.ForceResendingToken mResendToken;
    ProgressDialog progress;
    CountDownTimer countDownTimer;

    User user;
    FirebaseAuth mAuth;
    FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();
    public String phoneActivity = "phone";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_number);
        resendBtn = findViewById(R.id.resendSms);
        phonenumber = findViewById(R.id.phonenumber);
        otp = findViewById(R.id.otpnum);
        phonenumber.setText(String.format("+" + getIntent().getStringExtra("countrycode") + "-" + getIntent().getStringExtra("phone"), getIntent().getStringExtra("phone")));
        verificationId = getIntent().getStringExtra("verification");
        mAuth = FirebaseAuth.getInstance();
        constants = new Constants();
        initView();
        startVerify();

    }

    private void startVerify() {
        startPhoneNumberVerification(PhoneNumber1);
        showTimer(60000);
        showProgressDialog("Sending a Verification Code");

    }

    private void startPhoneNumberVerification(String phoneNumber1) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber1)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void initView() {
        PhoneNumber1 = getIntent().getStringExtra("phone_number");
        phonenumber.setText(PhoneNumber1);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                //   Toast.makeText(PhoneAuth.this,"Otp sent Successfully",Toast.LENGTH_SHORT).show();
                hideProgressDialog();
                String smsCode = credential.getSmsCode();
                if (smsCode != null) {
                    if (!smsCode.isEmpty()) {
                        otp.setText(smsCode);
                    }
                }

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                hideProgressDialog();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.e("Exception:", "FirebaseAuthInvalidCredentialsException", e);
                    Log.e("=========:", "FirebaseAuthInvalidCredentialsException " + e.getMessage());
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // The SMS quota for the project has been exceeded
                    Log.e("Exception:", "FirebaseTooManyRequestsException", e);
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                hideProgressDialog();
                Log.e("OnCodeSent==", "onCodeSent" + verificationId);

                mVerification = verificationId;
                mResendToken = token;

            }
        };
    }

    private void showTimer(long millisecond) {
        countDownTimer = new CountDownTimer(millisecond, 1000) {
            @Override
            public void onTick(long millisUntilFinised) {

                resendBtn.setText("Resend Sms: " + millisUntilFinised / 1000);
                resendBtn.setTextColor(getResources().getColor(R.color.disablebtn));
                resendBtn.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resendBtn.setText("Resend Sms");
                resendBtn.setEnabled(true);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OtpNumber.this, PhoneAuth.class);
        startActivity(intent);
        finish();

    }


    public void ResendSms(View view) {
        if (mResendToken != null) {
            resendVerification(PhoneNumber1);
            showTimer(6000);
            showProgressDialog("Sending a Verification code");
        }
    }

    private void resendVerification(String Phonenumber1) {

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(Phonenumber1)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        if (otp.getText().toString().trim().isEmpty()) {
            Toast.makeText(OtpNumber.this, "Otp is Not Valid", Toast.LENGTH_SHORT).show();
        } else {
            if (mVerification != null) {
                mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if (Objects.requireNonNull(task.getResult().getAdditionalUserInfo()).isNewUser()) {
                                userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                user = new User(userId, "", "", "", "", "", phonenumber.getText().toString(), 0);
                                FirestoreClass firestoreClass = new FirestoreClass();
                                firestoreClass.registerUser(OtpNumber.this, user);

                            } else {
                                new FirestoreClass().getUserDetails(OtpNumber.this);

                            }
                        } else {
                            hideProgressDialog();
                            notifyUserAndRetry("Your Phone Number Verification is Failed.Retry again!");
                        }
                    }
                });

            }
        }

    }


    private void notifyUserAndRetry(String s) {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setMessage(s);
        materialAlertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showLoginActivity();
            }
        });
    }

    private void showLoginActivity() {
        Intent intent = new Intent(this, PhoneAuth.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void showHomeActivity(User user) {
        hideProgressDialog();
        if (user.profileCompleted == 0) {
            Intent intent = new Intent(this, NameOfUser.class);
            intent.putExtra("Verfication", phoneActivity);
            intent.putExtra(constants.EXTRA_USER_DETAILS, user);
            startActivity(intent);
        } else {
            new FirestoreClass().setSharedPreferenceShow(this);
            Intent intent = new Intent(this, Home2Activity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);


        }
    }

    public void newPhoneUser() {

        mFireStore.collection(constants.user).document(getCurrentUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                hideProgressDialog();
                User user = documentSnapshot.toObject(User.class);
                Intent intent = new Intent(OtpNumber.this, NameOfUser.class);
                intent.putExtra("Verfication", phoneActivity);
                intent.putExtra(constants.EXTRA_USER_DETAILS, user);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgressDialog();
                Log.e(TAG, e.getMessage(), e);
            }
        });


    }

    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = "";
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        }
        return currentUserId;
    }


    public void verify(View view) {
        String code = otp.getText().toString();
        if (!code.isEmpty() && !mVerification.isEmpty()) {

            showProgressDialog("Please Wait...");

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerification, code.toString());
            signInWithPhoneAuthCredential(credential);
        }
    }
}