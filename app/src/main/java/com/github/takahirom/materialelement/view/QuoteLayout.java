package com.github.takahirom.materialelement.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.takahirom.materialelement.R;

public class QuoteLayout extends FrameLayout {

    public QuoteLayout(@NonNull Context context) {
        this(context, null);
    }

    public QuoteLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuoteLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.QuoteLayout);
        CharSequence text = a.getText(R.styleable.QuoteLayout_android_text);

        View view = LayoutInflater.from(context).inflate(R.layout.quote_layout, this, true);
        TextView textView = (TextView) view.findViewById(R.id.quote_text);
        textView.setText(text);
    }
}
