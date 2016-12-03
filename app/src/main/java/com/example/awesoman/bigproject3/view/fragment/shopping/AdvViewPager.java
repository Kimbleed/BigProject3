package com.example.awesoman.bigproject3.view.fragment.shopping;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.awesoman.bigproject3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awesome on 2016/11/22.
 */

public class AdvViewPager extends ViewPager {
    private AdvAdapter pagerAdapter;
    private int ADV_PLAY = 0x223;
    int[] adv = {R.drawable.adv_0, R.drawable.adv_1, R.drawable.adv_2, R.drawable.adv_3};
    List<View> mData;
    String[] mDataString = new String[]{"广告1","广告2","广告3","广告4"};
    int count = 0;


    public AdvViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        pagerAdapter = new AdvAdapter(context);
        this.setAdapter(pagerAdapter);
        new AutoPlayHandler().sendEmptyMessageDelayed(ADV_PLAY,3000);
        this.setClickable(true);
//        getParent().requestDisallowInterceptTouchEvent(true);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        count = position;
    }

    class AutoPlayHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
                super.handleMessage(msg);
            if(msg.what == ADV_PLAY) {
                if(count==adv.length) {
                    count = 0;
                    setCurrentItem(count++,false);
                }
                else
                    setCurrentItem(count++);
                this.sendEmptyMessageDelayed(ADV_PLAY, 3000);
            }
        }
    }

    class AdvAdapter extends PagerAdapter {
        public AdvAdapter(Context context) {
            Resources resources = context.getResources();
            mData = new ArrayList<>();
            for (int i = 0; i < adv.length; i++) {
                Drawable drawable = resources.getDrawable(adv[i], null);
                View view =LayoutInflater.from(context).inflate(R.layout.item_adv,null);
                ((TextView)view.findViewById(R.id.tv)).setText(mDataString[i]);
                view.setBackground(drawable);
                mData.add(view);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mData.get(position));
            Log.i("instantiateItem",position+"");
            return mData.get(position);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
