package com.github.takahirom.materialelement.main;

import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.takahirom.materialelement.R;
import com.github.takahirom.materialelement.util.ScreenUtil;

public class AnimateRecyclerAdapter extends RecyclerView.Adapter {
    int animatedRow = -1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_card, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > animatedRow) {
            // https://www.youtube.com/watch?feature=player_detailpage&v=6p3i6H2oGa0#t=602
            animatedRow = position;
            long animationDelay = 1000 + holder.getAdapterPosition() * 25;

            holder.itemView.setAlpha(0);
            holder.itemView.setTranslationY(ScreenUtil.dp2px(16, holder.itemView.getContext()));


            holder.itemView.animate()
                    .alpha(1)
                    .translationY(0)
                    .setDuration(200)
                    .setInterpolator(new LinearOutSlowInInterpolator())
                    .setStartDelay(animationDelay)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
