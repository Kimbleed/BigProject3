package com.example.awesoman.bigproject3.view.activity.Goods;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.model.entity.GoodsEntity;
import com.example.awesoman.bigproject3.presenter.shop.GoodsPresenter;
import com.example.awesoman.bigproject3.presenter.shop.GoodsView;
import com.example.awesoman.bigproject3.view.fragment.shopping.OtherTypeAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Awesome on 2016/11/24.
 */

public class TypeActivity extends AppCompatActivity implements GoodsView {

    @Bind(R.id.ptrLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;

    @Bind(R.id.goods_recyclerview)
    RecyclerView recyclerView;


    GoodsPresenter goodsPresenter;
    OtherTypeAdapter otherTypeAdapter;
    String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){

        goodsPresenter = new GoodsPresenter();
        goodsPresenter.attachView(this);

        type = getIntent().getStringExtra("type");
        otherTypeAdapter = new OtherTypeAdapter();
        goodsPresenter.refreshData(1,type);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(otherTypeAdapter);

        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setBackgroundResource((R.color.black));
        ptrClassicFrameLayout.setDurationToCloseHeader(1000);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                Log.i("onLoadMoreBegin","here");
                goodsPresenter.loadMoreData(type);
                ptrClassicFrameLayout.refreshComplete();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                Log.i("onRefreshBegin","here");
                goodsPresenter.refreshData(1,type);
                ptrClassicFrameLayout.refreshComplete();
            }
        });





    }

    @Override
    public void hideRefresh() {

    }

    @Override
    public void addRefreshData(List<GoodsEntity> data) {
        otherTypeAdapter.setData(data);
        otherTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void addData(List<GoodsEntity> data) {

    }
}
