package com.github.takahirom.material_design_animation_playground.main;

class HeaderItem extends ListItem {

    public final String title;

    HeaderItem(int viewType, int itemId, String title) {
        super(viewType, itemId);
        this.title = title;
    }
}
