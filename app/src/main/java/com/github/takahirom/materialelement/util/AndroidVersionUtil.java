package com.github.takahirom.materialelement.util;

import android.os.Build;

public class AndroidVersionUtil {
    public static boolean isGreaterThanKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isGreaterThanL() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isGreaterThanM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
