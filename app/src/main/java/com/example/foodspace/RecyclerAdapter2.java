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

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder> {
    Context context;
List<PopularFood>popularFoodsList;

    public RecyclerAdapter2(Context context, List<PopularFood> popularFoodsList) {
        this.context = context;
        this.popularFoodsList = popularFoodsList;
    }

    @NonNull
    @Override
    public RecyclerAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.restuarantsitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter2.ViewHolder holder, int position) {
holder.foodImage.setImageResource(popularFoodsList.get(position).getFoodImageURl());
holder.resturantName.setText(popularFoodsList.get(position).getName());
holder.rating.setText(popularFoodsList.get(position).getRating());
holder.price.setText(popularFoodsList.get(position).getPrice());
holder.halRestaurant.setText(popularFoodsList.get(position).getHalRest());
holder.typeofFood.setText(popularFoodsList.get(position).getTypeFood());
    }

    @Override
    public int getItemCount() {
        return popularFoodsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      TextView resturantName,rating,typeofFood,halRestaurant,price;

      ImageView foodImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.price);
            foodImage=itemView.findViewById(R.id.fooditem);
            resturantName=itemView.findViewById(R.id.ResturantName);
            rating=itemView.findViewById(R.id.textView4);
            typeofFood=itemView.findViewById(R.id.foodstyle);
            halRestaurant=itemView.findViewById(R.id.halResturant);
        }
    }
}
