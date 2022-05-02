package com.example.foodspace.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodspace.R;
import com.example.foodspace.ui.Activities.Home.Home2Activity;
import com.example.foodspace.ui.Fragments.FragmentAdapter.viewPagerAdapter;
import com.example.foodspace.Model.viewPagerTabModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class expandedLayoutitemactivity extends AppCompatActivity {
    private ViewPager viewPager;
    viewPagerAdapter mainAdapter;
    EditText search;
    private TabLayout tabLayout;
    String title;
    EditText e1;
    private List<viewPagerTabModel> finalAdapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_layoutitemactivity);

        search = findViewById(R.id.search);
        title = "title";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("title");
            search.setText(title);
        }

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setTabRippleColor(null);
        finalAdapterData = new ArrayList<>();
        if (title != null && title.equalsIgnoreCase("Pizza")) {
            finalAdapterData.add(new viewPagerTabModel("allpizza", "All", title));
            finalAdapterData.add(new viewPagerTabModel("vegpizza", "Veg Pizza", title));
            finalAdapterData.add(new viewPagerTabModel("pannerpizza", "Panner Pizza", title));
            finalAdapterData.add(new viewPagerTabModel("margeritapizza", "Margerita Pizza", title));
            finalAdapterData.add(new viewPagerTabModel("tanduripannerpizza", "Tanduri Panner", title));
            finalAdapterData.add(new viewPagerTabModel("chessepizza", "Cheesey Pizza", title));
            finalAdapterData.add(new viewPagerTabModel("mushroompizza", "Mushroom", title));

        } else if (title != null && title.equalsIgnoreCase("panner")) {
            finalAdapterData.add(new viewPagerTabModel("allpanner", "All", title));
            finalAdapterData.add(new viewPagerTabModel("sahipanner", "Sahi Panner", title));
            finalAdapterData.add(new viewPagerTabModel("kadahipanner", "Kadahi Panner", title));
            finalAdapterData.add(new viewPagerTabModel("mutterpanner", "mutter panner", title));
            finalAdapterData.add(new viewPagerTabModel("butterpanner", "Butter Panner", title));
            finalAdapterData.add(new viewPagerTabModel("palakpanner", "Palak Panner", title));
            finalAdapterData.add(new viewPagerTabModel("manchurian", "Panner Manchurian", title));

        } else if (title != null && title.equalsIgnoreCase("momos")) {
            finalAdapterData.add(new viewPagerTabModel("allmomos", "All", title));
            finalAdapterData.add(new viewPagerTabModel("steamveg", "Steam Veg", title));
            finalAdapterData.add(new viewPagerTabModel("fry", "Fry Non Veg", title));
            finalAdapterData.add(new viewPagerTabModel("cheesemomo", "Chese Panner", title));


        } else if (title != null && title.equalsIgnoreCase("Burger")) {
            finalAdapterData.add(new viewPagerTabModel("allburger", "All", title));
            finalAdapterData.add(new viewPagerTabModel("aloo", "Aloo patti", title));
            finalAdapterData.add(new viewPagerTabModel("vegbur", "Veg Burger", title));
            finalAdapterData.add(new viewPagerTabModel("nonvegburger", "Non Veg", title));
            finalAdapterData.add(new viewPagerTabModel("pannerburger", "Panner Burger", title));

        } else if (title != null && title.equalsIgnoreCase("Cake")) {
            finalAdapterData.add(new viewPagerTabModel("allcake", "All", title));
            finalAdapterData.add(new viewPagerTabModel("blackforest", "Black Forest", title));
            finalAdapterData.add(new viewPagerTabModel("pineapple", "PineApple", title));
            finalAdapterData.add(new viewPagerTabModel("chocolate", "Chocolate ", title));
            finalAdapterData.add(new viewPagerTabModel("dark", "Dark Chocolate", title));

        } else if (title != null && title.equalsIgnoreCase("Pasta")) {
            finalAdapterData.add(new viewPagerTabModel("allpasta", "All", title));
            finalAdapterData.add(new viewPagerTabModel("vegpasta", "VegPasta", title));
            finalAdapterData.add(new viewPagerTabModel("nonvegpasta", "NonVegPasta", title));
            finalAdapterData.add(new viewPagerTabModel("sphagetti", "Sphagetti ", title));
            finalAdapterData.add(new viewPagerTabModel("pastameatball", "Manchurian", title));

        } else if (title != null && title.equalsIgnoreCase("Shake")) {
            finalAdapterData.add(new viewPagerTabModel("allshake", "All", title));
            finalAdapterData.add(new viewPagerTabModel("mangoshake", "Mango", title));
            finalAdapterData.add(new viewPagerTabModel("vanillashake", "Vanilla", title));
            finalAdapterData.add(new viewPagerTabModel("milkshake", "Milk", title));
            finalAdapterData.add(new viewPagerTabModel("chocholateshake", "Chocolate", title));
        } else if (title != null && title.equalsIgnoreCase("Biryani")) {
            finalAdapterData.add(new viewPagerTabModel("allbiryani", "All", title));
            finalAdapterData.add(new viewPagerTabModel("hydrabadi", "Hydrabadi", title));
            finalAdapterData.add(new viewPagerTabModel("theri", "Thehari", title));
            finalAdapterData.add(new viewPagerTabModel("lucknowi", "Lucknowi", title));

        } else if (title != null && title.equalsIgnoreCase("Rolls")) {
            finalAdapterData.add(new viewPagerTabModel("allrolls", "All", title));
            finalAdapterData.add(new viewPagerTabModel("bakedrolls", "Baked Rolls", title));
            finalAdapterData.add(new viewPagerTabModel("vegroll", "Veg Roll", title));
            finalAdapterData.add(new viewPagerTabModel("nonvegroll", "NonVegRoll", title));
            finalAdapterData.add(new viewPagerTabModel("friedroll", "FriedRoll", title));
        } else if (title != null && title.equalsIgnoreCase("Nooddles")) {
            finalAdapterData.add(new viewPagerTabModel("allnoodles", "All", title));
            finalAdapterData.add(new viewPagerTabModel("vegnoodles", "Black Forest", title));
            finalAdapterData.add(new viewPagerTabModel("nonvegnoodles", "PineApple", title));
            finalAdapterData.add(new viewPagerTabModel("eggnoodles", "Chocolate ", title));
            finalAdapterData.add(new viewPagerTabModel("chowmein", "Dark Chocolater", title));

        } else if (title != null && title.equalsIgnoreCase("Parathas")) {
            finalAdapterData.add(new viewPagerTabModel("allparatha", "All", title));
            finalAdapterData.add(new viewPagerTabModel("alooparatha", "AlooPartha", title));
            finalAdapterData.add(new viewPagerTabModel("gobiparatha", "GobiPartha", title));
            finalAdapterData.add(new viewPagerTabModel("pannerparatha", "PannerPartha", title));
            finalAdapterData.add(new viewPagerTabModel("palakparatha", "palakParatha", title));
        } else if (title != null && title.equalsIgnoreCase("Chicken")) {
            finalAdapterData.add(new viewPagerTabModel("allchicken", "All", title));
            finalAdapterData.add(new viewPagerTabModel("chickencurry", "Chicken Curry", title));
            finalAdapterData.add(new viewPagerTabModel("tandoorichicken", "Tandoori", title));
            finalAdapterData.add(new viewPagerTabModel("lolipop", "Lolipop", title));
            finalAdapterData.add(new viewPagerTabModel("chickentikka", "Chicken\nTikka", title));
        } else if (title != null && title.equalsIgnoreCase("Fries")) {
            finalAdapterData.add(new viewPagerTabModel("allfries", "All", title));
            finalAdapterData.add(new viewPagerTabModel("potatofries", "PotatoFries", title));
            finalAdapterData.add(new viewPagerTabModel("nonvegfries", "nonVeg\nFries", title));
            finalAdapterData.add(new viewPagerTabModel("pannerfries", "PannerFries", title));

        } else if (title != null && title.equalsIgnoreCase("Subways")) {
            finalAdapterData.add(new viewPagerTabModel("vegsubway", "All", title));
            finalAdapterData.add(new viewPagerTabModel("nonvegsubway", "NonVeg\nSubways", title));


        }
        mainAdapter = new viewPagerAdapter(getSupportFragmentManager(), expandedLayoutitemactivity.this, finalAdapterData.size(), finalAdapterData);
        viewPager.setAdapter(mainAdapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mainAdapter.getTabView(i));
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    ImageView imageView = view.findViewById(R.id.item_icon);
                    imageView.setImageAlpha(0xFF);
                    TextView textView = view.findViewById(R.id.item_name);
                    textView.setTextColor(getResources().getColor(R.color.black));

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    ImageView imageView = view.findViewById(R.id.item_icon);
                    imageView.setImageAlpha(0x3F);
                    TextView textView = view.findViewById(R.id.item_name);
                    textView.setTextColor(getResources().getColor(R.color.gray_1));


                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(expandedLayoutitemactivity.this, Home2Activity.class);


        startActivity(i);
        finish();
        overridePendingTransition(0, 0);

    }
}