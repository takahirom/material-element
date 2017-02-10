package com.github.takahirom.material_design_animation_playground.transforming;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.transition.Transition;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.github.takahirom.material_design_animation_playground.ListItem;
import com.github.takahirom.material_design_animation_playground.R;

public class TransformingActivity extends AppCompatActivity {

    public final static String RESULT_EXTRA_ITEM_ID = "RESULT_EXTRA_ITEM_ID";
    public static final String INTENT_EXTRA_ITEM = "item";
    private ListItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transforming);
        item = getIntent().getParcelableExtra(INTENT_EXTRA_ITEM);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViews();
    }

    public void setupViews() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        final ImageView imageView = (ImageView) findViewById(R.id.detail_image);
        ActivityCompat.postponeEnterTransition(this);
//        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(TransformingActivity.this, android.R.color.white));

        Glide.with(this).load(item.imageUrl).into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            protected void setResource(GlideDrawable resource) {
                super.setResource(resource);

                imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        ActivityCompat.startPostponedEnterTransition(TransformingActivity.this);
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

                getWindow().getEnterTransition().removeListener(this);
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

        findViewById(R.id.aync_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CardView cardView = (CardView) findViewById(R.id.aync_card);
                cardView.setOnClickListener(new View.OnClickListener() {
                    boolean isSmall = true;

                    @Override
                    public void onClick(View view) {
                        if (isSmall) {
                            ViewCompat
                                    .animate(view)
                                    .scaleX(3.0f)
                                    .setDuration(275)
                                    .setStartDelay(0)
                                    .setInterpolator(new FastOutSlowInInterpolator())
                                    .start();
                            ViewCompat
                                    .animate(view)
                                    .scaleY(3.0f)
                                    .setDuration(350)
                                    .setStartDelay(25) // 開始をずらす
                                    .setInterpolator(new FastOutSlowInInterpolator())
                                    .start();
                        } else {
                            ViewCompat
                                    .animate(view)
                                    .scaleX(1.0f)
                                    .setDuration(325)
                                    .setStartDelay(50)
                                    .setInterpolator(new FastOutSlowInInterpolator())
                                    .start();
                            ViewCompat
                                    .animate(view)
                                    .scaleY(1.0f)
                                    .setStartDelay(0)
                                    .setDuration(325)
                                    .setInterpolator(new FastOutSlowInInterpolator())
                                    .start();
                        }
                        isSmall = !isSmall;
                    }
                });
            }
        });

        findViewById(R.id.sync_card).setOnClickListener(new View.OnClickListener() {
            boolean isSmall = true;

            @Override
            public void onClick(View v) {
                ViewCompat
                        .animate(v)
                        .scaleX(isSmall ? 3.0f : 1.0f)
                        .scaleY(isSmall ? 3.0f : 1.0f)
                        .setDuration(300)
                        .setInterpolator(new FastOutSlowInInterpolator())
                        .start();
                isSmall = !isSmall;
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

