package com.example.awesoman.bigproject3;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.awesoman.bigproject3.model.entity.UserEntity;
import com.example.awesoman.bigproject3.presenter.identify.user.LoginPresenter;
import com.example.awesoman.bigproject3.presenter.identify.user.LoginView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Awesome on 2016/11/25.
 */

public class MyApplication extends Application implements LoginView{
    UserEntity user = null;

    LoginPresenter loginPresenter;

    String icon_path ="/storage/emulated/0/crop_cache_file.jpg";

    String processAppName;

    public String getIcon_path() {
        return icon_path;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //判断是否已登陆
        judgeIsLogin();

        //配置ImageLoader
        initImageLoader();

//        Fresco.initialize(this);
        if(isAppProcess()){
            initEaseUI();
        }
    }

    public void judgeIsLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        Log.i("judgeIsLogin","beforeIf");
        if(sharedPreferences.getBoolean("isLogin",false)) {
            user = new UserEntity();
            loginPresenter = new LoginPresenter();
            loginPresenter.attachView(this);
            Log.i("application",sharedPreferences.getString("username", null));
            loginPresenter.requestLogin(sharedPreferences.getString("username", null),sharedPreferences.getString("password",null));
        }
    }

    public void initImageLoader(){
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(4*1024*1024)
                .defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(configuration);
    }


    public void initEaseUI(){
        EMOptions options = new EMOptions();
        //关闭自动登陆
        options.setAutoLogin(false);
            //默认加好友时，是不需要认证的，改成需要认证
        EaseUI.getInstance().init(this,null);
//        EaseUI.getInstance().setUserProfileProvider();

        EMClient.getInstance().setDebugMode(false);
    }

    /**
     * 判断Application是否在App进程中启动
     *
     */
    private boolean isAppProcess(){
        int pid = android.os.Process.myPid();
        Log.i("isAppProcess","start");
        String processAppName = getAppName(pid);
        Log.i("PackageName",getPackageName());
        Log.i("PackageName",processAppName);
        if(processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())){
            Log.i("isAppProcess","before return false");
            return false;
        }
        Log.i("isAppProcess","before return true");
        return true;
    }

    private String getAppName(int pID){
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo info:processes){
            if(info.pid == pID)
                processName = info.processName;
        }
        return processName;
    }



    @Override
    public void afterLogin(UserEntity entity) {
        user = entity;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
