package com.mystery.skinsupport;

import android.graphics.drawable.Drawable;

public interface SkinSupport {

    int getColor(int resId);

    Drawable getDrawable(int resId);

    Drawable getGif(int resId);
}