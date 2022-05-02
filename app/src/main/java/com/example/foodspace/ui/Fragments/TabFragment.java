package com.example.foodspace.ui.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodspace.Model.PopularRestaurant;
import com.example.foodspace.R;
import com.example.foodspace.ui.Adapters.RecyclerAdapter4;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TabFragment extends Fragment {
    private TextView textView;
    RecyclerView recyclerView1;
    List<PopularRestaurant> categoryitem;

    public TabFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TabFragment newInstance(String name) {

        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putString("tabName", name);
        fragment.setArguments(args);


        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        textView = view.findViewById(R.id.text_view);
        textView.setText("All Restaurant delivering " + getArguments().getString("tabName"));


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView1 = view.findViewById(R.id.categoryRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        categoryitem = getItem();
        RecyclerAdapter4 recyclerAdapter4 = new RecyclerAdapter4(categoryitem, getActivity());
        recyclerView1.setAdapter(recyclerAdapter4);
    }

    List<PopularRestaurant> getItem() {
        List<PopularRestaurant> rest = new ArrayList<PopularRestaurant>();


        if (getArguments() != null && getArguments().getString("tabName") != null && getArguments().getString("tabName").equalsIgnoreCase("allpizza")) {
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
        }
        return rest;
    }
}
