package com.example.foodspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Home2Activity extends AppCompatActivity {

private RecyclerView recyclerViewCategoryList,recyclerViewRestuarant;
TextView seemore;
private BottomNavigationView bottomNavigationView;
private List<PopularFoodItem> foodItems;
private List<PopularFood>Restuarant;
private FloatingActionButton floatingActionButton;
private boolean expanded=false;
private RecyclerAdapter.RecyclerViewClickListener listener;
FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey("expanded")){
                expanded=savedInstanceState.getBoolean("expanded");
            }
        }
        recyclerViewCategory();
        recyclerRestaurant();
        bottomNavigationView=findViewById(R.id.BottomnavigationView);
     
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);
        bottomNavigationView.setItemRippleColor(null);
bottomNavigationView.getMenu().getItem(0).setEnabled(false);

    }
    private void recyclerViewCategory(){
        recyclerViewCategoryList=findViewById(R.id.recyclerView1);
       initRecycler();
seemore=findViewById(R.id.seemore);

seemore.setOnClickListener(v-> setExpanded(!expanded));
setExpanded(expanded);



    }
    private void setExpanded(boolean expanded){

        this.expanded=expanded;
        if(expanded){
            expand(recyclerViewCategoryList);
            seemore.setText("see less");
            setButtonDrawable(R.drawable.show_less);

        }
        else{
            collapse(recyclerViewCategoryList);
            seemore.setText("see more");
            setButtonDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24);

        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outstate){
        outstate.putBoolean("expanded",expanded);
        super.onSaveInstanceState(outstate);
    }
    private int pixelToDp(){
        int dp=(int)(216 * getResources().getDisplayMetrics().density);
        return dp;
    }
    private void initRecycler(){
foodItems=getItem(getApplicationContext());
setonClickListener();
        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(this,foodItems,listener);
        recyclerViewCategoryList.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
        GridLayoutManager gridLayoutManager=(GridLayoutManager) recyclerViewCategoryList.getLayoutManager();
    }

    private void setonClickListener() {
        listener=new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int postion) {
                Intent i=new Intent(getApplicationContext(),expandedLayoutitemactivity.class);
                i.putExtra("title",foodItems.get(postion).getTitle());
                startActivity(i);
                finish();

            }
        };
    }

    //food item in expandable layout
    private List<PopularFoodItem>getItem(Context context){

        List<PopularFoodItem>ret=new ArrayList<PopularFoodItem>();
        ret.add(new PopularFoodItem("Biryani",drawableToBitmap(getDrawable(context,"image1"))));
        ret.add(new PopularFoodItem("Burger",drawableToBitmap(getDrawable(context,"image2"))));
        ret.add(new PopularFoodItem("Cake",drawableToBitmap(getDrawable(context,"image3"))));
        ret.add(new PopularFoodItem("Chicken",drawableToBitmap(getDrawable(context,"image4"))));
        ret.add(new PopularFoodItem("Fries",drawableToBitmap(getDrawable(context,"image5"))));
        ret.add(new PopularFoodItem("Momos",drawableToBitmap(getDrawable(context,"image6"))));
        ret.add(new PopularFoodItem("Noodles",drawableToBitmap(getDrawable(context,"image7"))));
        ret.add(new PopularFoodItem("Panner",drawableToBitmap(getDrawable(context,"image8"))));
        ret.add(new PopularFoodItem("Parathas",drawableToBitmap(getDrawable(context,"image9"))));
        ret.add(new PopularFoodItem("Pasta",drawableToBitmap(getDrawable(context,"image10"))));
        ret.add(new PopularFoodItem("Rolls",drawableToBitmap(getDrawable(context,"image12"))));
        ret.add(new PopularFoodItem("Subways",drawableToBitmap(getDrawable(context,"image13"))));
        ret.add(new PopularFoodItem("Shake",drawableToBitmap(getDrawable(context,"image14"))));
        ret.add(new PopularFoodItem("Pizza",drawableToBitmap(getDrawable(context,"pizza1"))));

        return ret;
    }

    public static Drawable getDrawable(Context context,String name){
        int resourceId=context.getResources().getIdentifier(name,"drawable",context.getPackageName());
        return AppCompatResources.getDrawable(context,resourceId);
    }
    private void setButtonDrawable(int imageResource){
        seemore.setCompoundDrawablesWithIntrinsicBounds(0,0,imageResource,0);
    }

    public static Bitmap drawableToBitmap(Drawable drawable){
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        }
        Bitmap bitmap=Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        drawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    public void collapse(final  View view){
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        final  int targetHeight=view.getMeasuredHeight();

        ValueAnimator valueAnimator=ValueAnimator.ofInt(view.getMeasuredHeight(),pixelToDp()).setDuration(400);
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

public void expand(final View view){
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight=view.getMeasuredHeight();
    ValueAnimator valueAnimator = ValueAnimator.ofInt(pixelToDp(),view.getMeasuredHeight())
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
private void recyclerRestaurant(){
recyclerViewRestuarant=findViewById(R.id.resturantRecycler);
RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
recyclerViewRestuarant.setLayoutManager(layoutManager);
Restuarant=getRestFood();
RecyclerAdapter2 recyclerAdapter2=new RecyclerAdapter2(this,Restuarant);
recyclerViewRestuarant.setAdapter(recyclerAdapter2);
}
List<PopularFood> getRestFood(){
        List<PopularFood>rest=new ArrayList<PopularFood>();
        rest.add(new PopularFood("3.9","Punjabi Tadka","200 for one","North Indian ,Canadian"," This is Pure & Non veg restuarant",R.drawable.punjabi));
    rest.add(new PopularFood("4.0","Chat-Chit","100 for one","Fast-Food,Chat,GolGappe"," This is Pure veg restuarant",R.drawable.chat));
    rest.add(new PopularFood("4.1","Beijing Street","300 for one","Asian,Chinese,Thai"," This is Pure & Non veg restuarant",R.drawable.chinese));
    rest.add(new PopularFood("4.0","Tossian Pizza","250 for one","Pizza ,Fast Food,Desserts"," This is Pure & Non veg restuarant",R.drawable.pizza1));
    rest.add(new PopularFood("4.2","BakeQeens","150 for one","Bakery"," This is Pure & Non veg Bakery ",R.drawable.cake));
    rest.add(new PopularFood("4.3","McDonald","290 for one","Burger, Fast Food,Coke,Icecream"," This is Pure & Non veg restuarant",R.drawable.mcd));
    rest.add(new PopularFood("3.9","Subways","150 for one","Subways,Fast Food,Drinks"," This is Pure & Non veg restuarant",R.drawable.subway));
    rest.add(new PopularFood("4.3","Chill's Grill & Bar","350 for one","Mexican,Italian"," This is Pure & Non veg restuarant",R.drawable.mexi));
    rest.add(new PopularFood("3.9","Kia's Cafe","350 for one","Korean,Sushi,Beverage"," This is Pure & Non veg restuarant",R.drawable.korean));
    rest.add(new PopularFood("4.0","Yangkiez-Momos & More","150 for one","Chinese,Momos"," This is Pure & Non veg restuarant",R.drawable.momos));
    rest.add(new PopularFood("4.4","Dosa Country","150 for one","South Indian ,Beverage,Shake"," This is Pure  ",R.drawable.dosa));
    rest.add(new PopularFood("3.9","Namaste Asia","400 for one","Asian,Chinese,Japanese"," This is Pure & Non veg restuarant",R.drawable.indo));

return rest;


}


//about profile fragment

    public void profile(View view) {
        Intent i=new Intent(Home2Activity.this,profile.class);
        startActivity(i);
        finish();
        overridePendingTransition(0,0);
    }

    public void locationMap(View view) {
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(Home2Activity.this,R.style.BottomSheetDialogTheme);
        View bottomSheetView= LayoutInflater.from(getApplicationContext()).inflate(R.layout.mapbottomsheet,(LinearLayout)findViewById(R.id.bottomsheetmap));

        bottomSheetView.findViewById(R.id.currentlocation).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(0,0);

            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetView.getLayoutParams().height=1500;
        bottomSheetDialog.show();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          Intent selectedActivity=null;
            if (item.getItemId() == R.id.nav_Dining) {


                selectedActivity = new Intent(Home2Activity.this, Dinning.class);

            }

            startActivity(selectedActivity);
            overridePendingTransition(0,0);
            finish();
            return true;
        }
    } ;
}