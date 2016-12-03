package com.example.awesoman.bigproject3.presenter.identify.user;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.awesoman.bigproject3.presenter.MvpPresenter;
import com.example.awesoman.bigproject3.model.entity.UserEntity;
import com.example.awesoman.bigproject3.model.entity.UserResult;
import com.example.awesoman.bigproject3.network.ShopClient;
import com.example.awesoman.bigproject3.network.UICallback;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Awesome on 2016/11/25.
 */

public class UpdatePresenter extends MvpPresenter<UpdateView> {
    Call call;

    public void requestUpdate(UserEntity entity,File file){
        call = ShopClient.getInstance().getUpdateRequest(entity, file);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {

            }

            @Override
            public void onResponseInUi(Call call, String body) {
                Log.i("requestUpdateOnUi",body);
                UserResult result = new Gson().fromJson(body, UserResult.class);
                getView().afterUpdate(result.getData());
            }
        });
    }
    @NonNull
    @Override
    protected UpdateView getNullObject() {
        return null;
    }
}
