package com.example.foodspace;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
String countrycode;
String phoneNumber;
public static final String TAG ="Hi";

private int count=1;
private final int CREDENTIAL_PICKER_REQUEST = 1;
private Button materialButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        edt1=findViewById(R.id.phonenumber);
        materialButton=findViewById(R.id.materialButton);

        edt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count==1)
                {
                    try {
                        phoneSelection();
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                    count++;

                }
            }
        });
        codePicker1=findViewById(R.id.countryCodePicker);
mAuth=FirebaseAuth.getInstance();


    }

    private void phoneSelection() throws IntentSender.SendIntentException {
        HintRequest hintRequest=new HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build();
        CredentialsOptions options=new CredentialsOptions.Builder().forceEnableSaveDialog().build();
        PendingIntent credentialsClient=Credentials.getClient(getApplicationContext(),options).getHintPickerIntent(hintRequest);
        startIntentSenderForResult(credentialsClient.getIntentSender(),CREDENTIAL_PICKER_REQUEST,null,0,0,0,new Bundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK)
        {
            Credential credential=data.getParcelableExtra(Credential.EXTRA_KEY);
            edt1.setText(credential.getId().substring(3));
        }
        else if(requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE){
            Toast.makeText(this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }
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
            checkNumber();

        }
    }

    private void checkNumber() {
        countrycode=codePicker1.getSelectedCountryCodeWithPlus();
        phoneNumber=countrycode+edt1.getText().toString();
        notifyUser();

    }

    private void notifyUser() {
   /*     MaterialAlertDialogBuilder materialAlertDialogBuilder=new MaterialAlertDialogBuilder(this).setMessage("We will be verifying the Phone Number "+phoneNumber+"Is this Ok or Would you life to edit the number ?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ShowOtpActivity();
            }
        }).setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
           dialogInterface.dismiss();
           edt1.setFocusable(true);
            }
        });
        materialAlertDialogBuilder.setCancelable(false);
        materialAlertDialogBuilder.show();*/

        AlertDialog.Builder builder=new AlertDialog.Builder(this).setMessage("We will be verifying the Phone Number "+phoneNumber+" Is this Ok or Would you like to edit the number ?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ShowOtpActivity();
            }
        }).setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                edt1.setFocusable(true);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void ShowOtpActivity() {
        Intent i1=new Intent(PhoneAuth.this,OtpNumber.class);
        i1.putExtra("phone_number",phoneNumber);
        finish();
        startActivity(i1);
    }


}