package com.github.takahirom.material_design_animation_playground.main;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.takahirom.material_design_animation_playground.R;
import com.github.takahirom.material_design_animation_playground.choreography.ChoreographyActivity;
import com.github.takahirom.material_design_animation_playground.durationeasing.DurationAndEasingActivity;
import com.github.takahirom.material_design_animation_playground.movement.MovementActivity;
import com.github.takahirom.material_design_animation_playground.transforming.TransformingActivity;

import java.util.ArrayList;

class ImplementationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_IMPLEMENTATION = 1;


    private final ArrayList<ListItem> listItems;
    private OnItemClickListener onItemClickListener;

    ImplementationAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        listItems = new ArrayList<>();
        listItems.add(new HeaderItem(0, VIEW_TYPE_HEADER, context.getString(R.string.motion)));
        listItems.add(
                new ImplementationItem(
                        1,
                        "Duration & easing",
                        "https://storage.googleapis.com/material-design/publish/material_v_10/assets/0BybB4JO78tNpRlY1eHJ4LTh4ZjQ/01-duration-and-easing.png",
                        DurationAndEasingActivity.class
                )
        );
        listItems.add(
                new ImplementationItem(
                        2,
                        "Movement",
                        "https://storage.googleapis.com/material-design/publish/material_v_10/assets/0BybB4JO78tNpWnRtS1RnaVk3Sjg/02-movement.png",
                        MovementActivity.class
                )
        );
        listItems.add(
                new ImplementationItem(
                        3,
                        "Transforming material",
                        "https://storage.googleapis.com/material-design/publish/material_v_10/assets/0BybB4JO78tNpNHFEd2JvRXlqRlU/03-transforming-material.png",
                        TransformingActivity.class
                )
        );
        listItems.add(
                new ImplementationItem(
                        4,
                        "Choreography",
                        "https://storage.googleapis.com/material-design/publish/material_v_10/assets/0BybB4JO78tNpaEZJZ0E2ODQ1blE/04-choreography.png",
                        ChoreographyActivity.class
                )
        );
        for (int i = 10; i < 30; i++) {
            listItems.add(
                    new ImplementationItem(
                            i,
                            "Duration & easing" + i,
                            "https://storage.googleapis.com/material-design/publish/material_v_10/assets/0BybB4JO78tNpRlY1eHJ4LTh4ZjQ/01-duration-and-easing.png",
                            DurationAndEasingActivity.class
                    )
            );
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_header, parent, false));
            case VIEW_TYPE_IMPLEMENTATION:
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_implementation, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_HEADER:
                ((HeaderViewHolder) holder).bind((HeaderItem) listItems.get(position));
                break;
            case VIEW_TYPE_IMPLEMENTATION:
                ((ItemViewHolder) holder).bind((ImplementationItem) listItems.get(position), onItemClickListener);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listItems.get(position).viewType;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleView;

        HeaderViewHolder(View view) {
            super(view);
            titleView = (TextView) view.findViewById(R.id.title);
        }

        void bind(final HeaderItem item) {
            titleView.setText(item.title);
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView titleView;

        ItemViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.implementation_imge);
            titleView = (TextView) view.findViewById(R.id.implementation_title);
        }

        void bind(final ImplementationItem item, final OnItemClickListener itemClickListener) {
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
        void onItemClick(ImageView fromImageView, ImplementationItem item);
    }

    @Override
    public long getItemId(int position) {
        return listItems.get(position).itemId;
    }

    int getItemPosition(final long itemId) {
        for (int position = 0; position < listItems.size(); position++) {
            ListItem listItem = listItems.get(position);
            if (listItem.itemId == itemId) return position;
        }
        return RecyclerView.NO_POSITION;
    }
}