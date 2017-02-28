package com.github.takahirom.materialelement.motion.durationeasing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.github.takahirom.materialelement.animation.transition.TransitionUtils;
import com.github.takahirom.materialelement.main.ImplementationItem;
import com.github.takahirom.materialelement.R;
import com.github.takahirom.materialelement.util.ScreenUtil;

public class DurationAndEasingActivity extends AppCompatActivity {

    public final static String RESULT_EXTRA_ITEM_ID = "RESULT_EXTRA_ITEM_ID";
    public static final String INTENT_EXTRA_ITEM = "item";
    private ImplementationItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_durations);
        item = getIntent().getParcelableExtra(INTENT_EXTRA_ITEM);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViews();
    }

    public void setupViews() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        final ImageView imageView = (ImageView) findViewById(R.id.detail_image);
//        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(DurationAndEasingActivity.this, android.R.color.white));
        imageView.setImageResource(item.imageRes);
        // If you want to load async, you can use this code
        //        ActivityCompat.postponeEnterTransition(this);
//        Glide.with(this).load(item.imageUrl).into(new GlideDrawableImageViewTarget(imageView) {
//            @Override
//            protected void setResource(GlideDrawable resource) {
//                super.setResource(resource);
//
//                imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                    @Override
//                    public boolean onPreDraw() {
//                        ActivityCompat.startPostponedEnterTransition(DurationAndEasingActivity.this);
//                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);
//                        default_window_return true;
//                    }
//                });
//            }
//        });
        TransitionUtils.setSharedElementEnterTransitionEndListenerCompat(getWindow(), new TransitionUtils.OnSharedElementEnterTransitionEndListener() {
            @Override
            public void onEnd(Transition transition) {
                collapsingToolbarLayout.setTitleEnabled(true);
                collapsingToolbarLayout.setTitle(item.title);
            }
        });

        setupCustomDuration();
        setupNaturalCurve();
    }

    private void setupNaturalCurve() {
        final View standardFab = findViewById(R.id.curve_standard_fab);
        final View decelerationFab = findViewById(R.id.curve_deceleration_fab);
        final View accelerationFab = findViewById(R.id.curve_acceleration_fab);
        final View sharpFab = findViewById(R.id.curve_sharp_fab);

        findViewById(R.id.easing_carve_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                standardFab.performClick();
                decelerationFab.performClick();
                accelerationFab.performClick();
                sharpFab.performClick();
            }
        });
        standardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTranslationX() > 0) {
                    v
                            .animate()
                            .translationX(0)
                            .setDuration(290)
                            .setInterpolator(new FastOutSlowInInterpolator())
                            .start();
                } else {
                    v
                            .animate()
                            .translationX(ScreenUtil.dp2px(300, DurationAndEasingActivity.this))
                            .setDuration(290)
                            .setInterpolator(new FastOutSlowInInterpolator())
                            .start();
                }
            }
        });
        decelerationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTranslationX() > 0) {
                    v
                            .animate()
                            .translationX(0)
                            .setDuration(290)
                            .setInterpolator(new LinearOutSlowInInterpolator())
                            .start();
                } else {
                    v
                            .animate()
                            .translationX(ScreenUtil.dp2px(300, DurationAndEasingActivity.this))
                            .setDuration(290)
                            .setInterpolator(new LinearOutSlowInInterpolator())
                            .start();
                }
            }
        });

        accelerationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTranslationX() > 0) {
                    v
                            .animate()
                            .translationX(0)
                            .setDuration(290)
                            .setInterpolator(new FastOutLinearInInterpolator())
                            .start();
                } else {
                    v
                            .animate()
                            .translationX(ScreenUtil.dp2px(300, DurationAndEasingActivity.this))
                            .setDuration(290)
                            .setInterpolator(new FastOutLinearInInterpolator())
                            .start();
                }
            }
        });

        final Interpolator sharpInterpolator = PathInterpolatorCompat.create(0.4f, 0, 0.6f, 1);
        sharpFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTranslationX() > 0) {
                    v
                            .animate()
                            .translationX(0)
                            .setDuration(290)
                            .setInterpolator(sharpInterpolator)
                            .start();
                } else {
                    v
                            .animate()
                            .translationX(ScreenUtil.dp2px(300, DurationAndEasingActivity.this))
                            .setDuration(290)
                            .setInterpolator(sharpInterpolator)
                            .start();
                }
            }
        });
    }

    private void setupCustomDuration() {
        final View dynamicDurationLongFab = findViewById(R.id.duration_fab1);
        final View dynamicDurationShortFab = findViewById(R.id.duration_fab2);

        findViewById(R.id.dynamic_duration_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicDurationLongFab.performClick();
                dynamicDurationShortFab.performClick();
            }
        });

        dynamicDurationLongFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTranslationX() > 0) {
                    view.animate().translationX(0).setDuration(290).start();
                } else {
                    view.animate().translationX(ScreenUtil.dp2px(300, DurationAndEasingActivity.this)).setDuration(290).start();
                }
            }
        });

        dynamicDurationShortFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTranslationX() > 0) {
                    view.animate().translationX(0).setDuration(225).start();
                } else {
                    view.animate().translationX(ScreenUtil.dp2px(120, DurationAndEasingActivity.this)).setDuration(225).start();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        setResultAndFinish();
    }


    void setResultAndFinish() {
        final Intent resultData = new Intent();
        resultData.putExtra(RESULT_EXTRA_ITEM_ID, item.itemId);
        setResult(RESULT_OK, resultData);
        ActivityCompat.finishAfterTransition(this);
    }

}
