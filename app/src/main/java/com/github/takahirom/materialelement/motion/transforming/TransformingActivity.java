package com.github.takahirom.materialelement.motion.transforming;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.github.takahirom.materialelement.animation.transition.TransitionUtils;
import com.github.takahirom.materialelement.main.ImplementationItem;
import com.github.takahirom.materialelement.R;
import com.github.takahirom.materialelement.animation.transition.FabTransform;
import com.github.takahirom.materialelement.util.AndroidVersionUtil;

public class TransformingActivity extends AppCompatActivity {

    public final static String RESULT_EXTRA_ITEM_ID = "RESULT_EXTRA_ITEM_ID";
    public static final String INTENT_EXTRA_ITEM = "item";
    private ImplementationItem item;

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
//        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(TransformingActivity.this, android.R.color.white));
        imageView.setImageResource(item.imageRes);

        TransitionUtils.setSharedElementEnterTransitionEndListenerCompat(getWindow(), new TransitionUtils.OnSharedElementEnterTransitionEndListener() {
            @Override
            public void onEnd(Transition transition) {
                collapsingToolbarLayout.setTitleEnabled(true);
                collapsingToolbarLayout.setTitle(item.title);
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

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransformingActivity.this, LoginActivity.class);
                int color = ContextCompat.getColor(TransformingActivity.this, R.color.colorAccent);
                if (AndroidVersionUtil.isGreaterThanL()) {
                    FabTransform.addExtras(intent, color, R.drawable.ic_add_white_24dp);
                }
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(TransformingActivity.this,
                                v,
                                getString(R.string.transition_name_login));
                ActivityCompat.startActivity(TransformingActivity.this,
                        intent,
                        optionsCompat.toBundle());
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

