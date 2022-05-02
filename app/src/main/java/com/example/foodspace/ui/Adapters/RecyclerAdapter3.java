package com.example.foodspace.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodspace.Model.PopularPlaces;
import com.example.foodspace.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter3 extends RecyclerView.Adapter<RecyclerAdapter3.ViewHolder> {
    private List<PopularPlaces> popPlace;
    Context context;

    public RecyclerAdapter3(List<PopularPlaces> popPlace, Context context) {
        this.popPlace = popPlace;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.diningplace, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter3.ViewHolder holder, int position) {
        holder.imageV.setImageBitmap(popPlace.get(position).getPlace());
        holder.textV.setText(popPlace.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return popPlace.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageV;
        public TextView textV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageV = itemView.findViewById(R.id.placeImage);
            textV = itemView.findViewById(R.id.placeText);
        }
    }
}
