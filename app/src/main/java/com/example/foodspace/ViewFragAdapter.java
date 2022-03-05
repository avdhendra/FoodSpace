package com.example.foodspace;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class ViewFragAdapter extends FragmentStateAdapter {

    public ViewFragAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment( int position) {
        switch (position){
            case 0:
                return new menuFragment();
            case 1:
                return new DiningFragment();

            case 2:
                return new ReviewFragment();

            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
