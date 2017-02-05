package com.github.takahirom.material_design_animation_playground.durationeasing;

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
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.github.takahirom.material_design_animation_playground.ListItem;
import com.github.takahirom.material_design_animation_playground.R;
import com.github.takahirom.material_design_animation_playground.ScreenUtil;

public class DurationAndEasingActivity extends AppCompatActivity {

    public final static String RESULT_EXTRA_ITEM_ID = "RESULT_EXTRA_ITEM_ID";
    public static final String INTENT_EXTRA_ITEM = "item";
    private ListItem item;

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
        ActivityCompat.postponeEnterTransition(this);
//        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(DurationAndEasingActivity.this, android.R.color.white));

        Glide.with(this).load(item.imageUrl).into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            protected void setResource(GlideDrawable resource) {
                super.setResource(resource);

                imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        ActivityCompat.startPostponedEnterTransition(DurationAndEasingActivity.this);
                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    }
                });
            }
        });
        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                collapsingToolbarLayout.setTitleEnabled(true);
                collapsingToolbarLayout.setTitle(item.title);
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
        });

        setupCustomDuration();
        setupNaturalCurve();
    }

    private void setupNaturalCurve() {
        findViewById(R.id.curve_standard_fab).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.curve_deceleration_fab).setOnClickListener(new View.OnClickListener() {
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

        findViewById(R.id.curve_acceleration_fab).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.curve_sharp_fab).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.duration_fab1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTranslationX() > 0) {
                    view.animate().translationX(0).setDuration(290).start();
                } else {
                    view.animate().translationX(ScreenUtil.dp2px(300, DurationAndEasingActivity.this)).setDuration(290).start();
                }
            }
        });

        findViewById(R.id.duration_fab2).setOnClickListener(new View.OnClickListener() {
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
