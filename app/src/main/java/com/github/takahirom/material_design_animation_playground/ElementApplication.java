package com.github.takahirom.material_design_animation_playground;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ElementApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        // Install font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
