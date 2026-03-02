package com.ztcj.skinsupport_lib;

import android.graphics.drawable.Drawable;

public class SkinLoader {

    private final SkinSupport mSkin;
    private static volatile SkinLoader sInstance;

    private SkinLoader(SkinSupport skinSupport) {
        this.mSkin = skinSupport;
    }

    /**
     * 将换肤绑定在SkinLoader上，使得拥有换肤加载资源的能力
     */
    public static void bind(SkinSupport skinSupport) {
        if (sInstance == null) {
            synchronized (SkinLoader.class) {
                if (sInstance == null) {
                    sInstance = new SkinLoader(skinSupport);
                }
            }
        }
    }

    public static int getColor_(int resId) {
        return sInstance.mSkin.getColor(resId);
    }

    public static Drawable getDrawable_(int resId) {
        return sInstance.mSkin.getDrawable(resId);
    }

}