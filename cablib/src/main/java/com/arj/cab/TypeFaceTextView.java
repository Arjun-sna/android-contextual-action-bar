/*
 * Copyright (c) 2015 CodeBrahma to Present.
 * All Rights reserved
 *
 * This file is part of SeeChat Project.
 */

package com.arj.cab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TypeFaceTextView extends TextView {

    public TypeFaceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public TypeFaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public TypeFaceTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TypeFaceTextView);
            String fontName = a.getString(R.styleable.TypeFaceTextView_fontName);
            if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }
}