package com.movi.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.movi.global.FontCache;

public class TextViewLightSpace extends TextView {

    public TextViewLightSpace(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
        init();
    }

    public TextViewLightSpace(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
        init();
    }

    public TextViewLightSpace(Context context) {
        super(context);
        applyCustomFont(context);
        init();
    }

    public void init() {
//        if (CommonUtils.isAboveOrEqualAndroid21())
//            setLetterSpacing(0.07f);
    }


    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/cb.ttf", context);
        setTypeface(customFont);
    }

    @Override
    public void setLetterSpacing(float letterSpacing) {
        super.setLetterSpacing(letterSpacing);
    }
}
