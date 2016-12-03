package com.example.awesoman.bigproject3.view.fragment.shopping;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Awesome on 2016/11/23.
 */

public class MyPtrClass extends PtrClassicFrameLayout {
    public MyPtrClass(Context context) {
        super(context);
    }

    public MyPtrClass(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPtrClass(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float x,y;
    // 滑动距离及坐标 归还父控件焦点
    private float xDistance, yDistance, xLast, yLast,mLeft;
    boolean isFirst =true;
    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                isFirst = false;
                x = e.getX();
                y= e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                xDistance = e.getX()-x;
                yDistance = e.getY()-y;
                if(Math.abs(xDistance)>Math.abs(yDistance)){
                    return false;
                }
                else{
                    return super.dispatchTouchEvent(e);
                }
            case MotionEvent.ACTION_UP:
                break;
        }


        return super.dispatchTouchEvent(e);

    }
}
