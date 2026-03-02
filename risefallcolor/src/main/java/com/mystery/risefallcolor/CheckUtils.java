package com.mystery.risefallcolor;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

public class CheckUtils {

    public static void checkBackground(Drawable riseDrawable, Drawable fallDrawable) throws CheckException {
        if ((riseDrawable == null && fallDrawable != null) || (riseDrawable != null && fallDrawable == null)) {
            //需要都设置
            throw new CheckException("riseDrawable and fallDrawable必须同时搭配一起设置");
        }
    }

    public static void checkBackground(View view) throws CheckException {
        if (view instanceof TextView) {
            //需要都设置
            throw new CheckException("riseTColor and fallTColor只能用于TextView");
        }
    }

    public static void checkTColor(int riseTColor, int fallTColor) throws CheckException {
        if ((riseTColor == 0 && fallTColor != 0) || (riseTColor != 0 && fallTColor == 0)) {
            //需要都设置
            throw new CheckException("riseTColor and fallTColor必须同时搭配一起设置");
        }
    }

}