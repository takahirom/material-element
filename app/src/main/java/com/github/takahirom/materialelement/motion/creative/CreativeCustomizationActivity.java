package com.github.takahirom.materialelement.motion.creative;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.github.takahirom.materialelement.MaterialElementActivity;
import com.github.takahirom.materialelement.R;
import com.github.takahirom.materialelement.animation.transition.TransitionUtils;
import com.github.takahirom.materialelement.main.ImplementationItem;

public class CreativeCustomizationActivity extends MaterialElementActivity {

    public final static String RESULT_EXTRA_ITEM_ID = "RESULT_EXTRA_ITEM_ID";
    public static final String INTENT_EXTRA_ITEM = "item";
    private ImplementationItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creative_customization);
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
        TransitionUtils.setSharedElementEnterTransitionEndListenerCompat(getWindow(), new TransitionUtils.OnSharedElementEnterTransitionEndListener() {
            @Override
            public void onEnd(Transition transition) {
                collapsingToolbarLayout.setTitleEnabled(true);
                collapsingToolbarLayout.setTitle(item.title);
            }
        });
        final ImageView systemIconImageView = (ImageView) findViewById(R.id.system_icon);
        systemIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Animatable) systemIconImageView.getDrawable()).start();
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
