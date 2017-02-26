package com.github.takahirom.materialelement.movement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.takahirom.materialelement.main.ImplementationItem;
import com.github.takahirom.materialelement.R;

public class MovementActivity extends AppCompatActivity {


    public final static String RESULT_EXTRA_ITEM_ID = "RESULT_EXTRA_ITEM_ID";
    public static final String INTENT_EXTRA_ITEM = "item";
    private ImplementationItem item;
    private Scene arcScene1;
    private Scene arcScene2;
    private Scene notArcScene1;
    private Scene notArcScene2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);
        item = getIntent().getParcelableExtra(INTENT_EXTRA_ITEM);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViews();
    }

    public void setupViews() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        final ImageView imageView = (ImageView) findViewById(R.id.detail_image);

//        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(MovementActivity.this, android.R.color.white));
        imageView.setImageResource(item.imageRes);

        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                collapsingToolbarLayout.setTitleEnabled(true);
                collapsingToolbarLayout.setTitle(item.title);

                getWindow().getEnterTransition().removeListener(this);
                TransitionManager.go(arcScene1);
                TransitionManager.go(notArcScene1);
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

        setupArcMotion();
        setupNotArcMotion();

        final CardView cardView = (CardView) findViewById(R.id.inset_card);
        final View fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardView.getTranslationY() > 0) {
                    cardView
                            .animate()
                            .translationY(0)
                            .setInterpolator(new FastOutSlowInInterpolator())
                            .start();
                } else {
                    cardView
                            .animate()
                            .translationY(cardView.getHeight())
                            .setInterpolator(new FastOutSlowInInterpolator())
                            .start();
                }

            }
        });
    }

    private void setupArcMotion() {

        final RelativeLayout sceneRoot = (RelativeLayout) findViewById(R.id.arc_scene_root);
        arcScene1 = Scene.getSceneForLayout(sceneRoot, R.layout.card_arc_scene1, this);
        arcScene2 = Scene.getSceneForLayout(sceneRoot, R.layout.card_arc_scene2, this);
        sceneRoot.setOnClickListener(new View.OnClickListener() {
            boolean isScene2 = false;

            @Override
            public void onClick(View v) {
                final Transition transition = TransitionInflater.from(MovementActivity.this).inflateTransition(R.transition.change_bounds_arc);
                if (isScene2) {
                    TransitionManager.go(arcScene1, transition);
                } else {
                    TransitionManager.go(arcScene2, transition);
                }
                isScene2 = !isScene2;
            }
        });
    }


    private void setupNotArcMotion() {
        final RelativeLayout sceneRoot = (RelativeLayout) findViewById(R.id.not_arc_scene_root);
        notArcScene1 = Scene.getSceneForLayout(sceneRoot, R.layout.card_not_arc_scene1, this);
        notArcScene2 = Scene.getSceneForLayout(sceneRoot, R.layout.card_not_arc_scene2, this);
        sceneRoot.setOnClickListener(new View.OnClickListener() {
            boolean isScene2 = false;

            @Override
            public void onClick(View v) {
                if (isScene2) {
                    TransitionManager.go(notArcScene1, TransitionInflater.from(MovementActivity.this).inflateTransition(R.transition.change_bounds_arc));
                } else {
                    TransitionManager.go(notArcScene2, TransitionInflater.from(MovementActivity.this).inflateTransition(R.transition.change_bounds_arc));
                }
                isScene2 = !isScene2;
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
