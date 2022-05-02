package com.example.foodspace.ui.Activities.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodspace.Model.PopularPlaces;
import com.example.foodspace.Model.PopularRestaurant;
import com.example.foodspace.R;
import com.example.foodspace.ui.Activities.Profile.profile;
import com.example.foodspace.ui.Adapters.RecyclerAdapter3;
import com.example.foodspace.ui.Adapters.RecyclerAdapter4;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class Dinning extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private boolean expanded = false;
    private RecyclerView recyclerViewplaceList, recyclerViewRestuarant;
    TextView seemore;
    private List<PopularPlaces> placesItem;
    private List<PopularRestaurant> Restuarantplace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinning);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("expanded")) {
                expanded = savedInstanceState.getBoolean("expanded");
            }
        }
        recyclerViewCategory1();
        recyclerRestaurant1();
        bottomNavigationView = findViewById(R.id.BottomnavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_Dining);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        bottomNavigationView.setItemRippleColor(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);
    }

    private void recyclerViewCategory1() {
        recyclerViewplaceList = findViewById(R.id.placeRecyclerview);
        initRecycler1();
        seemore = findViewById(R.id.seemore1);

        seemore.setOnClickListener(v -> setExpanded1(!expanded));
        setExpanded1(expanded);


    }

    private void setExpanded1(boolean expanded) {

        this.expanded = expanded;
        if (expanded) {
            expand1(recyclerViewplaceList);
            seemore.setText("see less");
            setButtonDrawable1(R.drawable.show_less);

        } else {
            collapse1(recyclerViewplaceList);
            seemore.setText("see more");
            setButtonDrawable1(R.drawable.ic_baseline_keyboard_arrow_down_24);

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outstate) {
        outstate.putBoolean("expanded", expanded);
        super.onSaveInstanceState(outstate);
    }

    private int pixelToDp1() {
        int dp = (int) (116 * getResources().getDisplayMetrics().density);
        return dp;
    }

    private void initRecycler1() {
        placesItem = getItem1(getApplicationContext());

        RecyclerAdapter3 recyclerAdapter3 = new RecyclerAdapter3(placesItem, this);
        recyclerViewplaceList.setAdapter(recyclerAdapter3);
        recyclerAdapter3.notifyDataSetChanged();
        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerViewplaceList.getLayoutManager();
    }

    private List<PopularPlaces> getItem1(Context context) {

        List<PopularPlaces> ret = new ArrayList<PopularPlaces>();
        ret.add(new PopularPlaces("Outdoor", drawableToBitmap1(getDrawable1(context, "outdoortable"))));
        ret.add(new PopularPlaces("Concert", drawableToBitmap1(getDrawable1(context, "guitar"))));
        ret.add(new PopularPlaces("Cafe", drawableToBitmap1(getDrawable1(context, "coffee"))));
        ret.add(new PopularPlaces("Breakfast", drawableToBitmap1(getDrawable1(context, "englishbreakfast"))));
        ret.add(new PopularPlaces("Snacks", drawableToBitmap1(getDrawable1(context, "sandwich"))));
        ret.add(new PopularPlaces("Wifi", drawableToBitmap1(getDrawable1(context, "wifi"))));
        ret.add(new PopularPlaces("Healthy", drawableToBitmap1(getDrawable1(context, "salad"))));
        ret.add(new PopularPlaces("Pure Veg", drawableToBitmap1(getDrawable1(context, "leaf"))));

        return ret;
    }

    public static Drawable getDrawable1(Context context, String name) {
        int resourceId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return AppCompatResources.getDrawable(context, resourceId);
    }

    private void setButtonDrawable1(int imageResource) {
        seemore.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageResource, 0);
    }

    public static Bitmap drawableToBitmap1(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void collapse1(final View view) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(view.getMeasuredHeight(), pixelToDp1()).setDuration(400);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                lp.height = animatedValue;
                view.setLayoutParams(lp);
            }

        });
        valueAnimator.start();
    }

    public void expand1(final View view) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(pixelToDp1(), view.getMeasuredHeight())
                .setDuration(500);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                lp.height = animatedValue;
                view.setLayoutParams(lp);

            }
        });

        valueAnimator.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Dinning.this, Home2Activity.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);
    }

    public void profile(View view) {
        Intent i = new Intent(Dinning.this, profile.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);
    }

    public void locationMap(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Dinning.this, R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.mapbottomsheet, (LinearLayout) findViewById(R.id.bottomsheetmap));
        bottomSheetView.findViewById(R.id.currentlocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(0, 0);

            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetView.getLayoutParams().height = 1500;
        bottomSheetDialog.show();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent selectedActivity = null;
            if (item.getItemId() == R.id.nav_dev) {
                selectedActivity = new Intent(Dinning.this, Home2Activity.class);
            }
            startActivity(selectedActivity);
            overridePendingTransition(0, 0);
            finish();
            return true;
        }
    };

    private void recyclerRestaurant1() {
        recyclerViewRestuarant = findViewById(R.id.popularRestaurant);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewRestuarant.setLayoutManager(layoutManager);
        Restuarantplace = getRestFood();
        RecyclerAdapter4 recyclerAdapter4 = new RecyclerAdapter4(Restuarantplace, this);
        recyclerViewRestuarant.setAdapter(recyclerAdapter4);
    }

    List<PopularRestaurant> getRestFood() {
        List<PopularRestaurant> rest = new ArrayList<PopularRestaurant>();
        rest.add(new PopularRestaurant("4.5", "The Imperial Spice", "500 for one", "North Indian,Canadian", " This is Pure & Non veg restuarant", R.drawable.indian));
        rest.add(new PopularRestaurant("4.2", "Dragon Kin", "400 for one", "Sushi,Tempura,Sukiyaki", " This is Pure veg restuarant", R.drawable.japnese));
        rest.add(new PopularRestaurant("4.4", "Beijing Street", "350 for one", "Hot&Sour Soup,Szechwan Chicken", " This is Pure & Non veg restuarant", R.drawable.chinese1));
        rest.add(new PopularRestaurant("4.6", "Pizza Paradise", "250 for one", "Pizza Margherita,Tomato Pizzas", "This is Pure & Non veg restuarant", R.drawable.pizza5));
        rest.add(new PopularRestaurant("4.8", "Golden Bakery", "700 for one", "Bakery,bagels,buns", " This is Pure & Non veg Bakery ", R.drawable.bakery));
        rest.add(new PopularRestaurant("4.6", "Bello Italiano", "590 for one", "Polenta,Lasagna,Ravioli", " This is Pure & Non veg restuarant", R.drawable.italian));
        rest.add(new PopularRestaurant("4.4", "Los Amigos Restaurante", "450 for one", "Discada,Tacos,Burritos", " This is Pure & Non veg restuarant", R.drawable.mexican));
        rest.add(new PopularRestaurant("4.3", "Kerala Cuisine", "400 for one", "Masala Dosa,Hyderabadi Biryani", " This is Pure & Non veg restuarant", R.drawable.southindian));
        rest.add(new PopularRestaurant("4.2", "Sam Oh Jung", "420 for one", "Korean,Sushi,Beverage", " This is Pure & Non veg restuarant", R.drawable.korean1));
        rest.add(new PopularRestaurant("4.0", "Pizza Hut", "350 for one", "Chicken Popeyes,Waffle Fries", " This is Pure & Non veg restuarant", R.drawable.fastfood));
        rest.add(new PopularRestaurant("4.4", "Chinatown Thai", "350 for one", "Tom Kha Gai,Khao Pad", " This is Pure  ", R.drawable.thai));
        rest.add(new PopularRestaurant("4.7", "Sweet N'Spicy", "410 for one", "Asian,Chinese,Japanese", " This is Pure & Non veg restuarant", R.drawable.as1));

        return rest;


    }
}