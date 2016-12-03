package com.example.awesoman.bigproject3.presenter.shop;

import com.example.awesoman.bigproject3.presenter.MvpView;

import java.util.List;

/**
 * Created by Awesome on 2016/11/18.
 */

public interface GoodsDetailView extends MvpView{
    /**
     * 显示图片
     */
    void showImg(List<String> bitmapPaths);
}
