package com.example.awesoman.bigproject3.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Awesome on 2016/11/22.
 */

public class MyViewPager extends ViewPager {
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("onTouchEvent",this.getClass().toString()+(super.onTouchEvent(ev)?"true":"false"));
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("dispatchTouchEvent",this.getClass().toString()+(super.dispatchTouchEvent(ev)?"true":"false"));
        return super.dispatchTouchEvent(ev);
    }
}
