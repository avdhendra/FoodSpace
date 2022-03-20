package com.example.foodspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AboutYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_you);
    }

    public void AddReview(View view) {
    }

    public void AddRestaurant(View view) {
        Intent i=new Intent(this,Add_Restaurant.class);
        startActivity(i);
        finish();
        overridePendingTransition(0,0);
    }

    public void AddItem(View view) {
        Intent i=new Intent(this,Add_Item.class);
        startActivity(i);
        finish();
        overridePendingTransition(0,0);
    }
}