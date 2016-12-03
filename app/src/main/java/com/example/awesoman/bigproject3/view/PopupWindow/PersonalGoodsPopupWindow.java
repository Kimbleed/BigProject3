package com.example.awesoman.bigproject3.view.PopupWindow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.event.PersonalGoodEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by Awesome on 2016/11/29.
 */

public class PersonalGoodsPopupWindow implements View.OnClickListener{
    PopupWindow popupWindow;
    public PersonalGoodsPopupWindow(Context context) {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        final View popupWindow_view = LayoutInflater.from(context).inflate(R.layout.popupwindow_personal_goods, null,
                false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
       ImageView icon_all = (ImageView)popupWindow_view.findViewById(R.id.icon_all);
        ImageView icon_household = (ImageView)popupWindow_view.findViewById(R.id.icon_household);
        ImageView btn_all = (ImageView)popupWindow_view.findViewById(R.id.icon_dress);
        ImageView icon_toy = (ImageView)popupWindow_view.findViewById(R.id.icon_toy);
        ImageView icon_electron = (ImageView)popupWindow_view.findViewById(R.id.icon_electron);
        ImageView icon_book = (ImageView)popupWindow_view.findViewById(R.id.icon_book);
        ImageView icon_gift = (ImageView)popupWindow_view.findViewById(R.id.icon_gift);
        ImageView icon_other = (ImageView)popupWindow_view.findViewById(R.id.icon_other);


        icon_all.setOnClickListener(this);
        icon_household.setOnClickListener(this);
        btn_all.setOnClickListener(this);
        icon_toy.setOnClickListener(this);
        icon_electron.setOnClickListener(this);
        icon_book.setOnClickListener(this);
        icon_gift.setOnClickListener(this);
        icon_other.setOnClickListener(this);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.popupAnimation);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("onTouch","popupWindow_view");
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    Log.i("popupWindow","isShowing");
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.icon_all:
                EventBus.getDefault().post(new PersonalGoodEvent(""));
                popupWindow.dismiss();
                break;
            case R.id.icon_household:
                EventBus.getDefault().post(new PersonalGoodEvent("householde"));
                popupWindow.dismiss();
                break;
            case R.id.icon_dress:
                EventBus.getDefault().post(new PersonalGoodEvent("dress"));
                popupWindow.dismiss();
                break;
            case R.id.icon_toy:
                EventBus.getDefault().post(new PersonalGoodEvent("toy"));
                popupWindow.dismiss();
                break;
            case R.id.icon_electron:
                EventBus.getDefault().post(new PersonalGoodEvent("electron"));
                popupWindow.dismiss();
                break;
            case R.id.icon_book:
                EventBus.getDefault().post(new PersonalGoodEvent("book"));
                popupWindow.dismiss();
                break;
            case R.id.icon_gift:
                EventBus.getDefault().post(new PersonalGoodEvent("gift"));
                popupWindow.dismiss();
                break;
            case R.id.icon_other:
                EventBus.getDefault().post(new PersonalGoodEvent("other"));
                popupWindow.dismiss();
                break;
        }
    }

    public PopupWindow getIconPopupWindow(){
        return popupWindow;
    }

}
