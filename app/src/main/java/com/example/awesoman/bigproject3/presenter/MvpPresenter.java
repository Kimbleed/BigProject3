package com.example.awesoman.bigproject3.presenter;

import android.support.annotation.NonNull;

/**
 * Created by Awesome on 2016/11/16.
 */

public abstract class MvpPresenter <V extends MvpView>{

    private V view;

    /**
     * Presenter创建的回调。
     * <p/>
     * 在Activity或Fragment的onCreate()方法中调用。
     */
    public final void onCreate() {
//        EventBus.getDefault().register(this);
    }

    /**
     * Presenter和视图关联。
     * <p/>
     * 在Activity的onCreate()中调用。
     * <p/>
     * 在Fragment的onViewCreated()或onActivityCreated()方法中调用。
     */
    public final void attachView(V view) {
        this.view = view;
    }


    /**
     * Presenter和视图解除关联。
     * <p/>
     * 在Activity的onDestroy()中调用。
     * <p/>
     * 在Fragment的onViewDestroyed()中调用。
     */
    public void detachView() {
        // 使用Null Object Pattern，避免子类使用getView()方法时频繁检查null值。
        this.view = getNullObject();
    }

    /**
     * Presenter销毁的回调。
     * <p/>
     * 在Activity或Fragment的onDestroy()方法中调用。
     */
    public final void onDestroy() {
//        EventBus.getDefault().unregister(this);
    }

    protected final V getView() {
        return view;
    }

    protected abstract @NonNull
    V getNullObject();
}
