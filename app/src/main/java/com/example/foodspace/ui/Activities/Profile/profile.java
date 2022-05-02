package com.example.foodspace.ui.Activities.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodspace.Constants;
import com.example.foodspace.R;
import com.example.foodspace.Model.settingItem1;
import com.example.foodspace.ui.Activities.Home.Home2Activity;
import com.example.foodspace.ui.Activities.LoginSignUp.PhoneAuth;
import com.example.foodspace.ui.Activities.Menu.RestaurantMenu;
import com.example.foodspace.ui.Adapters.RecyclerAdapter;
import com.example.foodspace.ui.Adapters.RecyclerAdapter5;
import com.example.foodspace.ui.Adapters.RecyclerAdapter6;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {
    private List<settingItem1> settingItem, settingItem2;
    private RecyclerView recyclerViewSetting1, recyclerViewSetting2;
    private RecyclerAdapter5.RecyclerViewonClickListener1 listener;
    private RecyclerAdapter6.RecyclerViewClickListener listener2;
    private TextView viewActivity, username, useremail;
    public Constants constants = new Constants();
    public CircleImageView userProfileImage;
    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recyclerViewSetting1 = findViewById(R.id.recyclerView1);
        recyclerViewSetting2 = findViewById(R.id.recyclerView2);
        viewActivity = findViewById(R.id.viewActivity);
        useremail = findViewById(R.id.useremail);
        username = findViewById(R.id.username);
        userProfileImage = findViewById(R.id.profileImage);
toolbar=findViewById(R.id.profileNavigation);
        dosharedPreference();
        profileImageShow();
        RecyclerSetting1();
        RecyclerSetting2();
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
        Intent intent=new Intent(profile.this,Home2Activity.class);
        startActivity(intent);
        finish();

    }



    private void profileImageShow() {
        SharedPreferences sharedPreferences = getSharedPreferences(constants.FoodSpace_Preference, Context.MODE_PRIVATE);
        String ImagePref = sharedPreferences.getString(constants.USER_PROFILE_IMAGE, "");
        Glide.with(profile.this).load(ImagePref).centerCrop().placeholder(R.drawable.defaultavatar).into(userProfileImage);

    }

    private void dosharedPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences(constants.FoodSpace_Preference, Context.MODE_PRIVATE);
        String namePref = sharedPreferences.getString(constants.LOGGED_IN_USERNAME, "");
        String emailPref = sharedPreferences.getString(constants.LOGGED_IN_EMAIL, "");

        if (!namePref.equals("") && !emailPref.equals("")) {
            username.setText(namePref);
            useremail.setText(emailPref);

        }

    }


    private void RecyclerSetting1() {
        settingItem = getItem();
        setonClickListener();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewSetting1.setLayoutManager(layoutManager);
        RecyclerAdapter5 recyclerAdapter5 = new RecyclerAdapter5(this, settingItem, listener);
        recyclerViewSetting1.setAdapter(recyclerAdapter5);
        recyclerAdapter5.notifyDataSetChanged();
    }

    private void setonClickListener() {
        listener = new RecyclerAdapter5.RecyclerViewonClickListener1(){
            @Override
            public void onClick(View v, int postion) {
                if (settingItem.get(postion).getTitle().equalsIgnoreCase("Bookmarks")) {
                    Intent i = new Intent(getApplicationContext(), Bookmarks.class);
                    startActivity(i);
                    finish();

                }
            }
        };
    }

    private List<settingItem1> getItem() {
        List<settingItem1> ret = new ArrayList<settingItem1>();
        ret.add(new settingItem1("Bookmarks", R.drawable.ic_baseline_bookmark_border_24));
        ret.add(new settingItem1("Notification", R.drawable.ic_baseline_notifications_none_24));
        ret.add(new settingItem1("Settings", R.drawable.ic_baseline_settings_24));
        ret.add(new settingItem1("Payment", R.drawable.ic_baseline_payment_24));
        return ret;
    }


    private void RecyclerSetting2() {
        settingItem2 = getItem2();
        setonClickListener2();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewSetting2.setLayoutManager(layoutManager);
        RecyclerAdapter6 recyclerAdapter6 = new RecyclerAdapter6(this, settingItem2, listener2);
        recyclerViewSetting2.setAdapter(recyclerAdapter6);
        recyclerAdapter6.notifyDataSetChanged();
    }

    private void setonClickListener2() {
        listener2 = new RecyclerAdapter6.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int postion) {
                if (settingItem2.get(postion).getTitle().equalsIgnoreCase("About")) {
                    Intent i = new Intent(getApplicationContext(), RestaurantMenu.class);
                    startActivity(i);
                    finish();
                }
                if (settingItem2.get(postion).getTitle().equalsIgnoreCase("Logout")) {
                    mAuth.signOut();
                    Intent i = new Intent(profile.this, PhoneAuth.class);
                    startActivity(i);
                    finish();

                }
            }
        };
    }

    private List<settingItem1> getItem2() {
        List<settingItem1> setting = new ArrayList<settingItem1>();
        setting.add(new settingItem1("Your Rating", R.drawable.ic_baseline_star_outline_24));
        setting.add(new settingItem1("Favorite Order", R.drawable.ic_baseline_favorite_border_24));
        setting.add(new settingItem1("Online Order Help", R.drawable.ic_baseline_support_agent_24));
        setting.add(new settingItem1("About", R.drawable.ic_icons8_info));
        setting.add(new settingItem1("Logout", R.drawable.ic_baseline_logout_24));
        return setting;
    }




    public void ShowActivity(View view) {
        Intent i = new Intent(this, AboutYouActivity.class);
        startActivity(i);
        finish();

    }

}