package com.example.foodspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter4 extends RecyclerView.Adapter<RecyclerAdapter4.ViewHolder>{
    List<PopularRestaurant>popRes;
    Context context;

    public RecyclerAdapter4(List<PopularRestaurant> popRes, Context context) {
        this.popRes = popRes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter4.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.restuarantplaces,parent,false);
        return new RecyclerAdapter4.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter4.ViewHolder holder, int position) {
        holder.foodImage.setBackgroundResource(popRes.get(position).getFoodImageURl());
        holder.restaurantName.setText(popRes.get(position).getName());
        holder.rating.setText(popRes.get(position).getRating());
        holder.price.setText(popRes.get(position).getPrice());
        holder.halRestaurant.setText(popRes.get(position).getHalRest());
        holder.typeofFood.setText(popRes.get(position).getTypeFood());
    }

    @Override
    public int getItemCount() {
        return popRes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName,rating,typeofFood,halRestaurant,price;

        ImageView foodImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.price);
            foodImage=itemView.findViewById(R.id.fooditem);
            restaurantName=itemView.findViewById(R.id.ResturantName);
            rating=itemView.findViewById(R.id.rating);
            typeofFood=itemView.findViewById(R.id.foodstyle);
            halRestaurant=itemView.findViewById(R.id.halResturant);
        }
    }
}
