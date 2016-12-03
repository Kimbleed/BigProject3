package com.example.awesoman.bigproject3.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.awesoman.bigproject3.R;
import com.example.awesoman.bigproject3.view.activity.Goods.TypeActivity;
import com.example.awesoman.bigproject3.model.entity.GoodsEntity;
import com.example.awesoman.bigproject3.model.event.TypeEvent;
import com.example.awesoman.bigproject3.presenter.shop.GoodsPresenter;
import com.example.awesoman.bigproject3.presenter.shop.GoodsView;
import com.example.awesoman.bigproject3.view.fragment.shopping.GoodsTypeAdapter;
import com.example.awesoman.bigproject3.view.fragment.shopping.ShoppingRecycleAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 * Created by Awesome on 2016/11/16.
 */

public class ShoppingFragment extends Fragment implements GoodsView,GoodsTypeAdapter.GoodsTypeListener,View.OnClickListener{

    @Bind(R.id.backTop)
    TextView backTopView;

    @Bind(R.id.goods_recyclerview)
    RecyclerView goodsRecycleView;

    @Bind(R.id.ptrLayout)
    PtrClassicFrameLayout ptrLayout;


    private String type="";


    private GoodsPresenter goodsPresenter;
    private ShoppingRecycleAdapter shoppingRecycleAdapter;




    private GridLayoutManager.SpanSizeLookup bbb = new GridLayoutManager.SpanSizeLookup(){
        @Override
        public int getSpanSize(int position) {
            if(position == 0 ||position ==1)
                return 2;
            return 1;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册EventBus
        EventBus.getDefault().register(this);
        goodsPresenter = new GoodsPresenter();
        goodsPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping,null);
        ButterKnife.bind(this,view);
        ptrLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptrLayout);
        backTopView.setVisibility(View.GONE);
        goodsRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if(layoutManager instanceof LinearLayoutManager){
                    LinearLayoutManager manager = (LinearLayoutManager)layoutManager;
                    if(manager.findFirstVisibleItemPosition()>1){
                        backTopView.setVisibility(View.VISIBLE);
                    }
                    else{
                        backTopView.setVisibility(View.GONE);
                    }
                }
            }
        });
        backTopView.setOnClickListener(this);
        goodsPresenter.refreshData(1,"");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        Log.i("onViewCreated","before new ShoppingAdapter");
        shoppingRecycleAdapter = new ShoppingRecycleAdapter(getContext());

        goodsRecycleView.setAdapter(shoppingRecycleAdapter);

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.backTop:
                goodsRecycleView.scrollToPosition(0);
        }
    }

    private void initView(){
        //配置PtrFrameLayout
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setSpanSizeLookup(bbb);
        goodsRecycleView.setLayoutManager(gridLayoutManager);

        ptrLayout.setLastUpdateTimeRelateObject(this);
        ptrLayout.setBackgroundResource((R.color.white));
        ptrLayout.setDurationToCloseHeader(1000);
        ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                Log.i("onLoadMoreBegin","here");
                goodsPresenter.loadMoreData(type);
                ptrLayout.refreshComplete();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                Log.i("onRefreshBegin","here");
                goodsPresenter.refreshData(1,type);
                ptrLayout.refreshComplete();
            }
        });

    }



    @Override
    public void addRefreshData(List<GoodsEntity> data) {
        shoppingRecycleAdapter.setData(data);
        shoppingRecycleAdapter.notifyDataSetChanged();
    }

    @Override
    public void addData(List<GoodsEntity> data) {
        shoppingRecycleAdapter.addData(data);
        shoppingRecycleAdapter.notifyDataSetChanged();
    }


    @Override
    public void hideRefresh() {
        ptrLayout.refreshComplete();
    }

    @Override
    public void updateType(String type) {
        goodsPresenter.refreshData(1,type);
        this.type = type;

    }

    public void onEventMainThread(TypeEvent event) {
//        goodsPresenter.refreshData(1,event.getType());
        Log.i("onEventMainThread","onEventMainThread");
        startActivity(new Intent().setClass(getContext(), TypeActivity.class).putExtra("type",event.getType()));
    }

}
