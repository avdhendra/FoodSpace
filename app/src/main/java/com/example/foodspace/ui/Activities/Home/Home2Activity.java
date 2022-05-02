package com.example.foodspace.ui.Activities.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.foodspace.Constants;
import com.example.foodspace.DAO.CategoriesDao;
import com.example.foodspace.Firestore.FirestoreClass;

import com.example.foodspace.Model.PopularFood;
import com.example.foodspace.Model.PopularFoodItem;
import com.example.foodspace.R;
import com.example.foodspace.ui.Activities.BaseActivity;
import com.example.foodspace.ui.Activities.expandedLayoutitemactivity;
import com.example.foodspace.ui.Activities.Profile.profile;
import com.example.foodspace.ui.Adapters.RecyclerAdapter;
import com.example.foodspace.ui.Adapters.RecyclerAdapter2;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Home2Activity extends BaseActivity {

    private RecyclerView recyclerViewCategoryList, recyclerViewRestuarant;
    TextView seemore;
    private BottomNavigationView bottomNavigationView;
    private List<PopularFoodItem> foodItems;
    private List<PopularFood> Restuarant;
    private FloatingActionButton floatingActionButton;
    private boolean expanded = false;
    RecyclerAdapter recyclerAdapter;
    Constants constants;
    String TAG="Activity";
    CategoriesDao dao;
    CircleImageView userProfile;
    private RecyclerAdapter.RecyclerViewClickListener listener;
    ArrayList<PopularFoodItem>popularFoodItemArrayList=new ArrayList<>();
    ShimmerFrameLayout shimmerFrameLayoutRec1,shimmerFrameLayoutRec2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("expanded")) {
                expanded = savedInstanceState.getBoolean("expanded");
            }
        }
        shimmerFrameLayoutRec1=findViewById(R.id.shimmer_layout);

        constants = new Constants();
        dao=new CategoriesDao();
        userProfile = findViewById(R.id.userProfile);
        recyclerViewCategory();
        recyclerRestaurant();
        profileImageShow();

        bottomNavigationView = findViewById(R.id.BottomnavigationView);

        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);
        bottomNavigationView.setItemRippleColor(null);
        bottomNavigationView.getMenu().getItem(0).setEnabled(false);

    }

    private void profileImageShow() {


        SharedPreferences sharedPreferences = getSharedPreferences(constants.FoodSpace_Preference, Context.MODE_PRIVATE);
        String ImagePref = sharedPreferences.getString(constants.USER_PROFILE_IMAGE, "");
        Glide.with(Home2Activity.this).load(ImagePref).placeholder(R.drawable.defaultavatar).into(userProfile);

    }


    private void recyclerViewCategory() {
        recyclerViewCategoryList = findViewById(R.id.recyclerView1);

        initRecycler();
        seemore = findViewById(R.id.seemore);

        seemore.setOnClickListener(v -> setExpanded(!expanded));
        setExpanded(expanded);


    }

    private void setExpanded(boolean expanded) {

        this.expanded = expanded;
        if (expanded) {
            expand(recyclerViewCategoryList);
            seemore.setText("see less");
            setButtonDrawable(R.drawable.show_less);

        } else {
            collapse(recyclerViewCategoryList);
            seemore.setText("see more");
            setButtonDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24);

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outstate) {
        outstate.putBoolean("expanded", expanded);
        super.onSaveInstanceState(outstate);
    }

    private int pixelToDp() {
        int dp = (int) (216 * getResources().getDisplayMetrics().density);
        return dp;
    }

    private void initRecycler() {
       /* foodItems = getItem(getApplicationContext());*/
        setonClickListener();
        dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data:snapshot.getChildren()){
                    PopularFoodItem popularFoodItem=new PopularFoodItem();
                    popularFoodItem.setBitmap(Objects.requireNonNull(data.child("bitmap").getValue()).toString());
                    popularFoodItem.setTitle(Objects.requireNonNull(data.child("title").getValue()).toString());
                    popularFoodItemArrayList.add(popularFoodItem);
                }
                recyclerViewCategoryList.setVisibility(View.VISIBLE);
                shimmerFrameLayoutRec1.stopShimmer();
                shimmerFrameLayoutRec1.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerAdapter=new RecyclerAdapter(this,listener,popularFoodItemArrayList);
        recyclerViewCategoryList.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
