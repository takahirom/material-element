package com.github.takahirom.material_design_animation_playground.main;

import android.os.Parcel;
import android.os.Parcelable;

public class ImplementationItem extends ListItem implements Parcelable {
    public final String title;
    public final String imageUrl;
    private final String activityClassName;

    ImplementationItem(int itemId, String title, String imageUrl, Class<?> clazz) {
        super(ImplementationAdapter.VIEW_TYPE_IMPLEMENTATION, itemId);

        this.title = title;
        this.imageUrl = imageUrl;
        this.activityClassName = clazz.getName();
    }

    protected ImplementationItem(Parcel in) {
        title = in.readString();
        imageUrl = in.readString();
        activityClassName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(activityClassName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImplementationItem> CREATOR = new Creator<ImplementationItem>() {
        @Override
        public ImplementationItem createFromParcel(Parcel in) {
            return new ImplementationItem(in);
        }

        @Override
        public ImplementationItem[] newArray(int size) {
            return new ImplementationItem[size];
        }
    };

    Class<?> getActivityClass() {
        try {
            return Class.forName(activityClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


}