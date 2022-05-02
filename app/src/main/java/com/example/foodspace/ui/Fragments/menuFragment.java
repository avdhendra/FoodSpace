package com.example.foodspace.ui.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.foodspace.R;
import com.example.foodspace.Model.restaurantData;
import com.example.foodspace.ui.Adapters.RecyclerAdapter7;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class menuFragment extends Fragment {

    List<restaurantData> restaurantMenus;
    RecyclerView recyclerViewRestMenu;
    private RecyclerAdapter7.RecyclerViewClickListener listener2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        recyclerViewRestMenu = view.findViewById(R.id.recyclerViewresturant);
        restaurantMenus1();
        return view;
    }

    private void restaurantMenus1() {
        restaurantMenus = getItem();
        setOnClickListener();
        recyclerViewRestMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapter7 recyclerAdapter7 = new RecyclerAdapter7(restaurantMenus, getContext(), listener2);
        recyclerViewRestMenu.setAdapter(recyclerAdapter7);
    }

    private void setOnClickListener() {
        listener2 = new RecyclerAdapter7.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int postion) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.bottomsheetmenuitem, (LinearLayout) v.findViewById(R.id.bottomsheetmap));
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetView.getLayoutParams().height = 1800;
                bottomSheetDialog.show();
            }
        };
    }

    private List<restaurantData> getItem() {
        List<restaurantData> result = new ArrayList<restaurantData>();
        result.add(new restaurantData("Palak Panner", 150, R.drawable.palakpanner));
        result.add(new restaurantData("Chicken Cury", 300, R.drawable.chickencurry));
        result.add(new restaurantData("Sphagetti", 150, R.drawable.sphagetti));
        result.add(new restaurantData("Pizza", 300, R.drawable.pizza1));
        return result;
    }
}