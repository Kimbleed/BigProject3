package com.example.awesoman.bigproject3.presenter.identify.good;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.awesoman.bigproject3.presenter.MvpPresenter;
import com.example.awesoman.bigproject3.model.entity.GoodsEntity;
import com.example.awesoman.bigproject3.network.ShopClient;
import com.example.awesoman.bigproject3.network.UICallback;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Awesome on 2016/11/26.
 */

public class UploadGoodsPresenter extends MvpPresenter<UploadGoodsView> {
    Call call;

    public void requestUpload(GoodsEntity entity,List<File> files){
        call = ShopClient.getInstance().getUploadGoodsRequest(entity,files);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {

            }
            ;
            @Override
            public void onResponseInUi(Call call, String body) {
                Log.i("requestUpload",body);
                getView().afterUploadGoodsRequest();
            }
        });
    }

    @NonNull
    @Override
    protected UploadGoodsView getNullObject() {
        return null;
    }
}
