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

public class RecyclerAdapter5 extends RecyclerView.Adapter<RecyclerAdapter5.ViewHolder> {
    List<settingItem1> settingItem1s;
    Context context;
    private RecyclerAdapter5.RecyclerViewonClickListener1 listener;

    public RecyclerAdapter5(Context context, List<settingItem1> popRes, RecyclerAdapter5.RecyclerViewonClickListener1 listener) {
        settingItem1s = popRes;
        this.context = context;
        this.listener = listener;

    }

    @NonNull
    @Override
    public RecyclerAdapter5.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.setting, parent, false);
        return new RecyclerAdapter5.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter5.ViewHolder holder, int position) {
        holder.settingImage.setImageResource(settingItem1s.get(position).getBitmap());
        holder.title.setText(settingItem1s.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return settingItem1s.size();
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

    public interface RecyclerViewonClickListener1 {
        void onClick(View v, int postion);
    }


}
