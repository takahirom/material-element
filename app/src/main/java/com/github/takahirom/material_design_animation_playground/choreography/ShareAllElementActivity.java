package com.github.takahirom.material_design_animation_playground.choreography;

import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.takahirom.material_design_animation_playground.R;

public class ShareAllElementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_element_share_scene2);

        ActivityCompat.postponeEnterTransition(this);
        final ImageView rowImage = (ImageView) findViewById(R.id.all_content_element_share_image);
        Glide.with(this).load(R.drawable.choreography).asBitmap().into(new BitmapImageViewTarget(rowImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                rowImage.setImageDrawable(circularBitmapDrawable);
                rowImage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        ActivityCompat.startPostponedEnterTransition(ShareAllElementActivity.this);
                        rowImage.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    }
                });

            }
        });
    }
}
