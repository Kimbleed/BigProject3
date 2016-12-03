package com.example.awesoman.bigproject3.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.awesoman.bigproject3.MyApplication;
import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.view.activity.BaseActivity;
import com.example.awesoman.bigproject3.view.fragment.ChatFragment;
import com.example.awesoman.bigproject3.view.fragment.IdentifyFragment;
import com.example.awesoman.bigproject3.view.fragment.PeopleFragment;
import com.example.awesoman.bigproject3.view.fragment.ShoppingFragment;
import com.example.awesoman.bigproject3.view.fragment.shopping.UnLoginFragment;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseContactListFragment;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @Bind({R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4})
    TextView btnViews[];
    @Bind(R.id.fragment_container)
    ViewPager viewPager;

    @Override
    public void setIsToolbar() {
        super.isBackHome=false;
    }

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        viewPager.setAdapter(fragmentPagerAdapter);
        btnViews[0].setSelected(true);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i =0;i<btnViews.length;i++){
                    if(i!=position)
                        btnViews[i].setSelected(false);
                    else
                        btnViews[i].setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4})
    public void onClick(View view){
        for(TextView btn:btnViews){
            if(btn!=view)
                btn.setSelected(false);
            else
                btn.setSelected(true);
        }
        switch (view.getId()){
            case R.id.btn1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.btn2:
                    viewPager.setCurrentItem(1);
                break;
            case R.id.btn3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.btn4:
                viewPager.setCurrentItem(3);
                break;
        }
    }
    private FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ShoppingFragment();
                case 1:
                    if(((MyApplication)getApplication()).getUser()!=null) {
//                        EaseChatFragment easeChatFragment = new EaseChatFragment();
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
//                        easeChatFragment.setArguments(bundle);
                        return new ChatFragment();
                    }
                    return new UnLoginFragment();
                case 2:
                    if(((MyApplication)getApplication()).getUser()!=null)
                        return  new EaseContactListFragment();
                    return new UnLoginFragment();
                case 3:
                    return new IdentifyFragment();
            }
            return  null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("onTouchEvent",this.getClass().toString()+(super.onTouchEvent(event)?"true":"false"));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("dispatchTouchEvent",this.getClass().toString()+(super.dispatchTouchEvent(ev)?"true":"false"));
        return super.dispatchTouchEvent(ev);
    }

}
