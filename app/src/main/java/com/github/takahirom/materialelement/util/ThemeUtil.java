package com.github.takahirom.materialelement.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.view.Window;

import com.github.takahirom.materialelement.R;

public class ThemeUtil {
    public static void setTaskDescriptionColor(Activity activity) {
        if (AndroidVersionUtil.isGreaterThanL()) {
            Bitmap bm = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.element_icon);
            String name = activity.getString(R.string.app_name);
            int color = ContextCompat.getColor(activity, R.color.colorPrimaryDark);
            ActivityManager.TaskDescription taskDesc = new ActivityManager.TaskDescription(name, bm, color);
            activity.setTaskDescription(taskDesc);
        }
    }

}
