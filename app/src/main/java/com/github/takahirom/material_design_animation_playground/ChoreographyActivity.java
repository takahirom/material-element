package com.github.takahirom.material_design_animation_playground;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class ChoreographyActivity extends AppCompatActivity {


    public final static String RESULT_EXTRA_ITEM_ID = "RESULT_EXTRA_ITEM_ID";
    public static final String INTENT_EXTRA_ITEM = "item";
    private ListItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choreography);
        item = getIntent().getParcelableExtra(INTENT_EXTRA_ITEM);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViews();
    }

    public void setupViews() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        final ImageView imageView = (ImageView) findViewById(R.id.detail_image);
        ActivityCompat.postponeEnterTransition(this);
//        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.ChoreographyActivity.this, android.R.color.white));

        final ImageView rowImage = (ImageView) findViewById(R.id.all_content_element_share_image);
        Glide.with(this).load(item.imageUrl).asBitmap().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                imageView.setImageBitmap(resource);
                imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        ActivityCompat.startPostponedEnterTransition(com.github.takahirom.material_design_animation_playground.ChoreographyActivity.this);
                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    }
                });

                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                rowImage.setImageDrawable(circularBitmapDrawable);
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

        final CardView cardView = (CardView) findViewById(R.id.all_content_element_share_card);
        cardView.setOnClickListener(new View.OnClickListener() {
            boolean isScene1 = true;
            @Override
            public void onClick(View v) {
                final Scene scene = Scene.getSceneForLayout(cardView, isScene1 ? R.layout.all_element_share_scene2 : R.layout.all_element_share_scene1, ChoreographyActivity.this);
                final Transition transition = TransitionInflater.from(ChoreographyActivity.this).inflateTransition(isScene1 ? R.transition.continuity_enter : R.transition.continuity_exit);
                TransitionManager.go(scene, transition);

                ((ImageView) scene.getSceneRoot().findViewById(R.id.all_content_element_share_image)).setImageDrawable(rowImage.getDrawable());

                isScene1 = !isScene1;
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
