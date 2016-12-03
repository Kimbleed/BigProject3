package com.example.awesoman.bigproject3.view.PopupWindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.awesoman.bigproject3.R;

/**
 * Created by Awesome on 2016/11/28.
 */

public class IconPopupWindow {
    PopupWindow popupWindow;

    public IconPopupWindow(Context context,final PicWindow.Listener picListener) {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = LayoutInflater.from(context).inflate(R.layout.popupwindow_personal_icon, null,
                false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        final Button btn_camera =(Button) popupWindow_view.findViewById(R.id.btn_camera);
        Button btn_gallery =(Button) popupWindow_view.findViewById(R.id.btn_gallery);
        Button btn_cancel =(Button) popupWindow_view.findViewById(R.id.btn_cancel);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picListener.toCamera();
            }
        });
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picListener.toGallery();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        // 设置动画效果
//        popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    public PopupWindow getIconPopupWindow(){
        return popupWindow;
    }


}
