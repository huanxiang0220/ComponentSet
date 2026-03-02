package com.mystery.risefallcolor;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import com.mystery.skinsupport.SkinSupport;

import java.util.ArrayList;
import java.util.List;

public class ColorManager {

    private final SkinSupport mSkinSupport;
    private static volatile ColorManager sInstance;

    private ColorManager(Application application, SkinSupport skinSupport) {
        this.mSkinSupport = skinSupport;
        application.registerActivityLifecycleCallbacks(new RiseFallActivityLifecycleRegister());
    }

    /**
     * 初始化 必须在Application中先进行初始化
     */
    public static void init(Application application, SkinSupport skinSupport) {
        if (sInstance == null) {
            synchronized (ColorManager.class) {
                if (sInstance == null) {
                    sInstance = new ColorManager(application, skinSupport);
                }
            }
        }
    }

    protected static ColorManager getInstance() {
        return sInstance;
    }

    protected SkinSupport getSkinSupport() {
        return mSkinSupport;
    }

    static class Entity {
        Activity activity;
        RiseFallFactory riseFallFactory;

        public Entity(Activity activity, RiseFallFactory riseFallFactory) {
            this.activity = activity;
            this.riseFallFactory = riseFallFactory;
        }
    }

    /**
     * <Activity:布局工厂>
     */
    private final List<Entity> mFactories = new ArrayList<>();

    public void put(Activity activity, RiseFallFactory factory) {
        mFactories.add(new Entity(activity, factory));
    }

    public void remove(@NonNull Activity activity) {
        for (int i = mFactories.size() - 1; i >= 0; i--) {
            if (activity == mFactories.get(i).activity) {
                mFactories.remove(i);
                break;
            }
        }
    }

    public RiseFallFactory get(Activity activity) {
        for (int i = mFactories.size() - 1; i >= 0; i--) {
            if (activity == mFactories.get(i).activity) {
                return mFactories.get(i).riseFallFactory;
            }
        }
        return null;
    }

    private boolean isRedRise = true;//是否是红涨绿跌

    boolean isRedRise() {
        return isRedRise;
    }

    /**
     * @param redRise true代表是红涨，false代表绿涨
     */
    public static void setRedRise(boolean redRise) {
        if (getInstance().isRedRise != redRise) {
            getInstance().isRedRise = redRise;
            //当前界面界面刷新
            List<Entity> factories = getInstance().mFactories;
            if (factories.isEmpty())
                return;
            for (Entity entity : factories) {
                entity.riseFallFactory.setNeedUpdate(true);//设置需要更新
            }
            Entity entity = factories.get(factories.size() - 1);
            entity.riseFallFactory.updateNotify();
        }
    }

}