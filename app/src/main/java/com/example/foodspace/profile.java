package com.example.foodspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.Login;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {
    private List<settingItem1> settingItem,settingItem2;
private RecyclerView recyclerViewSetting1,recyclerViewSetting2;
    private RecyclerAdapter.RecyclerViewClickListener listener;
    private RecyclerAdapter6.RecyclerViewClickListener listener2;
    private TextView viewActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recyclerViewSetting1=findViewById(R.id.recyclerView1);
        recyclerViewSetting2=findViewById(R.id.recyclerView2);
        viewActivity=findViewById(R.id.viewActivity);
        RecyclerSetting1();
        RecyclerSetting2();

    }


    private void RecyclerSetting1(){
        settingItem=getItem();
        setonClickListener();
    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
    recyclerViewSetting1.setLayoutManager(layoutManager);
    RecyclerAdapter5 recyclerAdapter5=new RecyclerAdapter5(this,settingItem,listener);
    recyclerViewSetting1.setAdapter(recyclerAdapter5);
    recyclerAdapter5.notifyDataSetChanged();
}

    private void setonClickListener() {
        listener=new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int postion) {
                if(settingItem.get(postion).getTitle().equalsIgnoreCase("Bookmarks")){
Intent i=new Intent(getApplicationContext(),Bookmarks.class);
                    startActivity(i);
                    finish();

                }
            }
        };
    }

    private List<settingItem1>getItem(){
        List<settingItem1>ret=new ArrayList<settingItem1>();
        ret.add(new settingItem1("Bookmarks",R.drawable.ic_baseline_bookmark_border_24));
        ret.add(new settingItem1("Notification",R.drawable.ic_baseline_notifications_none_24));
        ret.add(new settingItem1("Settings",R.drawable.ic_baseline_settings_24));
        ret.add(new settingItem1("Payment",R.drawable.ic_baseline_payment_24));
        return ret;
    }


    private void RecyclerSetting2() {
        settingItem2=getItem2();
        setonClickListener2();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerViewSetting2.setLayoutManager(layoutManager);
        RecyclerAdapter6 recyclerAdapter6=new RecyclerAdapter6(this,settingItem2,listener2);
        recyclerViewSetting2.setAdapter(recyclerAdapter6);
        recyclerAdapter6.notifyDataSetChanged();
    }

    private void setonClickListener2() {
listener2=new RecyclerAdapter6.RecyclerViewClickListener() {
    @Override
    public void onClick(View v, int postion) {
        if(settingItem2.get(postion).getTitle().equalsIgnoreCase("About"))
        {
            Intent i=new Intent(getApplicationContext(),RestaurantMenu.class);
            startActivity(i);
            finish();
        }
        if(settingItem2.get(postion).getTitle().equalsIgnoreCase("Logout")){
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();

        }
    }
};
    }

    private List<settingItem1>getItem2(){
        List<settingItem1>setting=new ArrayList<settingItem1>();
        setting.add(new settingItem1("Your Rating",R.drawable.ic_baseline_star_outline_24));
        setting.add(new settingItem1("Favorite Order",R.drawable.ic_baseline_favorite_border_24));
        setting.add(new settingItem1("Online Order Help",R.drawable.ic_baseline_support_agent_24));
        setting.add(new settingItem1("About",R.drawable.ic_icons8_info));
        setting.add(new settingItem1("Logout",R.drawable.ic_baseline_logout_24));
        return setting ;
    }


    public void backward(View view) {
    }

    public void ShowActivity(View view) {
        Intent i=new Intent(this,AboutYouActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(0,0);
    }
}