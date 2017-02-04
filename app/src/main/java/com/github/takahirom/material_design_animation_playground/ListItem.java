package com.github.takahirom.material_design_animation_playground;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable {
    public final int itemId;
    public final String title;
    public final String imageUrl;
    private final String activityClassName;

    protected ListItem(Parcel in) {
        itemId = in.readInt();
        title = in.readString();
        imageUrl = in.readString();
        activityClassName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(itemId);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(activityClassName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

    public Class<?> getActivityClass() {
        try {
            return Class.forName(activityClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ListItem(int itemId, String title, String imageUrl, Class<?> clazz) {
        this.itemId = itemId;

        this.title = title;
        this.imageUrl = imageUrl;
        this.activityClassName = clazz.getName();
    }

}