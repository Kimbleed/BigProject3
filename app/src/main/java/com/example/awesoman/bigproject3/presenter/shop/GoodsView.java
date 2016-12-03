package com.example.awesoman.bigproject3.presenter.shop;

import com.example.awesoman.bigproject3.presenter.MvpView;
import com.example.awesoman.bigproject3.model.entity.GoodsEntity;

import java.util.List;

/**
 * Created by Awesome on 2016/11/16.
 */

public interface GoodsView extends MvpView {

    /**
     * 隐藏下拉刷新视图
     */
    void hideRefresh();


    /**添加刷新更多数据
     *
     */
    void addRefreshData(List<GoodsEntity> data);

    /**添加更多数据
     *
     */
    void addData(List<GoodsEntity>data);
}