/*        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerViewCategoryList.getLayoutManager();*/
     /*   FirebaseRecyclerOptions<PopularFoodItem>options=new FirebaseRecyclerOptions.Builder<PopularFoodItem>().setIndexedQuery().setQuery(mbase,PopularFoodItem.class).build();
        *//*RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, foodItems, listener);*//*

        recyclerAdapter.notifyDataSetChanged();*/

    }

    private void setonClickListener() {
        listener = new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int postion) {
                Intent i = new Intent(getApplicationContext(), expandedLayoutitemactivity.class);
                i.putExtra("title", popularFoodItemArrayList.get(postion).getTitle());
                startActivity(i);
                finish();

            }
        };
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }*/

    //food item in expandable layout
   /* private List<PopularFoodItem> getItem(Context context) {

        List<PopularFoodItem> ret = new ArrayList<PopularFoodItem>();
        ret.add(new PopularFoodItem("Biryani", drawableToBitmap(getDrawable(context, "image1"))));
        ret.add(new PopularFoodItem("Burger", drawableToBitmap(getDrawable(context, "image2"))));
        ret.add(new PopularFoodItem("Cake", drawableToBitmap(getDrawable(context, "image3"))));
        ret.add(new PopularFoodItem("Chicken", drawableToBitmap(getDrawable(context, "image4"))));
        ret.add(new PopularFoodItem("Fries", drawableToBitmap(getDrawable(context, "image5"))));
        ret.add(new PopularFoodItem("Momos", drawableToBitmap(getDrawable(context, "image6"))));
        ret.add(new PopularFoodItem("Noodles", drawableToBitmap(getDrawable(context, "image7"))));
        ret.add(new PopularFoodItem("Panner", drawableToBitmap(getDrawable(context, "image8"))));
        ret.add(new PopularFoodItem("Parathas", drawableToBitmap(getDrawable(context, "image9"))));
        ret.add(new PopularFoodItem("Pasta", drawableToBitmap(getDrawable(context, "image10"))));
        ret.add(new PopularFoodItem("Rolls", drawableToBitmap(getDrawable(context, "image12"))));
        ret.add(new PopularFoodItem("Subways", drawableToBitmap(getDrawable(context, "image13"))));
        ret.add(new PopularFoodItem("Shake", drawableToBitmap(getDrawable(context, "image14"))));
        ret.add(new PopularFoodItem("Pizza", drawableToBitmap(getDrawable(context, "pizza1"))));

        return ret;
    }*/


    public static Drawable getDrawable(Context context, String name) {
        int resourceId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return AppCompatResources.getDrawable(context, resourceId);
    }

    private void setButtonDrawable(int imageResource) {
        seemore.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageResource, 0);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void collapse(final View view) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(view.getMeasuredHeight(), pixelToDp()).setDuration(400);
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

    public void expand(final View view) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(pixelToDp(), view.getMeasuredHeight())
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

    //food item in resturant
    private void recyclerRestaurant() {
        recyclerViewRestuarant = findViewById(R.id.resturantRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewRestuarant.setLayoutManager(layoutManager);
        Restuarant = getRestFood();
        RecyclerAdapter2 recyclerAdapter2 = new RecyclerAdapter2(this, Restuarant);
        recyclerViewRestuarant.setAdapter(recyclerAdapter2);
    }

    List<PopularFood> getRestFood() {
        List<PopularFood> rest = new ArrayList<PopularFood>();
        rest.add(new PopularFood("3.9", "Punjabi Tadka", "200 for one", "North Indian ,Canadian", " This is Pure & Non veg restuarant", R.drawable.punjabi));
        rest.add(new PopularFood("4.0", "Chat-Chit", "100 for one", "Fast-Food,Chat,GolGappe", " This is Pure veg restuarant", R.drawable.chat));
        rest.add(new PopularFood("4.1", "Beijing Street", "300 for one", "Asian,Chinese,Thai", " This is Pure & Non veg restuarant", R.drawable.chinese));
        rest.add(new PopularFood("4.0", "Tossian Pizza", "250 for one", "Pizza ,Fast Food,Desserts", " This is Pure & Non veg restuarant", R.drawable.pizza1));
        rest.add(new PopularFood("4.2", "BakeQeens", "150 for one", "Bakery", " This is Pure & Non veg Bakery ", R.drawable.cake));
        rest.add(new PopularFood("4.3", "McDonald", "290 for one", "Burger, Fast Food,Coke,Icecream", " This is Pure & Non veg restuarant", R.drawable.mcd));
        rest.add(new PopularFood("3.9", "Subways", "150 for one", "Subways,Fast Food,Drinks", " This is Pure & Non veg restuarant", R.drawable.subway));
        rest.add(new PopularFood("4.3", "Chill's Grill & Bar", "350 for one", "Mexican,Italian", " This is Pure & Non veg restuarant", R.drawable.mexi));
        rest.add(new PopularFood("3.9", "Kia's Cafe", "350 for one", "Korean,Sushi,Beverage", " This is Pure & Non veg restuarant", R.drawable.korean));
        rest.add(new PopularFood("4.0", "Yangkiez-Momos & More", "150 for one", "Chinese,Momos", " This is Pure & Non veg restuarant", R.drawable.momos));
        rest.add(new PopularFood("4.4", "Dosa Country", "150 for one", "South Indian ,Beverage,Shake", " This is Pure  ", R.drawable.dosa));
        rest.add(new PopularFood("3.9", "Namaste Asia", "400 for one", "Asian,Chinese,Japanese", " This is Pure & Non veg restuarant", R.drawable.indo));

        return rest;


    }


//about profile fragment

    public void profile(View view) {
        Intent i = new Intent(Home2Activity.this, profile.class);
        startActivity(i);
        finish();

    }

    public void locationMap(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Home2Activity.this, R.style.BottomSheetDialogTheme);
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
            if (item.getItemId() == R.id.nav_Dining) {


                selectedActivity = new Intent(Home2Activity.this, Dinning.class);

            }

            startActivity(selectedActivity);
            overridePendingTransition(0, 0);
            finish();
            return true;
        }
    };


}