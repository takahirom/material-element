package com.github.takahirom.materialelement.util;

import android.os.Build;

public class AndroidVersionUtil {
    public static boolean isGreaterThanL(){
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP;
    }
}
