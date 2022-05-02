package com.example.foodspace.ui.Activities.LoginSignUp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodspace.Constants;
import com.example.foodspace.FirebaseModels.User;
import com.example.foodspace.Firestore.FirestoreClass;
import com.example.foodspace.ui.Activities.BaseActivity;
import com.example.foodspace.R;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class PhoneAuth extends BaseActivity {
    private EditText phonenumber;
    private static final int TIME_INTERVAL = 2000;
    private long backPressed;
    private FirebaseAuth mAuth;
    private CountryCodePicker codePicker1;
    private String countrycode;
    String phoneNumber;
    private FirebaseFirestore mFireStore;
    String userId;
    Constants constants;
    User user;
    public static final String TAG = "Hi";

    private int count = 1;
    private final int CREDENTIAL_PICKER_REQUEST = 1;
    private TextView materialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        phonenumber = findViewById(R.id.phonenumber);
        materialButton = findViewById(R.id.materialButton);
        constants = new Constants();
        phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 1) {
                    try {
                        phoneSelection();
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                    count++;

                }
            }
        });
        codePicker1 = findViewById(R.id.countryCodePicker);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();


    }


    private void phoneSelection() throws IntentSender.SendIntentException {
        HintRequest hintRequest = new HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build();
        CredentialsOptions options = new CredentialsOptions.Builder().forceEnableSaveDialog().build();
        PendingIntent credentialsClient = Credentials.getClient(getApplicationContext(), options).getHintPickerIntent(hintRequest);
        startIntentSenderForResult(credentialsClient.getIntentSender(), CREDENTIAL_PICKER_REQUEST, null, 0, 0, 0, new Bundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                phonenumber.setText(credential.getId().substring(3));
            }

        } else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE) {
            Toast.makeText(this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }
    }

    public void emailActivity(View view) {
        Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i1);
        finish();

    }


    @Override
    public void onBackPressed() {
        if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press Back again to Exit", Toast.LENGTH_SHORT).show();
        }
        //this when we click after 2 second then backpressed is match with system time
        backPressed = System.currentTimeMillis();
    }

    public void sendOtp(View view) {
        if (TextUtils.isEmpty(Objects.requireNonNull(phonenumber.getText()).toString().trim())) {
            showErrorSnackBar("Please Enter the Valid Phone Number", true);


        } else if (phonenumber.getText().toString().trim().length() != 10) {

            showErrorSnackBar("Invalid Phone Number", true);

        } else {
            registerNumber();


        }
    }

    private void registerNumber() {

        checkNumber();

    }

    public void checkNumber() {

        countrycode = codePicker1.getSelectedCountryCodeWithPlus();
        phoneNumber = countrycode + phonenumber.getText().toString();
        notifyUser();

    }

    private void notifyUser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("We will be verifying the Phone Number " + phoneNumber + " Is this Ok or Would you like to edit the number ?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ShowOtpActivity();
            }
        }).setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                phonenumber.setFocusable(true);
            }
        });

        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorSnackBarSuccess));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorSnackBarSuccess));
                //dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.black));
            }
        });
        dialog.show();


    }

    public void ShowOtpActivity() {
        Intent i1 = new Intent(PhoneAuth.this, OtpNumber.class);
        i1.putExtra("phone_number", phoneNumber);

        startActivity(i1);
        finish();
    }


}