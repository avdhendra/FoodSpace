package com.example.foodspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Home2Activity extends AppCompatActivity {

private RecyclerView recyclerViewCategoryList,recyclerViewRestuarant;
TextView seemore;

private List<PopularFoodItem> foodItems;
private List<PopularFood>Restuarant;
private boolean expanded=false;
private final int expandedHeight=116;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        recyclerViewCategory();
        recyclerRestaurant();

    }
    private void recyclerViewCategory(){
        recyclerViewCategoryList=findViewById(R.id.recyclerView1);
       initRecycler();
seemore=findViewById(R.id.seemore);
seemore.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

       //see the drawable at the end seemore.;
        expanded=!expanded;
        if (expanded){
            expand(recyclerViewCategoryList);
            seemore.setText("See Less");
        }else{
            collapse(recyclerViewCategoryList);
            seemore.setText("See More");
        }
        recyclerViewCategoryList.invalidate();
    }
});

    }
    private int pixelToDp(int pixels){
        int dp=(int)(pixels * getResources().getDisplayMetrics().density);
        return dp;
    }
    private void initRecycler(){
foodItems=getItem(getApplicationContext());

        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(this,foodItems);
        recyclerViewCategoryList.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

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
        ret.add(new PopularFoodItem("Pizza",drawableToBitmap(getDrawable(context,"image12"))));
        ret.add(new PopularFoodItem("Rolls",drawableToBitmap(getDrawable(context,"image13"))));
        ret.add(new PopularFoodItem("Subways",drawableToBitmap(getDrawable(context,"image14"))));
        ret.add(new PopularFoodItem("Shake",drawableToBitmap(getDrawable(context,"image15"))));

        return ret;
    }

    public static Drawable getDrawable(Context context,String name){
        int resourceId=context.getResources().getIdentifier(name,"drawable",context.getPackageName());
        return AppCompatResources.getDrawable(context,resourceId);
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

        ValueAnimator valueAnimator=ValueAnimator.ofInt(view.getMeasuredHeight(),pixelToDp(expandedHeight)).setDuration(400);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
               int animatedValue=(int)valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams lp=view.getLayoutParams();
                lp.height=animatedValue;
                view.setLayoutParams(lp);
                if(animatedValue==0){

                }
            }
        });
valueAnimator.start();
    }

public void expand(final View view){
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight=view.getMeasuredHeight();
    ValueAnimator valueAnimator = ValueAnimator.ofInt(pixelToDp(expandedHeight),view.getMeasuredHeight())
            .setDuration(500);

    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int animatedValue = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            lp.height = animatedValue;
            view.setLayoutParams(lp);
            if (animatedValue == 0) {

            }
        }
    });

    valueAnimator.start();
}
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
    rest.add(new PopularFood("4.0","Tossian Pizza","250 for one","Pizza ,Fast Food,Desserts"," This is Pure & Non veg restuarant",R.drawable.pizza));
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
}