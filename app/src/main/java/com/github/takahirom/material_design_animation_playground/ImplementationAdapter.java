package com.github.takahirom.material_design_animation_playground;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.takahirom.material_design_animation_playground.durationeasing.DurationAndEasingActivity;
import com.github.takahirom.material_design_animation_playground.movement.MovementActivity;
import com.github.takahirom.material_design_animation_playground.transforming.TransformingActivity;

import java.util.ArrayList;

public class ImplementationAdapter extends RecyclerView.Adapter<ImplementationAdapter.ItemViewHolder> {

    private final ArrayList<ListItem> listItems;
    private OnItemClickListener onItemClickListener;

    public ImplementationAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        listItems = new ArrayList<>();
        listItems.add(
                    new ListItem(
                            1,
                            "Duration & easing",
                            "https://storage.googleapis.com/material-design/publish/material_v_10/assets/0BybB4JO78tNpRlY1eHJ4LTh4ZjQ/01-duration-and-easing.png",
                            DurationAndEasingActivity.class
                    )
            );
        listItems.add(
                    new ListItem(
                            2,
                            "Movement",
                            "https://storage.googleapis.com/material-design/publish/material_v_10/assets/0BybB4JO78tNpWnRtS1RnaVk3Sjg/02-movement.png",
                            MovementActivity.class
                    )
            );
        listItems.add(
                    new ListItem(
                            2,
                            "Transforming material",
                            "https://storage.googleapis.com/material-design/publish/material_v_10/assets/0BybB4JO78tNpNHFEd2JvRXlqRlU/03-transforming-material.png",
                            TransformingActivity.class
                    )
            );
        for (int i = 10; i < 30; i++) {
            listItems.add(
                    new ListItem(
                            i,
                            "Duration & easing" + i,
                            "https://storage.googleapis.com/material-design/publish/material_v_10/assets/0BybB4JO78tNpRlY1eHJ4LTh4ZjQ/01-duration-and-easing.png",
                            DurationAndEasingActivity.class
                    )
            );
        }

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ItemViewHolder itemViewHolder = new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.implementation_item, parent, false));
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(listItems.get(position), onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView titleView;

        ItemViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.implementation_imge);
            titleView = (TextView) view.findViewById(R.id.implementation_title);
        }

        void bind(final ListItem item, final OnItemClickListener itemClickListener) {
            Glide.with(itemView.getContext()).load(item.imageUrl).into(imageView);
            titleView.setText(item.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(imageView, item);
                }
            });
        }
    }

    interface OnItemClickListener {
        public void onItemClick(ImageView fromImageView, ListItem item);
    }

    @Override
    public long getItemId(int position) {
        return listItems.get(position).itemId;
    }

    public int getItemPosition(final long itemId) {
        for (int position = 0; position < listItems.size(); position++) {
            if (listItems.get(position).itemId == itemId) return position;
        }
        return RecyclerView.NO_POSITION;
    }
}