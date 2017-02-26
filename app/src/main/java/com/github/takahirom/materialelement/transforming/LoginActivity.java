package com.github.takahirom.materialelement.transforming;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.github.takahirom.materialelement.R;
import com.github.takahirom.materialelement.animation.transition.FabTransform;


public class LoginActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FabTransform.setup(this, findViewById(R.id.container));
    }


    public void dismiss(View view) {
        ActivityCompat.finishAfterTransition(this);
    }
}
