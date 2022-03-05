package com.example.foodspace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class menuFragment extends Fragment {

List<restaurantData> restaurantMenus;
RecyclerView recyclerViewRestMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   View view=inflater.inflate(R.layout.fragment_menu, container, false);
   recyclerViewRestMenu=view.findViewById(R.id.recyclerViewresturant);
restaurantMenus1();
    return view;
    }

    private void restaurantMenus1() {
        restaurantMenus=getItem();
        recyclerViewRestMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapter7 recyclerAdapter7=new RecyclerAdapter7(restaurantMenus,getContext());
        recyclerViewRestMenu.setAdapter(recyclerAdapter7);
    }

    private List<restaurantData> getItem() {
        List<restaurantData>result=new ArrayList<restaurantData>();
        result.add(new restaurantData("Palak Panner",150,R.drawable.palakpanner));
        result.add(new restaurantData("Chicken Cury",300,R.drawable.chickencurry));
        result.add(new restaurantData("Sphagetti",150,R.drawable.sphagetti));
        result.add(new restaurantData("Pizza",300,R.drawable.pizza1));
        return result;
    }
}