package com.mystery.magic.buildins.commonnavigator.titles;

import android.content.Context;

import com.mystery.magic.buildins.ArgbEvaluatorHolder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 两种颜色过渡的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public class ColorTransitionPagerTitleView extends SimplePagerTitleView {

    public ColorTransitionPagerTitleView(Context context) {
        super(context);
    }


    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(leavePercent, mSelectedColor, mNormalColor);
        setTextColor(color);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        int color = ArgbEvaluatorHolder.eval(enterPercent, mNormalColor, mSelectedColor);
//        getPaint().setFakeBoldText(true);
        setTextColor(color);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        if (isBoldEnable) {
            setTypeface(isNum(getText().toString()) ? mNumSelectTypeface : mSelectTypeface);
        } else {
            setTypeface(mNormalTypeface);
        }
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        if (isBoldEnable) {
            setTypeface(isNum(getText().toString()) ? mNumNormalTypeface : mNormalTypeface);
        }
    }

    public static boolean isNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

}