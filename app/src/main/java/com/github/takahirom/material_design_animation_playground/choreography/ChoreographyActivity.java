package com.github.takahirom.material_design_animation_playground.choreography;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.takahirom.material_design_animation_playground.ListItem;
import com.github.takahirom.material_design_animation_playground.MainActivity;
import com.github.takahirom.material_design_animation_playground.R;
import com.github.takahirom.material_design_animation_playground.ScreenUtil;

import static com.github.takahirom.material_design_animation_playground.R.id.view;

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

        final ImageView allShareRowImage = (ImageView) findViewById(R.id.all_element_share_image);
        final ImageView fewShareImage = (ImageView) findViewById(R.id.few_element_share_image);
        Glide.with(this).load(R.drawable.choreography).asBitmap().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                imageView.setImageBitmap(resource);
                imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        ActivityCompat.startPostponedEnterTransition(ChoreographyActivity.this);
                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    }
                });

                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                allShareRowImage.setImageDrawable(circularBitmapDrawable);
                fewShareImage.setImageDrawable(circularBitmapDrawable);
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

        setupAllElemenShared(allShareRowImage);

        setupFewElementsAreShared(fewShareImage);

        setupCreation();
    }

    private void setupCreation() {
        findViewById(R.id.new_surface).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final CardView contentView = new CardView(ChoreographyActivity.this);

                contentView.setUseCompatPadding(true);
                final TextView textView = new TextView(ChoreographyActivity.this);
                textView.setText("test");
                contentView.addView(textView);

                int dp8 = (int) ScreenUtil.dp2px(8, ChoreographyActivity.this);
                ((CardView.LayoutParams) textView.getLayoutParams()).setMargins(dp8, dp8, dp8, dp8);
                final int dp80 = (int) ScreenUtil.dp2px(80, ChoreographyActivity.this);

                final PopupWindow popupWindow = new PopupWindow(contentView, dp80, dp80, true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAsDropDown(view);

                contentView.getLayoutParams().height = 0;
                contentView.getLayoutParams().width = 0;
                ((FrameLayout.LayoutParams) contentView.getLayoutParams()).gravity = Gravity.BOTTOM;

                contentView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        contentView.getViewTreeObserver().removeOnPreDrawListener(this);

                        TransitionManager.beginDelayedTransition(contentView, new ChangeBounds()
                                .setInterpolator(new FastOutSlowInInterpolator()));

                        contentView.getLayoutParams().height = dp80;
                        contentView.getLayoutParams().width = dp80;

                        return false;
                    }
                });

            }
        });
    }


    private void setupAllElemenShared(final ImageView rowImage) {
        final CardView cardView = (CardView) findViewById(R.id.all_element_share_card);
        cardView.setOnClickListener(new View.OnClickListener() {
            boolean isScene1 = true;

            @Override
            public void onClick(View v) {
                final Scene scene = Scene.getSceneForLayout(cardView, isScene1 ? R.layout.all_element_share_scene2 : R.layout.all_element_share_scene1, ChoreographyActivity.this);
                final Transition transition = TransitionInflater.from(ChoreographyActivity.this).inflateTransition(isScene1 ? R.transition.all_elemnet_share_enter : R.transition.all_element_share_return);
                TransitionManager.go(scene, transition);

                ((ImageView) scene.getSceneRoot().findViewById(R.id.all_element_share_image)).setImageDrawable(rowImage.getDrawable());

                isScene1 = !isScene1;
            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Intent intent = new Intent(ChoreographyActivity.this, ShareAllElementActivity.class);
                final ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(ChoreographyActivity.this, cardView.getChildAt(0), "all_element_share");
                ActivityCompat.startActivity(ChoreographyActivity.this, intent, optionsCompat.toBundle());
                return false;
            }
        });
    }

    private void setupFewElementsAreShared(final ImageView fewShareImage) {
        final CardView cardView = (CardView) findViewById(R.id.few_element_share_card);
        cardView.setOnClickListener(new View.OnClickListener() {
            boolean isScene1 = true;

            @Override
            public void onClick(View v) {
                final Scene scene = Scene.getSceneForLayout(cardView, isScene1 ? R.layout.few_element_share_scene2 : R.layout.few_element_share_scene1, ChoreographyActivity.this);
                final Transition transition = TransitionInflater.from(ChoreographyActivity.this).inflateTransition(R.transition.few_elemnet_share_enter);
                TransitionManager.go(scene, transition);

                ((ImageView) scene.getSceneRoot().findViewById(R.id.few_element_share_image)).setImageDrawable(fewShareImage.getDrawable());

                isScene1 = !isScene1;
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Intent intent = new Intent(ChoreographyActivity.this, ShareFewElementActivity.class);
                final ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(ChoreographyActivity.this, cardView.getChildAt(0), "few_element_share");
                ActivityCompat.startActivity(ChoreographyActivity.this, intent, optionsCompat.toBundle());
                return false;
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
