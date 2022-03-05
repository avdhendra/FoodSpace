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


public class ReviewFragment extends Fragment {
List<ReviewItem>reviewItemList;
RecyclerView reviewRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_review, container, false);
   reviewRecyclerView=view.findViewById(R.id.recyclerViewreview);

        reviewRecycler();
    return view;
    }

    private void reviewRecycler() {
    reviewItemList=getItem();
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapter8 recyclerAdapter8=new RecyclerAdapter8(reviewItemList,getContext());
        reviewRecyclerView.setAdapter(recyclerAdapter8);
    }

    private List<ReviewItem> getItem() {
    List<ReviewItem>result=new ArrayList<ReviewItem>();
    result.add(new ReviewItem("Avdhendra",R.drawable.sphagetti, (float) 4.4,"Good Food But can better"));

    return result;
    }
}