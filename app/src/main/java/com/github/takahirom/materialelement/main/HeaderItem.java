package com.github.takahirom.materialelement.main;

class HeaderItem extends ListItem {

    public final String title;

    HeaderItem(int viewType, int itemId, String title) {
        super(viewType, itemId);
        this.title = title;
    }
}
