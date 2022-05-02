package com.example.foodspace.ui.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodspace.Model.PopularFoodItem;
import com.example.foodspace.R;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<PopularFoodItem> popItem;
    private RecyclerViewClickListener listener;
    Context context;
    public RecyclerAdapter(Context context,RecyclerViewClickListener listener,ArrayList<PopularFoodItem> popItem){
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
       /* holder.imageView.setImageBitmap(popItem.get(position).getBitmap());*/
        Shimmer shimmer=new Shimmer.ColorHighlightBuilder().setBaseColor(Color.parseColor("#F3F3F3")).
                setBaseAlpha(1).setHighlightColor(Color.parseColor("#E7E7E7")).setHighlightColor(1).setDropoff(50).build();
        ShimmerDrawable shimmerDrawable=new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);

        Glide.with(context).load(popItem.get(position).getBitmap()).placeholder(shimmerDrawable).into(holder.imageView);
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
