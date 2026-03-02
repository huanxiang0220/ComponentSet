package com.mystery.risefallcolor;

import android.view.View;

public class RiseFallAttr {

    View view;

    int isPresenterType;

    boolean setBackground;
    int riseDrawableId;
    int fallDrawableId;

    boolean setTColor;
    int riseColorId;
    int fallColorId;

    public RiseFallAttr(View view, int isPresenterType) {
        this.view = view;
        this.isPresenterType = isPresenterType;
    }

    /**
     * 设置背景
     */
    public RiseFallAttr setDrawable(int riseDrawableId, int fallDrawableId) {
        this.riseDrawableId = riseDrawableId;
        this.fallDrawableId = fallDrawableId;
        this.setBackground = true;
        return this;
    }

    /**
     * 设置文本颜色
     */
    public RiseFallAttr setTColor(int riseColor, int fallColor) {
        this.riseColorId = riseColor;
        this.fallColorId = fallColor;
        this.setTColor = true;
        return this;
    }

}