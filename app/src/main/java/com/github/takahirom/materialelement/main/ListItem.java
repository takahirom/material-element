package com.github.takahirom.materialelement.main;

import android.os.Parcel;
import android.os.Parcelable;

class ListItem implements Parcelable {
    public int viewType;
    public int itemId;

    ListItem(int viewType, int itemId) {
        this.viewType = viewType;
        this.itemId = itemId;
    }

    public ListItem() {
    }

    protected ListItem(Parcel in) {
        viewType = in.readInt();
        itemId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(viewType);
        dest.writeInt(itemId);
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
}
