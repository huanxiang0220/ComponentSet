package com.mystery.magic.buildins.commonnavigator.titles;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

import com.mystery.magic.buildins.UIUtil;
import com.mystery.magic.buildins.commonnavigator.abs.IMeasurablePagerTitleView;

/**
 * 带文本的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public class SimplePagerTitleView extends AppCompatTextView implements IMeasurablePagerTitleView {
    protected int mSelectedColor;
    protected int mNormalColor;
    protected Typeface mNormalTypeface, mNumNormalTypeface, mSelectTypeface, mNumSelectTypeface;
    protected boolean isBoldEnable = false;//是否启用粗体

    public SimplePagerTitleView(Context context) {
        super(context, null);
        init(context);
    }

    private void init(Context context) {
        setGravity(Gravity.CENTER);
        int padding = UIUtil.dip2px(context, 10);
        setPadding(padding, 0, padding, 0);
//        setGravity(Gravity.BOTTOM);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        setTextColor(mSelectedColor);
        setTypeface(isBoldEnable ? mSelectTypeface : mNormalTypeface);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        setTextColor(mNormalColor);
        setTypeface(mNormalTypeface);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
    }

    @Override
    public int getContentLeft() {
        Rect bound = new Rect();
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), bound);
        int contentWidth = bound.width();
        return getLeft() + getWidth() / 2 - contentWidth / 2;
    }

    @Override
    public int getContentTop() {
        Paint.FontMetrics metrics = getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int) (getHeight() / 2 - contentHeight / 2);
    }

    @Override
    public int getContentRight() {
        Rect bound = new Rect();
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), bound);
        int contentWidth = bound.width();
        return getLeft() + getWidth() / 2 + contentWidth / 2;
    }

    @Override
    public int getContentBottom() {
        Paint.FontMetrics metrics = getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int) (getHeight() / 2 + contentHeight / 2);
    }

    public int getSelectedColor() {
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }

    public int getNormalColor() {
        return mNormalColor;
    }

    public void setNormalColor(int normalColor) {
        mNormalColor = normalColor;
    }

    public void setTextGravity(int gravity) {
        setGravity(gravity);
    }

    public void setTextPadding(int l, int t, int r, int b) {
        setPadding(l, t, r, b);
    }

    public void setBoldEnable(boolean boldEnable) {
        isBoldEnable = boldEnable;
    }

    public void setTypeface(Typeface normalTypeface, Typeface selectTypeface) {
        mNormalTypeface = normalTypeface;
        mNumNormalTypeface = normalTypeface;
        mSelectTypeface = selectTypeface;
        mNumSelectTypeface = selectTypeface;
    }

    public void setTypeface(Typeface normalTypeface, Typeface selectTypeface, Typeface numNormalTypeface, Typeface numSelectTypeface) {
        mNormalTypeface = normalTypeface;
        mSelectTypeface = selectTypeface;
        mNumNormalTypeface = numNormalTypeface;
        mNumSelectTypeface = numSelectTypeface;
    }

    public void setNumTypeface(Typeface numNormalTypeface, Typeface numSelectTypeface) {
        mNumNormalTypeface = numNormalTypeface;
        mNumSelectTypeface = numSelectTypeface;
    }

}