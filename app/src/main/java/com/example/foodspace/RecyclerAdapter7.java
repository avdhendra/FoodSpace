package com.example.foodspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter7 extends RecyclerView.Adapter<RecyclerAdapter7.ViewHolder>{
    List<restaurantData> restaurantData1;
    Context context;

    public RecyclerAdapter7(List<restaurantData> restaurantData1, Context context) {
        this.restaurantData1 = restaurantData1;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter7.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.resturantmenuitem,parent,false);
        return new RecyclerAdapter7.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter7.ViewHolder holder, int position) {
holder.title.setText(restaurantData1.get(position).getTitle());
holder.price.setText(String.valueOf(restaurantData1.get(position).getPrice()));

holder.image.setBackgroundResource(restaurantData1.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return restaurantData1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,price;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.dishesname);
            price=itemView.findViewById(R.id.dishesprice);
            image=itemView.findViewById(R.id.dishesImage);
        }
    }
}
