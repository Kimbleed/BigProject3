package com.example.awesoman.bigproject3.presenter.shop;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.awesoman.bigproject3.presenter.MvpPresenter;
import com.example.awesoman.bigproject3.network.ShopClient;
import com.example.awesoman.bigproject3.network.UICallback;
import com.example.awesoman.bigproject3.model.entity.GoodsResult;
import com.example.awesoman.bigproject3.view.fragment.ShoppingFragment;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Awesome on 2016/11/16.
 */

public class GoodsPresenter extends MvpPresenter<GoodsView>{
    /**
     * GoodsView view 调用方法 this.view = (goodsView的子类)
     * @return
     */
    Call call;

    /**
     * 获取商品时，分页下标
     */
    private int pageInt = 1;

    public void refreshData(int pageNo,String type){
        pageInt = pageNo;
        call = ShopClient.getInstance().getGoods(pageNo,type);
        call.enqueue(new UICallback(){
            @Override
            public void onFailureInUi(Call call, IOException e) {
                Log.i("onFailureInUi",e.toString());
            }

            @Override
            public void onResponseInUi(Call call, String body) {
                Gson gson  = new Gson();
                GoodsResult result = gson.fromJson(body,GoodsResult.class);
                switch (result.getCode()){
                    case 1:
                        getView(). addRefreshData(result.getData());
                        getView().hideRefresh();
                        break;
                }
            }
        });
    }

    public void loadMoreData(String type){
        call = ShopClient.getInstance().getGoods(++pageInt,type);
        call.enqueue(new UICallback(){
            @Override
            public void onFailureInUi(Call call, IOException e) {
                Log.i("onFailureInUi",e.toString());
            }

            @Override
            public void onResponseInUi(Call call, String body) {
                Gson gson  = new Gson();
                GoodsResult result = gson.fromJson(body,GoodsResult.class);
                switch (result.getCode()){
                    case 1:
                        getView().addData(result.getData());
                        getView().hideRefresh();
                        break;
                }
            }
        });
    }

    @NonNull
    @Override
    protected ShoppingFragment getNullObject() {
        return null;
    }

}
