/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.takahirom.materialelement.animation.transition;

import android.app.Notification;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.Toast;

import com.github.takahirom.materialelement.R;
import com.github.takahirom.materialelement.util.AndroidVersionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utility methods for working with transitions
 */
public class TransitionUtils {

    private TransitionUtils() {
    }

    public static
    @Nullable
    Transition findTransition(
            @NonNull TransitionSet set, @NonNull Class<? extends Transition> clazz) {
        for (int i = 0; i < set.getTransitionCount(); i++) {
            Transition transition = set.getTransitionAt(i);
            if (transition.getClass() == clazz) {
                return transition;
            }
            if (transition instanceof TransitionSet) {
                Transition child = findTransition((TransitionSet) transition, clazz);
                if (child != null) return child;
            }
        }
        return null;
    }

    public static
    @Nullable
    Transition findTransition(
            @NonNull TransitionSet set,
            @NonNull Class<? extends Transition> clazz,
            @IdRes int targetId) {
        for (int i = 0; i < set.getTransitionCount(); i++) {
            Transition transition = set.getTransitionAt(i);
            if (transition.getClass() == clazz) {
                if (transition.getTargetIds().contains(targetId)) {
                    return transition;
                }
            }
            if (transition instanceof TransitionSet) {
                Transition child = findTransition((TransitionSet) transition, clazz, targetId);
                if (child != null) return child;
            }
        }
        return null;
    }

    public static List<Boolean> setAncestralClipping(@NonNull View view, boolean clipChildren) {
        return setAncestralClipping(view, clipChildren, new ArrayList<Boolean>());
    }

    private static List<Boolean> setAncestralClipping(
            @NonNull View view, boolean clipChildren, List<Boolean> was) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            was.add(group.getClipChildren());
            group.setClipChildren(clipChildren);
        }
        ViewParent parent = view.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            setAncestralClipping((ViewGroup) parent, clipChildren, was);
        }
        return was;
    }

    public static void restoreAncestralClipping(@NonNull View view, List<Boolean> was) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            group.setClipChildren(was.remove(0));
        }
        ViewParent parent = view.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            restoreAncestralClipping((ViewGroup) parent, was);
        }
    }


    public static void setSharedElementEnterTransitionEndListenerCompat(Window window, final OnSharedElementEnterTransitionEndListener listener) {
        if (!AndroidVersionUtil.isGreaterThanL()) {
            return;
        }
        window.getSharedElementEnterTransition().addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                listener.onEnd(transition);
            }
        });
    }

    public static void showForDebug(Window window) {
        if (!AndroidVersionUtil.isGreaterThanL()) {
            return;
        }
        String message = "If you want to disable this dialog, Please restart app." +
                "windowAnimations:" +
                window.getContext().getResources().getResourceName(window.getAttributes().windowAnimations) +
                "\n-----\n" +
                "SharedElementEnterTransition:" +
                window.getSharedElementEnterTransition() +
                "\n-----\n" +
                "ReturnTransition:" +
                window.getReturnTransition() +
                "\n-----\n" +
                "SharedElementReturnTransition:" +
                window.getSharedElementReturnTransition() +
                "\n-----\n" +
                "ReenterTransition:" +
                window.getReenterTransition() +
                "\n-----\n" +
                "SharedElementReenterTransition:" +
                window.getSharedElementReenterTransition() +
                "\n-----\n" +
                "ExitTransition:" +
                window.getExitTransition() +
                "\n-----\n" +
                "SharedElementExitTransition:" +
                window.getSharedElementExitTransition();
        new AlertDialog.Builder(window.getContext())
                .setMessage(message).show();
    }
    private static String toStringForLog(Object object){
        if (object == null) {
            return "None";
        }
        return object.toString();
    }

    public interface OnSharedElementEnterTransitionEndListener {
        void onEnd(Transition transition);
    }

    public static class TransitionListenerAdapter implements Transition.TransitionListener {

        @Override
        public void onTransitionStart(Transition transition) {
        }

        @Override
        public void onTransitionEnd(Transition transition) {
        }

        @Override
        public void onTransitionCancel(Transition transition) {
        }

        @Override
        public void onTransitionPause(Transition transition) {
        }

        @Override
        public void onTransitionResume(Transition transition) {
        }
    }
}
