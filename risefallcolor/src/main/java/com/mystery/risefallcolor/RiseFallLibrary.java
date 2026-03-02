package com.mystery.risefallcolor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

import java.lang.reflect.Field;

public class RiseFallLibrary {

    public static LayoutInflater inject(Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();

        if (inflater.getFactory2() == null) {
            RiseFallFactory factory = setDelegateFactory(activity);
            inflater.setFactory2(factory);

            ColorManager.getInstance().put(activity, factory);
        } else if (!(inflater.getFactory2() instanceof RiseFallFactory)) {
            forceSetFactory2(activity, inflater);
        }
        return inflater;
    }

    private static RiseFallFactory setDelegateFactory(Context context) {
        RiseFallFactory factory = new RiseFallFactory();
        if (context instanceof AppCompatActivity) {
            AppCompatDelegate delegate = ((AppCompatActivity) context).getDelegate();
            factory.setInterceptFactory((name, context1, attrs) ->
                    delegate.createView(null, name, context1, attrs));
        }
        return factory;
    }

    private static void forceSetFactory2(Activity activity, LayoutInflater inflater) {
        Class<LayoutInflaterCompat> compatClass = LayoutInflaterCompat.class;
        Class<LayoutInflater> inflaterClass = LayoutInflater.class;
        try {
            Field sCheckedField = compatClass.getDeclaredField("sCheckedField");
            sCheckedField.setAccessible(true);
            sCheckedField.setBoolean(inflater, false);
            Field mFactory = inflaterClass.getDeclaredField("mFactory");
            mFactory.setAccessible(true);
            Field mFactory2 = inflaterClass.getDeclaredField("mFactory2");
            mFactory2.setAccessible(true);
            RiseFallFactory factory = new RiseFallFactory();
            if (inflater.getFactory2() != null) {
                factory.setInterceptFactory2(inflater.getFactory2());
            } else if (inflater.getFactory() != null) {
                factory.setInterceptFactory(inflater.getFactory());
            }
            mFactory2.set(inflater, factory);
            mFactory.set(inflater, factory);

            ColorManager.getInstance().put(activity, factory);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}