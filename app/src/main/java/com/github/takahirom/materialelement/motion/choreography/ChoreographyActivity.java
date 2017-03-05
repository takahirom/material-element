package com.github.takahirom.materialelement.motion.choreography;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.util.Pair;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.takahirom.materialelement.animation.OnetimeViewTreeObserver;
import com.github.takahirom.materialelement.animation.transition.TransitionUtils;
import com.github.takahirom.materialelement.main.AnimateRecyclerAdapter;
import com.github.takahirom.materialelement.main.ImplementationItem;
import com.github.takahirom.materialelement.R;
import com.github.takahirom.materialelement.util.AndroidVersionUtil;
import com.github.takahirom.materialelement.util.ScreenUtil;
import com.github.takahirom.materialelement.view.ResourceUtil;

public class ChoreographyActivity extends AppCompatActivity {


    public final static String RESULT_EXTRA_ITEM_ID = "RESULT_EXTRA_ITEM_ID";
    public static final String INTENT_EXTRA_ITEM = "item";
    private ImplementationItem item;

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
//        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.ChoreographyActivity.this, android.R.color.white));

        final ImageView allShareRowImage = (ImageView) findViewById(R.id.all_element_share_image);
        final ImageView fewShareImage = (ImageView) findViewById(R.id.few_element_share_image);

        imageView.setImageResource(item.imageRes);

        Bitmap bitmap = ResourceUtil.getBitmap(this, item.imageRes);
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        circularBitmapDrawable.setCircular(true);
        allShareRowImage.setImageDrawable(circularBitmapDrawable);
        fewShareImage.setImageDrawable(circularBitmapDrawable);
        TransitionUtils.setSharedElementEnterTransitionEndListenerCompat(getWindow(), new TransitionUtils.OnSharedElementEnterTransitionEndListener() {
            @Override
            public void onEnd(Transition transition) {
                collapsingToolbarLayout.setTitleEnabled(true);
                collapsingToolbarLayout.setTitle(item.title);
            }
        });

        setupAllElementShared(allShareRowImage);

        setupFewElementsAreShared(fewShareImage);

        setupCreation();

        setupChoreographingSurfaces();

        setupRadicalReaction();
    }


    private void setupRadicalReaction() {
        final CardView cardView = (CardView) findViewById(R.id.radical_reaction_card);
        cardView.setOnTouchListener(new View.OnTouchListener() {
            int colorIndex = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int endRadius = (int) (1.41421 * v.getWidth());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!AndroidVersionUtil.isGreaterThanL()) {
                        Toast.makeText(ChoreographyActivity.this, R.string.all_not_support_os_version, Toast.LENGTH_LONG).show();
                        return false;
                    }

                    if (cardView.getChildCount() > 2) {
                        cardView.removeViewAt(0);
                    }
                    View view = new View(ChoreographyActivity.this);
                    cardView.addView(view, new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    int colors[] = new int[]{R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark};
                    view.setBackgroundColor(ContextCompat.getColor(ChoreographyActivity.this, colors[(colorIndex++) % colors.length]));
                    Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, (int) event.getX(), (int) event.getY(), 0, endRadius);
                    circularReveal.start();
                }
                return false;
            }
        });
    }


    private void setupAllElementShared(final ImageView rowImage) {
        final CardView cardView = (CardView) findViewById(R.id.all_element_share_card);
        cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent intent = new Intent(ChoreographyActivity.this, ShareAllElementActivity.class);
                final ActivityOptionsCompat optionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(ChoreographyActivity.this, cardView, getString(R.string.transition_name_all_element_share));
                ActivityCompat.startActivity(ChoreographyActivity.this, intent, optionsCompat.toBundle());

            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            boolean isScene1 = true;

            @Override
            public boolean onLongClick(View v) {
                if (AndroidVersionUtil.isGreaterThanL()) {
                    final Scene scene = Scene.getSceneForLayout(cardView, isScene1 ? R.layout.all_element_share_scene2 : R.layout.all_element_share_scene1, ChoreographyActivity.this);
                    final Transition transition = TransitionInflater.from(ChoreographyActivity.this).inflateTransition(isScene1 ? R.transition.choreography_all_element_share_enter : R.transition.choreography_all_element_share_return);
                    TransitionManager.go(scene, transition);

                    ((ImageView) scene.getSceneRoot().findViewById(R.id.all_element_share_image)).setImageDrawable(rowImage.getDrawable());

                    isScene1 = !isScene1;
                } else {
                    Toast.makeText(ChoreographyActivity.this, R.string.all_not_support_os_version, Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }

    private void setupFewElementsAreShared(final ImageView fewShareImage) {
        final CardView cardView = (CardView) findViewById(R.id.few_element_share_card);
        cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ChoreographyActivity.this, ShareFewElementActivity.class);
                final ActivityOptionsCompat optionsCompat;
                if (AndroidVersionUtil.isGreaterThanM()) {
                    optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(ChoreographyActivity.this,
                            Pair.create((View) cardView, getString(R.string.transition_name_few_element_share)),
                            Pair.create(cardView.findViewById(R.id.few_element_share_image), getString(R.string.transition_name_few_element_share_image))
                    );
                }else{
                    // FIXME: strange behavior on lollipop when use child shared element
                    // Probably cant animate child view
                    optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(ChoreographyActivity.this,
                            Pair.create((View) cardView, getString(R.string.transition_name_few_element_share))
                    );
                }
                ActivityCompat.startActivity(ChoreographyActivity.this, intent, optionsCompat.toBundle());
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            boolean isScene1 = true;

            @Override
            public boolean onLongClick(View v) {
                if (AndroidVersionUtil.isGreaterThanL()) {
                    final Scene scene = Scene.getSceneForLayout(cardView, isScene1 ? R.layout.few_element_share_scene2 : R.layout.few_element_share_scene1, ChoreographyActivity.this);
                    final Transition transition = TransitionInflater.from(ChoreographyActivity.this).inflateTransition(R.transition.choreography_few_element_share_enter);
                    TransitionManager.go(scene, transition);

                    ((ImageView) scene.getSceneRoot().findViewById(R.id.few_element_share_image)).setImageDrawable(fewShareImage.getDrawable());
                } else {
                    Toast.makeText(ChoreographyActivity.this, R.string.all_not_support_os_version, Toast.LENGTH_LONG).show();

                }
                isScene1 = !isScene1;
                return true;
            }
        });
    }


    private void setupCreation() {
        findViewById(R.id.new_surface).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final CardView contentView = new CardView(ChoreographyActivity.this);

                contentView.setUseCompatPadding(true);
                final TextView textView = new TextView(ChoreographyActivity.this);
                textView.setText("sample");
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

                ((FrameLayout.LayoutParams) contentView.getLayoutParams()).gravity = Gravity.TOP;

                OnetimeViewTreeObserver.addOnPreDrawListener(contentView, new OnetimeViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public void onPreDraw() {
                        android.support.transition.TransitionManager.beginDelayedTransition(contentView, new android.support.transition.ChangeBounds()
                                .setInterpolator(new LinearOutSlowInInterpolator()));

                        contentView.getLayoutParams().height = dp80;
                        contentView.getLayoutParams().width = dp80;
                    }
                });

            }
        });
    }


    private void setupChoreographingSurfaces() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        Button button = (Button) findViewById(R.id.start_choreographing_surfaces_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(new AnimateRecyclerAdapter());
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
