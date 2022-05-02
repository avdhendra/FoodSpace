package com.example.foodspace.ui.Activities.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodspace.R;
import com.example.foodspace.ui.Fragments.FragmentAdapter.ViewFragAdapter;
import com.example.foodspace.ui.Activities.Home.Home2Activity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RestaurantMenu extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager2 mViewpager;
    private ViewFragAdapter ViewpagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
        setSupportActionBar(toolbar);
        toolbar = findViewById(R.id.toolbar);
        mViewpager = findViewById(R.id.viewpager2);
        tabLayout = findViewById(R.id.tab_layout);

        ViewpagerAdapter = new ViewFragAdapter(getSupportFragmentManager(), getLifecycle());
        mViewpager.setAdapter(ViewpagerAdapter);
        new TabLayoutMediator(tabLayout, mViewpager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("DELIVERY");
                    break;
                case 1:
                    tab.setText("DINING");
                    break;
                case 2:
                    tab.setText("REVIEWS");
                    break;
            }
        }).attach();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Home2Activity.class);
        startActivity(i);
        finish();
    }
}