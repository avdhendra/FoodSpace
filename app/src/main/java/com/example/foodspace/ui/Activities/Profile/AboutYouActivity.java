package com.example.foodspace.ui.Activities.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodspace.Constants;
import com.example.foodspace.R;
import com.google.android.material.appbar.MaterialToolbar;

public class AboutYouActivity extends AppCompatActivity {
    Constants constants;
    TextView name, email, phonenumber;
    CircleImageView userProfileImage;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_you);
        name = findViewById(R.id.tv_name);
        email = findViewById(R.id.tv_email);
        phonenumber = findViewById(R.id.tv_mobile_number);
        constants = new Constants();
        userProfileImage = findViewById(R.id.iv_user_photo);
        toolbar=findViewById(R.id.toolbar_settings_activity);
        setPreferenceValue();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i =new Intent(AboutYouActivity.this,profile.class);
        startActivity(i);
        finish();

    }

    private void setPreferenceValue() {
        SharedPreferences sharedPreferences = getSharedPreferences(constants.FoodSpace_Preference, Context.MODE_PRIVATE);
        String namePref = sharedPreferences.getString(constants.LOGGED_IN_USERNAME, "");
        String emailPref = sharedPreferences.getString(constants.LOGGED_IN_EMAIL, "");
        String phonePref = sharedPreferences.getString(constants.LOGGED_IN_PHONE, "");
        String imagePref = sharedPreferences.getString(constants.USER_PROFILE_IMAGE, "");
        if (!namePref.equals("") && !emailPref.equals("") && !phonePref.equals("")) {
            name.setText(namePref);
            email.setText(emailPref);
            phonenumber.setText(phonePref);
            Glide.with(AboutYouActivity.this).load(imagePref).centerCrop().placeholder(R.drawable.defaultavatar).into(userProfileImage);


        }
    }

    public void AddReview(View view) {
    }

    public void AddRestaurant(View view) {
        Intent i = new Intent(this, Add_Restaurant.class);
        startActivity(i);
        finish();

    }

    public void AddItem(View view) {
        Intent i = new Intent(this, Add_Item.class);
        startActivity(i);
        finish();

    }
}