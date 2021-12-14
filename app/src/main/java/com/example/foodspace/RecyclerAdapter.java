package com.example.foodspace;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<PopularFoodItem> popItem;
private RecyclerViewClickListener listener;
    Context context;
    public RecyclerAdapter(Context context,List<PopularFoodItem>popItem,RecyclerViewClickListener listener){
     this.popItem=popItem;
        this.context=context;
   this.listener=listener;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_home2child,parent,false);
return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
holder.imageView.setImageBitmap(popItem.get(position).getBitmap());
holder.textView.setText(popItem.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return popItem.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        public ImageView imageView;
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_child);
            textView=itemView.findViewById(R.id.textItem);
        itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
listener.onClick(view,getAdapterPosition());
        }
    }
    public interface RecyclerViewClickListener{
        void onClick(View v,int postion);
    }
}
