package com.example.foodspace.ui.Fragments.FragmentAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodspace.R;
import com.example.foodspace.ui.Fragments.TabFragment;
import com.example.foodspace.Model.viewPagerTabModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class viewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private final int noOfTabs;

    private final List<viewPagerTabModel> mainModelList;
    public static final String TAG="ViewPager";

    public viewPagerAdapter(@NonNull FragmentManager fm ,Context context, int noOfTabs, List<viewPagerTabModel> mainModelList) {
        super(fm);
        this.context = context;
        this.noOfTabs = noOfTabs;
        this.mainModelList = mainModelList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        viewPagerTabModel mainModel = mainModelList.get(position);
        return  TabFragment.newInstance(mainModel.getTab_name());
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.customtab, null);
        viewPagerTabModel mainModel = mainModelList.get(position);
        TextView tv = (TextView) v.findViewById(R.id.item_name);



        tv.setText(mainModel.getLabel());
        ImageView img = (ImageView) v.findViewById(R.id.item_icon);
//Pizza
int drawable=getResId(mainModel.getTab_name());
if(drawable!=0)
{
    img.setImageResource(drawable);
}

        if(position != 0){
            img.setImageAlpha(0x3F);
            tv.setTextColor(context.getResources().getColor(R.color.gray_1));

        }
        return v;
    }
    public static int getResId(String name){
int result=0;
        try{
    result=R.drawable.class.getField(name).getInt(null);

}catch (Exception e)
        {
            Log.d(TAG,"NOT FOUND");
        }
        return result;
    }
}
