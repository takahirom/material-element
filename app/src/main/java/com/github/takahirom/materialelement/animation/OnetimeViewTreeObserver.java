package com.github.takahirom.materialelement.animation;

import android.view.View;
import android.view.ViewTreeObserver;

public class OnetimeViewTreeObserver {
    public static void addOnPreDrawListener(final View view, final OnetimeViewTreeObserver.OnPreDrawListener listener) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                listener.onPreDraw();
                return false;
            }
        });
    }

    public interface OnPreDrawListener {
        void onPreDraw();
    }
}
