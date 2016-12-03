package com.example.awesoman.bigproject3.presenter.identify.user;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.awesoman.bigproject3.presenter.MvpPresenter;
import com.example.awesoman.bigproject3.model.entity.UserResult;
import com.example.awesoman.bigproject3.network.ShopClient;
import com.example.awesoman.bigproject3.network.UICallback;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Awesome on 2016/11/25.
 */

public class RegistPresenter extends MvpPresenter<RegistView> {
    Call call;
    public void requestRegist(final String username,final String password){
        call = ShopClient.getInstance().getRequestResult(username,password);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {

            }
            @Override
            public void onResponseInUi(Call call, String body) {
                UserResult result = new Gson().fromJson(body,UserResult.class);
                    getView().afterRegist(result);
            }
        });
    }
    @NonNull
    @Override
    protected RegistView getNullObject() {
        return null;
    }
}
