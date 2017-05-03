/*
 * Copyright (C) 2017 takahirom
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
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.takahirom.materialelement.animation.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeConverter;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * From ChangeBoundsPort in AOSP
 */
public class AsymmetricTransform extends Transition {
    private static final String PROP_BOUNDS = "mdap:fabTransform:bounds";

    private static final String[] transitionProperties = {
            PROP_BOUNDS
    };
    private static final long DEFAULT_DURATION = 375L;

    public AsymmetricTransform() {
        super();
        setDuration(DEFAULT_DURATION);
    }

    public AsymmetricTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public String[] getTransitionProperties() {
        return transitionProperties;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }


    private void captureValues(TransitionValues transitionValues) {
        final View view = transitionValues.view;
        if (view == null || view.getWidth() <= 0 || view.getHeight() <= 0) return;
        transitionValues.values.put(PROP_BOUNDS, new Rect(view.getLeft(), view.getTop(),
                view.getRight(), view.getBottom()));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null) return null;

        final Rect startBounds = (Rect) startValues.values.get(PROP_BOUNDS);
        final Rect endBounds = (Rect) endValues.values.get(PROP_BOUNDS);

        final boolean expand = startBounds.top > endBounds.top;
        final View view = endValues.view;

        int startLeft = startBounds.left;
        int endLeft = endBounds.left;
        int startTop = startBounds.top;
        int endTop = endBounds.top;
        int startRight = startBounds.right;
        int endRight = endBounds.right;
        int startBottom = startBounds.bottom;
        int endBottom = endBounds.bottom;
        int startWidth = startRight - startLeft;
        int startHeight = startBottom - startTop;
        int endWidth = endRight - endLeft;
        int endHeight = endBottom - endTop;
        int numChanges = 0;
        if (startWidth != 0 && startHeight != 0 && endWidth != 0 && endHeight != 0) {
            if (startLeft != endLeft) {
                ++numChanges;
            }
            if (startRight != endRight) {
                ++numChanges;
            }
        }
        PropertyValuesHolder pvhWidth[] = new PropertyValuesHolder[numChanges];
        int pvhIndex = 0;
        if (startLeft != endLeft) {
            view.setLeft(startLeft);
        }
        if (startTop != endTop) {
            view.setTop(startTop);
        }
        if (startRight != endRight) {
            view.setRight(startRight);
        }
        if (startBottom != endBottom) {
            view.setBottom(startBottom);
        }
        if (startLeft != endLeft) {
            Path topLeftPath = getPathMotion().getPath(startLeft, startTop, endLeft,
                    endTop);
            pvhWidth[pvhIndex++] = PropertyValuesHolder.ofObject("left", new TypeConverter<PointF, Integer>(PointF.class, Integer.class) {
                @Override
                public Integer convert(PointF value) {
                    return (int) value.x;
                }
            }, topLeftPath);
        }

        if (startRight != endRight) {
            Path topRightPath = getPathMotion().getPath(startRight, startTop, endRight,
                    endTop);
            pvhWidth[pvhIndex] = PropertyValuesHolder.ofObject("right", new TypeConverter<PointF, Integer>(PointF.class, Integer.class) {
                @Override
                public Integer convert(PointF value) {
                    return (int) value.x;
                }
            }, topRightPath);
        }

        ObjectAnimator widthAnim = ObjectAnimator.ofPropertyValuesHolder(view, pvhWidth);


        pvhIndex = 0;
        numChanges = 0;
        if (startWidth != 0 && startHeight != 0 && endWidth != 0 && endHeight != 0) {
            if (startTop != endTop) {
                ++numChanges;
            }
            if (startBottom != endBottom) {
                ++numChanges;
            }
        }
        PropertyValuesHolder pvhHeight[] = new PropertyValuesHolder[numChanges];

        if (startTop != endTop) {
            Path topRightPath = getPathMotion().getPath(startRight, startTop, endRight,
                    endTop);
            pvhHeight[pvhIndex++] = PropertyValuesHolder.ofObject("top", new TypeConverter<PointF, Integer>(PointF.class, Integer.class) {
                @Override
                public Integer convert(PointF value) {
                    return (int) value.y;
                }
            }, topRightPath);
        }
        if (startBottom != endBottom) {
            Path topRightPath = getPathMotion().getPath(startRight, startBottom, endRight,
                    endBottom);
            pvhHeight[pvhIndex] = PropertyValuesHolder.ofObject("bottom", new TypeConverter<PointF, Integer>(PointF.class, Integer.class) {
                @Override
                public Integer convert(PointF value) {
                    return (int) value.y;
                }
            }, topRightPath);
        }
        ObjectAnimator heightAnim = ObjectAnimator.ofPropertyValuesHolder(view, pvhHeight);

        AnimatorSet animatorSet = new AnimatorSet();
        if (expand) {
            widthAnim.setDuration(275);
            heightAnim.setStartDelay(30);
            heightAnim.setDuration(345);
            animatorSet.playTogether(widthAnim, heightAnim);
        } else {
            widthAnim.setDuration(325);
            widthAnim.setStartDelay(50);
            heightAnim.setDuration(325);
            animatorSet.playTogether(heightAnim, widthAnim);
        }
        animatorSet.setInterpolator(new FastOutSlowInInterpolator());

        if (view.getParent() instanceof ViewGroup) {
            final ViewGroup parent = (ViewGroup) view.getParent();
//                        parent.suppressLayout(true);
            Transition.TransitionListener transitionListener = new Transition.TransitionListener() {
                boolean mCanceled = false;

                @Override
                public void onTransitionCancel(Transition transition) {
//                                parent.suppressLayout(false);
                    mCanceled = true;
                }

                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (!mCanceled) {
//                                    parent.suppressLayout(false);
                    }
                }

                @Override
                public void onTransitionPause(Transition transition) {
//                                parent.suppressLayout(false);
                }

                @Override
                public void onTransitionResume(Transition transition) {
//                                parent.suppressLayout(true);
                }
            };
            addListener(transitionListener);
        }
        return animatorSet;
    }

}