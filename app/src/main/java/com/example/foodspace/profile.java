package com.example.foodspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        NavigationView navigationView=findViewById(R.id.navigationview);
        navigationView.setItemIconTintList(null);
        mAuth=FirebaseAuth.getInstance();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frag=null;
                int itemid= item.getItemId();
                if(itemid==R.id.logout)
                {
                    mAuth.signOut();
startActivity(new Intent(profile.this,PhoneAuth.class));
finish();
overridePendingTransition(0,0);
return true;
                }
                return false;
            }

        });
    }

    public void backward(View view) {

        Intent i=new Intent(profile.this,Home2Activity.class);

        startActivity(i);
        finish();
        overridePendingTransition(0,0);

    }
    public void onBackPressed(){

        Intent i=new Intent(profile.this,Home2Activity.class);


        startActivity(i);
        finish();
        overridePendingTransition(0,0);

    }
}