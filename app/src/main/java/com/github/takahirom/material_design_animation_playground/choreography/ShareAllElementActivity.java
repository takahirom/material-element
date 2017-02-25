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
import com.github.takahirom.material_design_animation_playground.view.ResourceUtil;

public class ShareAllElementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_element_share_scene2);

        Bitmap bitmap = ResourceUtil.getBitmap(this, R.drawable.ic_choreography);
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        final ImageView rowImage = (ImageView) findViewById(R.id.all_element_share_image);
        rowImage.setImageDrawable(circularBitmapDrawable);
    }
}
