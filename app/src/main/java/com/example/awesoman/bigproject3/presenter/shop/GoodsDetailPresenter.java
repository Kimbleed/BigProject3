package com.example.awesoman.bigproject3.presenter.shop;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.awesoman.bigproject3.view.activity.Goods.GoodsDetailActivity;
import com.example.awesoman.bigproject3.presenter.MvpPresenter;
import com.example.awesoman.bigproject3.model.entity.GoodDetailResult;
import com.example.awesoman.bigproject3.model.entity.PageEntity;
import com.example.awesoman.bigproject3.network.ShopClient;
import com.example.awesoman.bigproject3.network.UICallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Awesome on 2016/11/18.
 */

public class GoodsDetailPresenter extends MvpPresenter<GoodsDetailActivity> {

    Call call;

    public void getImgPaths(String uuid){
        call = ShopClient.getInstance().getGoodDetail(uuid);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                Log.i("GoodsDetailPresenter",e.getMessage());
            }

            @Override
            public void onResponseInUi(Call call, String body) {
                GoodDetailResult result = new Gson().fromJson(body, GoodDetailResult.class);
                List<PageEntity> data  = result.getDatas().getPages();
                List<String> imgPaths = new ArrayList<>();
                for(int i = 0;i<data.size();i++){
                    imgPaths.add(data.get(i).getUri());
                }
                getView().showImg(imgPaths);
            }
        });

    }

    @NonNull
    @Override
    protected GoodsDetailActivity getNullObject() {
        return null;
    }
}
