package com.example.awesoman.bigproject3.presenter.identify.user;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.awesoman.bigproject3.EMUserManager;
import com.example.awesoman.bigproject3.MyApplication;
import com.example.awesoman.bigproject3.model.entity.UserEntity;
import com.example.awesoman.bigproject3.model.event.EMUserEvent;
import com.example.awesoman.bigproject3.presenter.MvpPresenter;
import com.example.awesoman.bigproject3.model.entity.LoginResult;
import com.example.awesoman.bigproject3.network.ShopClient;
import com.example.awesoman.bigproject3.network.UICallback;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Awesome on 2016/11/24.
 */

public class LoginPresenter extends MvpPresenter<LoginView>{

    Call call;
    public void requestLogin(String u,String p ){
        call = ShopClient.getInstance().getLoginResult(u,p);
        Log.i("requestLogin","start");
        call.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {

            }

            @Override
            public void onResponseInUi(Call call, String body) {
                LoginResult result = new Gson().fromJson(body,LoginResult.class);
                Log.i("requestLogin_onResponse",result.getMsg());
                if(result.getMsg().equals("succeed")) {
                    getView().afterLogin(result.getData());
                    if(getView() instanceof Activity )
                        EMUserManager.getInstance().asyncLogin(result.getData().getName(),
                                ((Activity)getView()).getSharedPreferences("login", Context.MODE_PRIVATE).getString("password",null));
                    else
                        EMUserManager.getInstance().asyncLogin(result.getData().getName(),
                                ((Application)getView()).getSharedPreferences("login", Context.MODE_PRIVATE).getString("password",null));
                }
            }
        });
    }
    @NonNull
    @Override
    protected LoginView getNullObject() {
        return null;
    }
}

