package com.github.takahirom.materialelement.pattern.transition;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.takahirom.materialelement.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter {
    OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigational_transition, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(position, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    interface OnItemClickListener {
        void onClick(int position, View view);
    }

}
