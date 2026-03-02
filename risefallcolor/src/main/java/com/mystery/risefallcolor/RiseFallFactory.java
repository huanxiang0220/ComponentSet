package com.mystery.risefallcolor;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.ztcj.skinsupport_lib.SkinSupport;

import java.util.HashMap;
import java.util.Map;

public class RiseFallFactory implements LayoutInflater.Factory2 {

    private final Map<Integer, RiseFallAttr> mCollectViews = new HashMap<>();
    private final ArrayMap<Integer, IColorUpdate> mColorUpdates = new ArrayMap<>();

    private LayoutInflater.Factory mViewCreateFactory;
    private LayoutInflater.Factory2 mViewCreateFactory2;

    public void setInterceptFactory(LayoutInflater.Factory factory) {
        this.mViewCreateFactory = factory;
    }

    public void setInterceptFactory2(LayoutInflater.Factory2 factory2) {
        this.mViewCreateFactory2 = factory2;
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return onCreateView(name, context, attrs);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        View view = null;

        //防止与其他调用factory库冲突，例如字体、皮肤替换库，用已经设置的factory来创建view
        if (mViewCreateFactory2 != null) {
            view = mViewCreateFactory2.onCreateView(name, context, attrs);
            if (view == null) {
                view = mViewCreateFactory2.onCreateView(null, name, context, attrs);
            }
        } else if (mViewCreateFactory != null) {
            view = mViewCreateFactory.onCreateView(name, context, attrs);
        }

        if (view instanceof IColorUpdate) {
            mColorUpdates.put(view.hashCode(), (IColorUpdate) view);
            return view;
        }
        return setViewBackground(name, context, attrs, view);
    }

    private View setViewBackground(String name, Context context, AttributeSet attrs, View view) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RiseFall);

        try {
            if (ta.getIndexCount() == 0)
                return view;
            if (view == null) {
                view = ViewCreate.createViewFromTag(context, name, attrs);
            }
            if (view == null) {
                return null;
            }
            if (ta.getIndexCount() < 3) {
                //需要都设置
                throw new IllegalArgumentException("rf_type and rf_rise_background,rf_fall_background be used");
            }
            SkinSupport skin = ColorManager.getInstance().getSkinSupport();

            int type = ta.getInt(R.styleable.RiseFall_rf_type, 0);

//            Drawable riseDrawable = ta.getDrawable(R.styleable.RiseFall_rf_rise_background);
            int riseDrawableId = ta.getResourceId(R.styleable.RiseFall_rf_rise_background, 0);
//            Drawable fallDrawable = ta.getDrawable(R.styleable.RiseFall_rf_fall_background);
            int fallDrawableId = ta.getResourceId(R.styleable.RiseFall_rf_fall_background, 0);

            if (riseDrawableId != 0 && fallDrawableId != 0) {
                view.setBackground(skin.getDrawable(type == 1 ? riseDrawableId : fallDrawableId));
                mCollectViews.put(view.hashCode(), new RiseFallAttr(view, type).setDrawable(riseDrawableId, fallDrawableId));
            }
            if ((riseDrawableId == 0 && fallDrawableId != 0) || (riseDrawableId != 0 && fallDrawableId == 0)) {
                //需要都设置
                throw new NoSuchFieldException("riseDrawable and fallDrawable必须同时搭配一起设置");
            }
            //文本
//            int riseTColor = ta.getColor(R.styleable.RiseFall_rf_riseTColor, 0);
            int riseTColorId = ta.getResourceId(R.styleable.RiseFall_rf_riseTColor, 0);
//            int fallTColor = ta.getColor(R.styleable.RiseFall_rf_fallTColor, 0);
            int fallTColorId = ta.getResourceId(R.styleable.RiseFall_rf_fallTColor, 0);

            if (riseTColorId != 0 && fallTColorId != 0) {
                if (!(view instanceof TextView)) {
                    //需要都设置
                    throw new NoSuchFieldException("riseTColor and fallTColor只能用于TextView");
                }
                ((TextView) view).setTextColor(skin.getColor(type == 1 ? riseTColorId : fallTColorId));
                mCollectViews.put(view.hashCode(), new RiseFallAttr(view, type).setTColor(riseTColorId, fallTColorId));
            }
            if ((riseTColorId == 0 && fallTColorId != 0) || (riseTColorId != 0 && fallTColorId == 0)) {
                //需要都设置
                throw new NoSuchFieldException("riseTColor and fallTColor必须同时搭配一起设置");
            }

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return view;
        } finally {
            ta.recycle();
        }
    }

    private boolean isNeedUpdate = true;

    public void setNeedUpdate(boolean update) {
        if (isNeedUpdate != update) {
            isNeedUpdate = update;
        }
    }

    public void updateNotify() {
        if (!isNeedUpdate) {
            return;
        }
        isNeedUpdate = false;

        for (Map.Entry<Integer, IColorUpdate> entry : mColorUpdates.entrySet()) {
            entry.getValue().updateNotify();
        }

        SkinSupport skin = ColorManager.getInstance().getSkinSupport();

        for (Map.Entry<Integer, RiseFallAttr> entry : mCollectViews.entrySet()) {
            RiseFallAttr attr = entry.getValue();
            int type = attr.isPresenterType;
            View view = attr.view;
            if (ColorManager.getInstance().isRedRise()) {
                if (attr.setBackground) {
                    if (type == 1) {//当前控件主涨
                        view.setBackground(skin.getDrawable(attr.riseDrawableId));
                    } else if (type == 2) {
                        view.setBackground(skin.getDrawable(attr.fallDrawableId));
                    }
                }
                if (attr.setTColor && view instanceof TextView) {
                    if (type == 1) {//当前控件主涨
                        ((TextView) view).setTextColor(skin.getColor(attr.riseColorId));
                    } else if (type == 2) {
                        ((TextView) view).setTextColor(skin.getColor(attr.fallColorId));
                    }
                }

            } else {
                if (attr.setBackground) {
                    if (type == 1) {//当前控件主涨
                        view.setBackground(skin.getDrawable(attr.fallDrawableId));
                    } else if (type == 2) {
                        view.setBackground(skin.getDrawable(attr.riseDrawableId));
                    }
                }
                if (attr.setTColor && view instanceof TextView) {
                    if (type == 1) {//当前控件主涨
                        ((TextView) view).setTextColor(skin.getColor(attr.fallColorId));
                    } else if (type == 2) {
                        ((TextView) view).setTextColor(skin.getColor(attr.riseColorId));
                    }
                }

            }
        }
    }

}