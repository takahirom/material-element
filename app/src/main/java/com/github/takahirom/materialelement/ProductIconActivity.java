package com.github.takahirom.materialelement;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.airbnb.lottie.LottieAnimationView;
import com.github.takahirom.materialelement.main.MainActivity;

public class ProductIconActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_icon);
        final LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                animationView.getViewTreeObserver().removeOnPreDrawListener(this);

                // wait for start animation
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ProductIconActivity.this, MainActivity.class));
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }, animationView.getDuration());
                return false;
            }
        });

    }
}
