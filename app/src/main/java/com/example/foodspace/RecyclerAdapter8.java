package com.example.foodspace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter8 extends RecyclerView.Adapter<RecyclerAdapter8.ViewHolder> {
List<ReviewItem>reviewItemList;
Context context;

    public RecyclerAdapter8(List<ReviewItem> reviewItemList, Context context) {
        this.reviewItemList = reviewItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter8.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.reviewcardview,parent,false);
    return new RecyclerAdapter8.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter8.ViewHolder holder, int position) {
holder.imageView.setBackgroundResource(reviewItemList.get(position).getImage());
holder.message.setText(reviewItemList.get(position).getMessage());
holder.name.setText(reviewItemList.get(position).getName());
holder.rate.setText(String.valueOf(reviewItemList.get(position).getRate()));
    }

    @Override
    public int getItemCount() {
        return reviewItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView name,rate,message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.reviewImage);
            name=itemView.findViewById(R.id.reviewname);
            rate=itemView.findViewById(R.id.reviewrating);
            message=itemView.findViewById(R.id.reviewmessage);

        }
    }
}
