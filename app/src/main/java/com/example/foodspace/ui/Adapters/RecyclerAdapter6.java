package com.example.foodspace.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodspace.R;
import com.example.foodspace.Model.settingItem1;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter6 extends RecyclerView.Adapter<RecyclerAdapter6.ViewHolder> {
    List<settingItem1> settingItem2s;
    Context context;
    private RecyclerViewClickListener listener;

    public RecyclerAdapter6(Context context, List<settingItem1> popRes, RecyclerViewClickListener listener) {
        settingItem2s = popRes;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerAdapter6.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.setting2, parent, false);
        return new RecyclerAdapter6.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter6.ViewHolder holder, int position) {
        holder.settingImage.setImageResource(settingItem2s.get(position).getBitmap());
        holder.title.setText(settingItem2s.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return settingItem2s.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView settingImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            settingImage = itemView.findViewById(R.id.settingImage1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int postion);
    }
}
